package com.solium.pcd.bo;

import com.solium.pcd.domain.PokerChip;
import com.solium.pcd.domain.PokerChips;
import com.solium.pcd.exception.CalculationException;
import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.util.Constants;
import com.solium.pcd.util.Util;

import java.math.BigDecimal;

public class PokerChipDistributionForBonusOneStrategy extends PokerChipDistributionStrategyBase implements IPokerChipDistributionStrategy {

    @Override
    public PokerChips calculate(PokerChips pokerChips) throws CalculationException, PokerChipException {
        PokerChips pokerChipsWithBuyIn = getPokerListWithBuyInOfOneForAllDenominations(pokerChips);
        return getOptimumDistribution(pokerChipsWithBuyIn);
    }

    private PokerChips getPokerListWithBuyInOfOneForAllDenominations(final PokerChips pokerChips) throws PokerChipException {

        BigDecimal initialBuyIn = new BigDecimal("0.00");
        for (PokerChip current : pokerChips) {
            current.setQuantitySetAside(Constants.BONUS_TWO_MIN_QUANTITY);
            initialBuyIn = Util.roundToDecimalPlaces(initialBuyIn.add(current.getDenomination()));
        }
        pokerChips.setBuyInAmount(Util.subtractFor(pokerChips.getBuyInAmount(), initialBuyIn));

        return pokerChips;
    }
}
