package com.solium.pcd.domain;

import java.math.BigDecimal;
import java.util.Comparator;

public class PokerChipComparator implements Comparator<PokerChip> {

    /**
     * Compares two PokerChipDistribution objects to determine which one
     * has the lowest denomination amount
     *
     * @param obj1 first object to compare
     * @param obj2 second object to compare
     * @return (1) if obj1 is greater than obj2, returns (-1) if obj1 is less
     * than obj2 else return (0)
     */
    public final int compare(final PokerChip obj1, final PokerChip obj2) {
        BigDecimal denomination1 = obj1.getDenomination();
        BigDecimal denomination2 = obj2.getDenomination();

        return denomination1.compareTo(denomination2);
    }
}
