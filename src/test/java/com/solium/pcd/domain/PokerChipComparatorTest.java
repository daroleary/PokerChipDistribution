package com.solium.pcd.domain;

import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.math.Amount;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PokerChipComparatorTest {

    @Test
    public void shouldOrderByDenominationInAscendingOrder() throws PokerChipException {

        List<PokerChip> list = getTestData();
        Collections.sort(list, new PokerChipComparator());

        assertEquals(Denomination.of(0.25), list.get(0).getDenomination());
        assertEquals(Denomination.of(0.50), list.get(1).getDenomination());
        assertEquals(Denomination.of(1.00), list.get(2).getDenomination());
    }

    private List<PokerChip> getTestData() throws PokerChipException {

        List<PokerChip> pokerChips = new ArrayList<>();
        pokerChips.add(getPokerChip(Color.RED, 1.00));
        pokerChips.add(getPokerChip(Color.BLUE, 0.25));
        pokerChips.add(getPokerChip(Color.BLACK, 0.50));

        return pokerChips;
    }

    private PokerChip getPokerChip(Color color,
                                   double denomination) {
        return PokerChip.newBuilder()
                .setColor(color)
                .setDenomination(Denomination.of(Amount.of(denomination)))
                .build();
    }
}
