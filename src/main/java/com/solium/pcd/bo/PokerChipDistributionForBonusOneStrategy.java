package com.solium.pcd.bo;

import com.solium.pcd.domain.PokerChip;
import com.solium.pcd.domain.PokerChips;
import com.solium.pcd.exception.CalculationException;
import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.math.Amount;
import com.solium.pcd.util.Constants;

public class PokerChipDistributionForBonusOneStrategy extends PokerChipDistributionStrategyBase implements IPokerChipDistributionStrategy {

    @Override
    public PokerChips calculate(PokerChips pokerChips) throws CalculationException, PokerChipException {
        PokerChips pokerChipsWithBuyIn = getPokerListWithBuyInOfOneForAllDenominations(pokerChips);
        return getOptimumDistribution(pokerChipsWithBuyIn);
    }

    private PokerChips getPokerListWithBuyInOfOneForAllDenominations(final PokerChips pokerChips) throws PokerChipException {

        Amount initialBuyIn = Amount.ZERO;
        for (PokerChip pokerChip : pokerChips) {
            pokerChip.setQuantitySetAside(Constants.BONUS_TWO_MIN_QUANTITY);
            initialBuyIn = initialBuyIn.add(pokerChip.getDenomination());
        }
        pokerChips.setBuyInAmount(pokerChips.getBuyInAmount().subtract(initialBuyIn));

        return pokerChips;
    }
}
