package com.solium.pcd.util;

import com.google.common.collect.ImmutableList;
import com.solium.pcd.domain.ChipRoll;
import com.solium.pcd.domain.Color;
import com.solium.pcd.domain.Denomination;
import com.solium.pcd.domain.PokerChip;
import org.junit.Assert;

import java.text.MessageFormat;
import java.util.Collection;

public class TestCase extends Assert {

    protected void assertSize(int expected, Collection<?> collection) {
        String errorMessage = MessageFormat.format("The collection should have had {0} items, instead it had {1} items.",
                                                   expected, collection.size());
        assertEquals(errorMessage, expected, collection.size());
    }

    protected static ImmutableList<String> getProblemOneTestCaseOneData() {

        ImmutableList.Builder<String> pokerDetails = new ImmutableList.Builder<>();

        pokerDetails.add("50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05");
        pokerDetails.add("10");
        pokerDetails.add("$10.00");

        return pokerDetails.build();
    }

    protected static ImmutableList<String> getProblemOneTestCaseTwoData() {

        ImmutableList.Builder<String> pokerDetails = new ImmutableList.Builder<>();

        pokerDetails.add("B1");
        pokerDetails.add("50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05");
        pokerDetails.add("10");
        pokerDetails.add("$10.00");

        return pokerDetails.build();
    }

    protected static ImmutableList<String> getProblemOneTestCaseThreeData() {

        ImmutableList.Builder<String> pokerDetails = new ImmutableList.Builder<>();

        pokerDetails.add("B2");
        pokerDetails.add("50/Red,50/Blue,100/Black,100/Green,100/Yellow,100/Taupe");
        pokerDetails.add("10");
        pokerDetails.add("$10.00");

        return pokerDetails.build();
    }

    protected static void assertChipRoll(ChipRoll chipRoll,
                                         Denomination expectedDenomination,
                                         int expectedQuantity) {
        assertChipRoll(chipRoll,
                       expectedQuantity,
                       Color.UNKNOWN,
                       expectedDenomination);
    }

    protected static void assertChipRoll(ChipRoll chipRoll,
                                         int expectedQuantity,
                                         Color expectedColor,
                                         Denomination expectedDenomination) {

        PokerChip expectedPokerChip = PokerChip.newBuilder()
                .setColor(expectedColor)
                .setDenomination(expectedDenomination)
                .build();

        assertEquals(expectedQuantity, chipRoll.getQuantity());
        assertEquals(expectedPokerChip, chipRoll.getPokerChip());
    }
}
