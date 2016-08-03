package com.solium.pcd.bo;

import com.solium.pcd.domain.Player;
import com.solium.pcd.domain.PokerTable;
import com.solium.pcd.exception.CalculationException;
import com.solium.pcd.exception.PokerChipException;

public interface IPokerChipDistributionStrategy {

    Player calculate(PokerTable pokerTable) throws CalculationException, PokerChipException;
}
