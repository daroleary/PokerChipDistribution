package com.solium.pcd.domain;

import com.solium.pcd.exception.PokerChipException;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PokerChipComparatorTest {

    @Test
    public void shouldOrderByDenominationInAscendingOrder() throws PokerChipException {

        List<PokerChip> list = getTestData();
        Collections.sort(list, new PokerChipComparator());

        Assert.assertEquals(new BigDecimal("0.50"), list.get(0).getDenomination());
        Assert.assertEquals(new BigDecimal("0.75"), list.get(1).getDenomination());
        Assert.assertEquals(new BigDecimal("1.00"), list.get(2).getDenomination());
    }

    private List<PokerChip> getTestData() throws PokerChipException {

        List<PokerChip> list = new ArrayList<>();
        list.add(new PokerChip(Color.RED, new BigDecimal("1.00"), 10));
        list.add(new PokerChip(Color.BLUE, new BigDecimal("0.50"), 5));
        list.add(new PokerChip(Color.BLACK, new BigDecimal("0.75"), 2));

        return list;
    }
}
