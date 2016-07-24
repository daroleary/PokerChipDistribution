package com.solium.pcd.bo;

import com.solium.pcd.domain.PokerChips;
import com.solium.pcd.exception.CalculationException;
import com.solium.pcd.exception.PokerChipException;

public interface IPokerChipDistributionStrategy {

    PokerChips calculate(PokerChips list) throws CalculationException, PokerChipException;
}
