package com.solium.pcd.domain;

import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.math.Amount;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PokerChipComparatorTest {

    @Test
    public void shouldOrderByDenominationInAscendingOrder() throws PokerChipException {

        List<PokerChip> list = getTestData();
        Collections.sort(list, new PokerChipComparator());

        Assert.assertEquals(Amount.of(0.50), list.get(0).getDenomination());
        Assert.assertEquals(Amount.of(0.75), list.get(1).getDenomination());
        Assert.assertEquals(Amount.of(1.00), list.get(2).getDenomination());
    }

    private List<PokerChip> getTestData() throws PokerChipException {

        List<PokerChip> list = new ArrayList<>();
        list.add(PokerChip.of(Color.RED, 1.00, 10));
        list.add(PokerChip.of(Color.BLUE, 0.50, 5));
        list.add(PokerChip.of(Color.BLACK, 0.75, 2));

        return list;
    }
}
