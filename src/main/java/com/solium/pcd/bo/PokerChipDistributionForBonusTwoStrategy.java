package com.solium.pcd.bo;

import com.solium.pcd.domain.Player;
import com.solium.pcd.domain.PokerTable;
import com.solium.pcd.exception.CalculationException;
import com.solium.pcd.exception.PokerChipException;

public class PokerChipDistributionForBonusTwoStrategy extends PokerChipDistributionStrategyBase implements IPokerChipDistributionStrategy {

    @Override
    public Player calculate(PokerTable pokerTable) throws CalculationException, PokerChipException {

        return getOptimumDistribution(pokerTable);
    }
}
