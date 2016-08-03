package com.solium.pcd.domain;

import com.google.common.collect.ComparisonChain;

import java.util.Comparator;

public class DenominationComparator implements Comparator<Denomination> {

    @Override
    public int compare(Denomination o1, Denomination o2) {

        return ComparisonChain.start()
                .compare(o1.getAmount(), o2.getAmount())
                .result();
    }
}
