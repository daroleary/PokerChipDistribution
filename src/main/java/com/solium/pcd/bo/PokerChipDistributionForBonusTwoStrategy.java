package com.solium.pcd.bo;

import com.solium.pcd.domain.PokerChips;
import com.solium.pcd.exception.CalculationException;
import com.solium.pcd.exception.PokerChipException;

public class PokerChipDistributionForBonusTwoStrategy extends PokerChipDistributionStrategyBase implements IPokerChipDistributionStrategy {

    @Override
    public PokerChips calculate(PokerChips pokerChips) throws CalculationException, PokerChipException {

        return getOptimumDistribution(pokerChips);
    }
}
