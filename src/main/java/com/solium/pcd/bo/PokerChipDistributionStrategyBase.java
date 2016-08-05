package com.solium.pcd.bo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import com.solium.pcd.contract.Contract;
import com.solium.pcd.domain.ChipRoll;
import com.solium.pcd.domain.ChipRollComparator;
import com.solium.pcd.domain.Denomination;
import com.solium.pcd.domain.Player;
import com.solium.pcd.domain.PokerChip;
import com.solium.pcd.domain.PokerTable;
import com.solium.pcd.exception.CalculationException;
import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.math.Amount;
import com.solium.pcd.util.Constants;
import com.solium.pcd.util.ImmutableListCollector;

import java.text.MessageFormat;
import java.util.function.Function;

abstract class PokerChipDistributionStrategyBase {

    Player getOptimumDistribution(PokerTable pokerTable) throws CalculationException, PokerChipException {

        Amount remainingBuyIn = pokerTable.getBuyIn();

        ImmutableList<ChipRoll> selectedChips =
                applyInitialBuyInFor(pokerTable.getPokerChipCollection(), remainingBuyIn);

        remainingBuyIn = remainingBuyIn.subtract(getTotalAmountFrom(selectedChips));

        selectedChips = takeOverBuyInToZeroFor(selectedChips, remainingBuyIn);

        Amount totalBuyIn = getTotalAmountFrom(selectedChips);
        if (!(pokerTable.getBuyIn().equals(totalBuyIn))) {
            throw new CalculationException(MessageFormat.format(
                    "Unable to get optimum poker chip distribution from given inputs. Expected buy in was {0} but was calculated as {1}",
                    pokerTable.getBuyIn(),
                    totalBuyIn));
        }

        return Player.newBuilder()
                .setAlgorithm(pokerTable.getAlgorithm())
                .setPokerChipDistribution(reversePokerChipDistribution(selectedChips))
                .build();
    }

    Player getBonusOneOptimumDistribution(PokerTable pokerTable) throws CalculationException, PokerChipException {
        PokerTable bonusOnePokerTable = getBonusOnePokerTableFrom(pokerTable);

        Player player = getOptimumDistribution(bonusOnePokerTable);

        ImmutableList<ChipRoll> pokerChipCollectionWithQuantitySetAside =
                applyQuantitySetAsideFor(player.getPokerChipDistribution());

        return player.toBuilder()
                .setPokerChipDistribution(reversePokerChipDistribution(pokerChipCollectionWithQuantitySetAside))
                .build();
    }

    private ImmutableList<ChipRoll> reversePokerChipDistribution(ImmutableList<ChipRoll> selectedChips) {
        return selectedChips.stream()
                .sorted(Ordering.from(new ChipRollComparator()).reverse())
                .collect(new ImmutableListCollector<>());
    }

    private Amount getTotalAmountFrom(ImmutableList<ChipRoll> chipRolls) {
        return chipRolls.stream()
                .map(chipRoll -> {
                    final Amount amount = chipRoll.getPokerChip().getDenomination().getAmount();
                    return amount.multiply(chipRoll.getQuantity());
                })
                .reduce(Amount.ZERO, Amount::add);
    }

    private ImmutableList<ChipRoll> applyInitialBuyInFor(ImmutableList<ChipRoll> chipRolls,
                                                         Amount remainingBuyIn) {

        ImmutableList.Builder<ChipRoll> selectedChips = new ImmutableList.Builder<>();

        Amount buyInRemaining = remainingBuyIn;
        for (ChipRoll chipRoll : chipRolls) {
            PokerChip pokerChip = chipRoll.getPokerChip();

            int buyInQuantity = buyInQuantityFor(chipRoll, buyInRemaining);
            ChipRoll selectedChipRoll = chipRoll.toBuilder()
                    .setQuantity(buyInQuantity)
                    .setPokerChip(pokerChip)
                    .build();

            selectedChips.add(selectedChipRoll);
            buyInRemaining = buyInRemaining.subtract(pokerChip.getDenomination().getAmount().multiply(buyInQuantity));
        }
        return selectedChips.build();
    }

    private ImmutableList<ChipRoll> takeOverBuyInToZeroFor(ImmutableList<ChipRoll> chipRolls,
                                                           Amount remainingBuyIn) throws PokerChipException {
        if (remainingBuyIn.greaterThanOrEqual(Amount.ZERO)) {
            return chipRolls;
        }

        ImmutableList.Builder<ChipRoll> selectedChips = new ImmutableList.Builder<>();

        Amount overBuyIn = remainingBuyIn.abs();

        for (ChipRoll chipRoll : chipRolls.reverse()) {
            PokerChip pokerChip = chipRoll.getPokerChip();

            int selectedQuantity = chipRoll.getQuantity();
            if (!overBuyIn.equals(Amount.ZERO)) {
                int overBuyInQuantity = overBuyInQuantityUpToMax(chipRoll, overBuyIn.abs());
                if (overBuyInQuantity > 0) {
                    selectedQuantity = selectedQuantity - overBuyInQuantity;
                    Amount denomination = pokerChip.getDenomination().getAmount();
                    overBuyIn = overBuyIn.subtract(denomination.multiply(overBuyInQuantity));
                }
            }

            ChipRoll selectedChipRoll = chipRoll.toBuilder()
                    .setQuantity(selectedQuantity)
                    .setPokerChip(pokerChip)
                    .build();

            selectedChips.add(selectedChipRoll);
        }
        return selectedChips.build();
    }

    private int buyInQuantityFor(ChipRoll chipRoll, final Amount remainingBuyIn) {

        Amount buyInQuantity = buyInQuantityUpToMax(chipRoll, remainingBuyIn);
        Amount quantity = Amount.of(chipRoll.getQuantity());

        if (remainingBuyIn.greaterThan(Amount.ZERO) &&
                (quantity.subtract(buyInQuantity).greaterThan(Amount.ZERO))) {
            buyInQuantity = buyInQuantity.add(Amount.ONE);
        }

        return buyInQuantity.intValue();
    }

    private Amount buyInQuantityUpToMax(ChipRoll chipRoll, Amount remainingBuyIn) {
        PokerChip pokerChip = chipRoll.getPokerChip();
        Amount maxQuantity = Amount.of(remainingBuyIn.divide(pokerChip.getDenomination().getAmount()));

        Amount quantity = Amount.of(chipRoll.getQuantity());
        if (maxQuantity.greaterThan(Amount.ZERO)
                && maxQuantity.lessThan(quantity)) {
            quantity = maxQuantity;
        }
        return quantity;
    }

    private int overBuyInQuantityUpToMax(ChipRoll chipRoll, Amount overBuyIn) {
        PokerChip pokerChip = chipRoll.getPokerChip();
        Amount maxQuantity = Amount.of(overBuyIn.divide(pokerChip.getDenomination().getAmount()));

        if (maxQuantity.greaterThan(Amount.ZERO)) {
            int buyInQuantity = chipRoll.getQuantity();
            if (maxQuantity.greaterThan(buyInQuantity)) {
                maxQuantity = Amount.of(buyInQuantity);
            }
        }
        return maxQuantity.intValue();
    }

    ImmutableList<ChipRoll> setPokerChipsAsideIfBonusOneAlgorithm(ImmutableList<ChipRoll> chipRolls,
                                                                  int quantityToSetAside) {
        return chipRolls.stream()
                .map(toSetQuantityAside(quantityToSetAside))
                .collect(new ImmutableListCollector<>());
    }

    private Function<ChipRoll, ChipRoll> toSetQuantityAside(int quantityToSetAside) {
        return chipRoll -> {
            PokerChip pokerChip = chipRoll.getPokerChip();
            Denomination denomination = pokerChip.getDenomination();

            int quantity = chipRoll.getQuantity();
            Contract.requires(quantity >= quantityToSetAside,
                              MessageFormat.format(
                                      "Unable to set a quantitySetAside of [{0}] which is greater than the current available quantity of [{1}]",
                                      quantityToSetAside,
                                      quantity));

            return chipRoll.toBuilder()
                    .setPokerChip(pokerChip)
                    .setQuantity(quantity - quantityToSetAside)
                    .build();
        };
    }

    private PokerTable getBonusOnePokerTableFrom(PokerTable pokerTable) {
        ImmutableList<ChipRoll> chipRollsAfterSetAside =
                setPokerChipsAsideIfBonusOneAlgorithm(pokerTable.getPokerChipCollection(),
                                                      Constants.BONUS_ONE_MIN_QUANTITY);

        Amount remainingBuyIn = pokerTable.getBuyIn();
        for (ChipRoll chipRoll : chipRollsAfterSetAside) {
            Amount denomination = chipRoll.getPokerChip().getDenomination().getAmount();
            remainingBuyIn = remainingBuyIn.subtract(denomination.multiply(Constants.BONUS_ONE_MIN_QUANTITY));
        }

        return pokerTable.toBuilder()
                .setPokerChipCollection(chipRollsAfterSetAside)
                .setBuyIn(remainingBuyIn)
                .build();
    }

    private ImmutableList<ChipRoll> applyQuantitySetAsideFor(ImmutableList<ChipRoll> chipRolls) {
        return chipRolls.stream()
                .map(chipRoll -> chipRoll.toBuilder()
                        .setQuantity(chipRoll.getQuantity() + Constants.BONUS_ONE_MIN_QUANTITY)
                        .build())
                .collect(new ImmutableListCollector<>());
    }
}
