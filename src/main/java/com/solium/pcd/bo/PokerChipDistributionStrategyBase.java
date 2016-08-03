package com.solium.pcd.bo;

import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Ordering;
import com.solium.pcd.contract.Contract;
import com.solium.pcd.domain.ChipRoll;
import com.solium.pcd.domain.Denomination;
import com.solium.pcd.domain.DenominationComparator;
import com.solium.pcd.domain.Player;
import com.solium.pcd.domain.PokerChip;
import com.solium.pcd.domain.PokerTable;
import com.solium.pcd.exception.CalculationException;
import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.math.Amount;
import com.solium.pcd.util.Constants;

import java.text.MessageFormat;

abstract class PokerChipDistributionStrategyBase {

    Player getOptimumDistribution(PokerTable pokerTable) throws CalculationException, PokerChipException {

        Amount remainingBuyIn = pokerTable.getBuyIn();

        ImmutableSortedMap<Denomination, ChipRoll> selectedChips =
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

        ImmutableSortedMap<Denomination, ChipRoll> pokerChipCollectionWithQuantitySetAside =
                applyQuantitySetAsideFor(player.getPokerChipDistribution());

        return player.toBuilder()
                .setPokerChipDistribution(reversePokerChipDistribution(pokerChipCollectionWithQuantitySetAside))
                .build();
    }

    private ImmutableSortedMap<Denomination, ChipRoll> reversePokerChipDistribution(ImmutableSortedMap<Denomination, ChipRoll> selectedChips) {
        return new ImmutableSortedMap.Builder<Denomination, ChipRoll>(
                Ordering.from(new DenominationComparator()).reversed())
                .putAll(selectedChips)
                .build();
    }


    private Amount getTotalAmountFrom(ImmutableSortedMap<Denomination, ChipRoll> pokerChipCollection) {
        Amount totalQuantity = Amount.ZERO;
        for (ChipRoll chipRoll : pokerChipCollection.values()) {
            Amount denomination = chipRoll.getPokerChip().getDenomination().getAmount();
            totalQuantity = totalQuantity.add(denomination.multiply(chipRoll.getQuantity()));
        }
        return totalQuantity;
    }

    private ImmutableSortedMap<Denomination, ChipRoll> applyInitialBuyInFor(ImmutableSortedMap<Denomination, ChipRoll> pokerChipCollection,
                                                                            Amount remainingBuyIn) {

        ImmutableSortedMap.Builder<Denomination, ChipRoll> selectedChips = new ImmutableSortedMap.Builder<>(new DenominationComparator());

        Amount buyInRemaining = remainingBuyIn;
        for (ChipRoll chipRoll : pokerChipCollection.values()) {
            PokerChip pokerChip = chipRoll.getPokerChip();

            int buyInQuantity = buyInQuantityFor(chipRoll, buyInRemaining);
            ChipRoll selectedChipRoll = chipRoll.toBuilder()
                    .setQuantity(buyInQuantity)
                    .setPokerChip(pokerChip)
                    .build();

            Denomination denomination = pokerChip.getDenomination();
            selectedChips.put(denomination, selectedChipRoll);
            buyInRemaining = buyInRemaining.subtract(denomination.getAmount().multiply(buyInQuantity));
        }
        return selectedChips.build();
    }

    private ImmutableSortedMap<Denomination, ChipRoll> takeOverBuyInToZeroFor(ImmutableSortedMap<Denomination, ChipRoll> pokerChipCollection,
                                                                              Amount remainingBuyIn) throws PokerChipException {
        if (remainingBuyIn.greaterThanOrEqual(Amount.ZERO)) {
            return pokerChipCollection;
        }

        ImmutableSortedMap.Builder<Denomination, ChipRoll> selectedChips = new ImmutableSortedMap.Builder<>(
                new DenominationComparator());

        Amount overBuyIn = remainingBuyIn.abs();

        ImmutableSortedMap<Denomination, ChipRoll> pokerChipCollectionInReverse =
                new ImmutableSortedMap.Builder<Denomination, ChipRoll>(Ordering.from(new DenominationComparator())
                                                                               .reverse())
                        .putAll(pokerChipCollection)
                        .build();

        for (ChipRoll chipRoll : pokerChipCollectionInReverse.values()) {
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

            selectedChips.put(pokerChip.getDenomination(), selectedChipRoll);
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

    ImmutableSortedMap<Denomination, ChipRoll> setPokerChipsAsideIfBonusOneAlgorithm(ImmutableSortedMap<Denomination, ChipRoll> pokerChipCollection,
                                                                                     int quantityToSetAside) {

        ImmutableSortedMap.Builder<Denomination, ChipRoll> selectedChips
                = new ImmutableSortedMap.Builder<>(new DenominationComparator());

        for (ChipRoll chipRoll : pokerChipCollection.values()) {
            PokerChip pokerChip = chipRoll.getPokerChip();
            Denomination denomination = pokerChip.getDenomination();

            int quantity = chipRoll.getQuantity();
            Contract.requires(quantity >= quantityToSetAside,
                              MessageFormat.format(
                                      "Unable to set a quantitySetAside of [{0}] which is greater than the current available quantity of [{1}]",
                                      quantityToSetAside,
                                      quantity));

            ChipRoll chipRollToSetAside = chipRoll.toBuilder()
                    .setPokerChip(pokerChip)
                    .setQuantity(quantity - quantityToSetAside)
                    .build();
            selectedChips.put(denomination, chipRollToSetAside);
        }

        return selectedChips.build();
    }

    private ImmutableSortedMap<Denomination, ChipRoll> applyQuantitySetAsideFor(ImmutableSortedMap<Denomination, ChipRoll> pokerChipCollection) {

        ImmutableSortedMap.Builder<Denomination, ChipRoll> selectedChips = new ImmutableSortedMap.Builder<>(
                new DenominationComparator());

        for (ChipRoll chipRoll : pokerChipCollection.values()) {
            PokerChip pokerChip = chipRoll.getPokerChip();
            ChipRoll selectedChipRoll = chipRoll.toBuilder()
                    .setQuantity(chipRoll.getQuantity() + Constants.BONUS_ONE_MIN_QUANTITY)
                    .setPokerChip(pokerChip)
                    .build();
            selectedChips.put(pokerChip.getDenomination(), selectedChipRoll);
        }

        return selectedChips.build();
    }

    private PokerTable getBonusOnePokerTableFrom(PokerTable pokerTable) {
        ImmutableSortedMap<Denomination, ChipRoll> pokerChipCollectionAfterSetAside =
                setPokerChipsAsideIfBonusOneAlgorithm(pokerTable.getPokerChipCollection(),
                                                      Constants.BONUS_ONE_MIN_QUANTITY);

        Amount remainingBuyIn = pokerTable.getBuyIn();
        for (ChipRoll chipRoll : pokerChipCollectionAfterSetAside.values()) {
            Amount denomination = chipRoll.getPokerChip().getDenomination().getAmount();
            remainingBuyIn = remainingBuyIn.subtract(denomination.multiply(Constants.BONUS_ONE_MIN_QUANTITY));
        }

        return pokerTable.toBuilder()
                .setPokerChipCollection(pokerChipCollectionAfterSetAside)
                .setBuyIn(remainingBuyIn)
                .build();
    }
}
