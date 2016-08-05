package com.solium.pcd.domain;

import com.google.common.collect.ComparisonChain;

import java.util.Comparator;

public class ChipRollComparator implements Comparator<ChipRoll> {

    @Override
    public int compare(ChipRoll o1, ChipRoll o2) {
        Denomination d1 = o1.getPokerChip().getDenomination();
        Denomination d2 = o2.getPokerChip().getDenomination();
        return ComparisonChain.start()
                .compare(d1, d2)
                .result();
    }
}
