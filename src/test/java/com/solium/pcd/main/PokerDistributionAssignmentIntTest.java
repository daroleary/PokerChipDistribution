package com.solium.pcd.main;

import com.google.common.collect.ImmutableSortedMap;
import com.solium.pcd.domain.ChipRoll;
import com.solium.pcd.domain.Color;
import com.solium.pcd.domain.Denomination;
import com.solium.pcd.domain.Player;
import com.solium.pcd.util.TestCase;
import org.junit.Test;

public class PokerDistributionAssignmentIntTest extends TestCase {

    @Test
    public void main_problemOne_shouldReturnOptimumDistribution() {
        Player actualPlayer = PokerDistributionAssignment.optimumPokerChipDistributionFor("data/problem1.txt");

        ImmutableSortedMap<Denomination, ChipRoll> pokerChipDistribution = actualPlayer.getPokerChipDistribution();

        assertEquals(6, pokerChipDistribution.size());
        assertChipRoll(pokerChipDistribution, Denomination.TWO_DOLLARS, 0);
        assertChipRoll(pokerChipDistribution, Denomination.ONE_DOLLAR, 1);
        assertChipRoll(pokerChipDistribution, Denomination.FIFTY_CENT, 10);
        assertChipRoll(pokerChipDistribution, Denomination.TWENTY_FIVE_CENTS, 10);
        assertChipRoll(pokerChipDistribution, Denomination.TEN_CENTS, 10);
        assertChipRoll(pokerChipDistribution, Denomination.FIVE_CENTS, 10);
    }

    @Test
    public void main_problemTwo_shouldReturnOptimumDistribution() {
        Player actualPlayer = PokerDistributionAssignment.optimumPokerChipDistributionFor("data/problem2.txt");

        ImmutableSortedMap<Denomination, ChipRoll> pokerChipDistribution = actualPlayer.getPokerChipDistribution();

        assertEquals(6, pokerChipDistribution.size());
        assertChipRoll(pokerChipDistribution, Denomination.TWO_DOLLARS, 1);
        assertChipRoll(pokerChipDistribution, Denomination.ONE_DOLLAR, 1);
        assertChipRoll(pokerChipDistribution, Denomination.FIFTY_CENT, 6);
        assertChipRoll(pokerChipDistribution, Denomination.TWENTY_FIVE_CENTS, 10);
        assertChipRoll(pokerChipDistribution, Denomination.TEN_CENTS, 10);
        assertChipRoll(pokerChipDistribution, Denomination.FIVE_CENTS, 10);
    }

    @Test
    public void main_problemThree_shouldReturnOptimumDistribution() {
        Player actualPlayer = PokerDistributionAssignment.optimumPokerChipDistributionFor("data/problem3.txt");

        ImmutableSortedMap<Denomination, ChipRoll> pokerChipDistribution = actualPlayer.getPokerChipDistribution();

        assertEquals(6, pokerChipDistribution.size());
        assertChipRoll(pokerChipDistribution, 4, Color.RED, Denomination.ONE_DOLLAR);
        assertChipRoll(pokerChipDistribution, 4, Color.BLUE, Denomination.FIFTY_CENT);
        assertChipRoll(pokerChipDistribution, 10, Color.BLACK, Denomination.TWENTY_FIVE_CENTS);
        assertChipRoll(pokerChipDistribution, 9, Color.GREEN, Denomination.TEN_CENTS);
        assertChipRoll(pokerChipDistribution, 10, Color.YELLOW, Denomination.FIVE_CENTS);
        assertChipRoll(pokerChipDistribution, 10, Color.TAUPE, Denomination.ONE_CENT);
    }
}
