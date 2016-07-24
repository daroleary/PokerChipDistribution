package com.solium.pcd.main;

import com.solium.pcd.domain.Color;
import com.solium.pcd.domain.PokerChip;
import com.solium.pcd.domain.PokerChips;
import com.solium.pcd.util.TestCase;
import com.solium.pcd.util.Util;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.MessageFormat;

public class PokerDistributionAssignmentIntTest extends TestCase {

    @Test
    public void main_problemOne_shouldReturnOptimumDistribution() {
        PokerChips actualPokerChipDistribution = PokerDistributionAssignment.optimumPokerChipDistributionFor("data/problem1.txt");
        assertSize(6, actualPokerChipDistribution);
        assertPokerChip(actualPokerChipDistribution.get(0), 2, 5, 0);
        assertPokerChip(actualPokerChipDistribution.get(1), 1, 5, 1);
        assertPokerChip(actualPokerChipDistribution.get(2), 0.50, 10, 10);
        assertPokerChip(actualPokerChipDistribution.get(3), 0.25, 10, 10);
        assertPokerChip(actualPokerChipDistribution.get(4), 0.10, 10, 10);
        assertPokerChip(actualPokerChipDistribution.get(5), 0.05, 10, 10);
    }

    @Test
    public void main_problemTwo_shouldReturnOptimumDistribution() {
        PokerChips actualPokerChipDistribution = PokerDistributionAssignment.optimumPokerChipDistributionFor("data/problem2.txt");
        assertSize(6, actualPokerChipDistribution);
        assertPokerChip(actualPokerChipDistribution.get(0), 2, 5, 1);
        assertPokerChip(actualPokerChipDistribution.get(1), 1, 5, 1);
        assertPokerChip(actualPokerChipDistribution.get(2), 0.50, 10, 6);
        assertPokerChip(actualPokerChipDistribution.get(3), 0.25, 10, 10);
        assertPokerChip(actualPokerChipDistribution.get(4), 0.10, 10, 10);
        assertPokerChip(actualPokerChipDistribution.get(5), 0.05, 10, 10);
    }

    @Test
    public void main_problemThree_shouldReturnOptimumDistribution() {
        PokerChips actualPokerChipDistribution = PokerDistributionAssignment.optimumPokerChipDistributionFor("data/problem3.txt");
        assertSize(6, actualPokerChipDistribution);
        assertPokerChip(actualPokerChipDistribution.get(0), 1, 5, 4, Color.RED);
        assertPokerChip(actualPokerChipDistribution.get(1), 0.50, 5, 4, Color.BLUE);
        assertPokerChip(actualPokerChipDistribution.get(2), 0.25, 10, 10, Color.BLACK);
        assertPokerChip(actualPokerChipDistribution.get(3), 0.10, 10, 9, Color.GREEN);
        assertPokerChip(actualPokerChipDistribution.get(4), 0.05, 10, 10, Color.YELLOW);
        assertPokerChip(actualPokerChipDistribution.get(5), 0.01, 10, 10, Color.TAUPE);
    }

    private void assertPokerChip(PokerChip firstPokerChip,
                                 double expectedDenomination,
                                 int expectedQuantity,
                                 int expectedBuyInQuantity) {
        assertPokerChip(firstPokerChip,
                expectedDenomination,
                expectedQuantity,
                expectedBuyInQuantity,
                Color.UNKNOWN);
    }

    private void assertPokerChip(PokerChip firstPokerChip,
                                 double expectedDenomination,
                                 int expectedQuantity,
                                 int expectedBuyInQuantity,
                                 Color expectedColor) {

        assertEquals(MessageFormat.format("Denomination: {0}, expected denomination {0}, got: {1}",
                expectedDenomination,
                firstPokerChip.getDenomination()),
                Util.roundToDecimalPlaces(BigDecimal.valueOf(expectedDenomination)), firstPokerChip.getDenomination());

        assertEquals(MessageFormat.format("Denomination: {0}, expected quantity {1}, got: {2}",
                expectedDenomination,
                expectedQuantity,
                firstPokerChip.getBuyInQuantity()),
                expectedQuantity, firstPokerChip.getQuantity());

        assertEquals(MessageFormat.format("Denomination: {0}, expected quantity set aside {1}, got: {2}",
                expectedDenomination,
                expectedBuyInQuantity,
                firstPokerChip.getQuantitySetAside()),
                expectedBuyInQuantity, firstPokerChip.getBuyInQuantity());

        assertEquals(MessageFormat.format("Denomination: {0}, expected color {1}, got: {2}",
                expectedDenomination,
                expectedColor,
                firstPokerChip.getColor()),
                expectedColor, firstPokerChip.getColor());
    }
}
