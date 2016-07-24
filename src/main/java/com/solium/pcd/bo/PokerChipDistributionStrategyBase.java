package com.solium.pcd.bo;

import com.solium.pcd.domain.PokerChip;
import com.solium.pcd.domain.PokerChipComparator;
import com.solium.pcd.domain.PokerChips;
import com.solium.pcd.exception.CalculationException;
import com.solium.pcd.exception.PokerChipException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.ListIterator;

import static com.solium.pcd.util.Util.isLessThanZero;
import static com.solium.pcd.util.Util.isNotZero;
import static com.solium.pcd.util.Util.roundToDecimalPlaces;

abstract class PokerChipDistributionStrategyBase {

    final PokerChips getOptimumDistribution(final PokerChips pokerChips) throws CalculationException, PokerChipException {

        applyInitialBuyInFor(pokerChips);
        takeOverBuyInToZeroFor(pokerChips);

        BigDecimal totalBuyIn = totalBuyInFor(pokerChips);
        if (!(0 == pokerChips.getBuyInAmount().compareTo(totalBuyIn))) {
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
        if (isLessThanZero(pokerChips.getRemainingBuyIn())) {
            pokerChips.sort(new PokerChipComparator());
            Collections.reverse(pokerChips);
            ListIterator<PokerChip> iter = pokerChips.listIterator();

            while (iter.hasNext() && isNotZero(pokerChips.getRemainingBuyIn())) {
                PokerChip pokerChip = iter.next();
                int overBuyInQuantity = pokerChip.overBuyInQuantityUpToMax(pokerChips.getRemainingBuyIn().abs());
                if (overBuyInQuantity > 0) {
                    int buyInQuantity = pokerChip.getBuyInQuantity();
                    pokerChip.setBuyInQuantity(buyInQuantity - overBuyInQuantity);
                }
            }
        }
    }

    private BigDecimal totalBuyInFor(PokerChips pokerChips) {

        BigDecimal totalBuyIn = new BigDecimal("0.00");

        for (PokerChip current : pokerChips) {
            int buyInQty = current.getBuyInQuantity();
            BigDecimal denomination = current.getDenomination();
            totalBuyIn = roundToDecimalPlaces(totalBuyIn.add(denomination.multiply(new BigDecimal(buyInQty))));
        }
        return totalBuyIn;
    }
}
