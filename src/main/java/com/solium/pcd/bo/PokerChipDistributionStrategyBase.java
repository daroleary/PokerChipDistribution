package com.solium.pcd.bo;

import com.solium.pcd.domain.PokerChip;
import com.solium.pcd.domain.PokerChipComparator;
import com.solium.pcd.domain.PokerChips;
import com.solium.pcd.exception.CalculationException;
import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.math.Amount;

import java.util.Collections;
import java.util.ListIterator;

abstract class PokerChipDistributionStrategyBase {

    final PokerChips getOptimumDistribution(final PokerChips pokerChips) throws CalculationException, PokerChipException {

        applyInitialBuyInFor(pokerChips);
        takeOverBuyInToZeroFor(pokerChips);

        Amount totalBuyIn = totalBuyInFor(pokerChips);
        if (!(pokerChips.getBuyInAmount().equals(totalBuyIn))) {
            throw new CalculationException(String.format("Unable to get optimum poker chip distribution from given inputs. Expected buy in was %s but was calculated as %s", pokerChips.getBuyInAmount(), totalBuyIn));
        }

        applyQuantitySetAsideFor(pokerChips);

        pokerChips.sort(new PokerChipComparator());
        Collections.reverse(pokerChips);

        return pokerChips;
    }

    private void applyQuantitySetAsideFor(PokerChips pokerChips) throws PokerChipException {
        for (PokerChip pokerChip : pokerChips) {
            pokerChip.applyQuantitySetAside();
        }
    }

    private void applyInitialBuyInFor(PokerChips pokerChips) throws PokerChipException {
        for (PokerChip pokerChip : pokerChips) {
            int buyInQuantity = pokerChip.buyInQuantityFor(pokerChips.getRemainingBuyIn());
            pokerChip.setBuyInQuantity(buyInQuantity);
        }
    }

    private void takeOverBuyInToZeroFor(PokerChips pokerChips) throws PokerChipException {
        if (pokerChips.getRemainingBuyIn().lessThan(Amount.ZERO)) {
            pokerChips.sort(new PokerChipComparator());
            Collections.reverse(pokerChips);
            ListIterator<PokerChip> iter = pokerChips.listIterator();

            while (iter.hasNext() && !pokerChips.getRemainingBuyIn().equals(Amount.ZERO)) {
                PokerChip pokerChip = iter.next();
                int overBuyInQuantity = pokerChip.overBuyInQuantityUpToMax(pokerChips.getRemainingBuyIn().abs());
                if (overBuyInQuantity > 0) {
                    int buyInQuantity = pokerChip.getBuyInQuantity();
                    pokerChip.setBuyInQuantity(buyInQuantity - overBuyInQuantity);
                }
            }
        }
    }

    private Amount totalBuyInFor(PokerChips pokerChips) {

        return pokerChips.stream()
                .map(pc -> pc.getDenomination().multiply(pc.getBuyInQuantity()))
                .reduce(Amount.ZERO, Amount::add);
    }
}
