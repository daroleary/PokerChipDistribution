package com.solium.pcd.main;

import com.google.common.collect.ImmutableList;
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

        ImmutableList<ChipRoll> pokerChipDistribution = actualPlayer.getPokerChipDistribution();

        assertSize(6, pokerChipDistribution);
        assertChipRoll(pokerChipDistribution.get(0), Denomination.TWO_DOLLARS, 0);
        assertChipRoll(pokerChipDistribution.get(1), Denomination.ONE_DOLLAR, 1);
        assertChipRoll(pokerChipDistribution.get(2), Denomination.FIFTY_CENT, 10);
        assertChipRoll(pokerChipDistribution.get(3), Denomination.TWENTY_FIVE_CENTS, 10);
        assertChipRoll(pokerChipDistribution.get(4), Denomination.TEN_CENTS, 10);
        assertChipRoll(pokerChipDistribution.get(5), Denomination.FIVE_CENTS, 10);
    }

    @Test
    public void main_problemTwo_shouldReturnOptimumDistribution() {
        Player actualPlayer = PokerDistributionAssignment.optimumPokerChipDistributionFor("data/problem2.txt");

        ImmutableList<ChipRoll> pokerChipDistribution = actualPlayer.getPokerChipDistribution();

        assertSize(6, pokerChipDistribution);
        assertChipRoll(pokerChipDistribution.get(0), Denomination.TWO_DOLLARS, 1);
        assertChipRoll(pokerChipDistribution.get(1), Denomination.ONE_DOLLAR, 1);
        assertChipRoll(pokerChipDistribution.get(2), Denomination.FIFTY_CENT, 6);
        assertChipRoll(pokerChipDistribution.get(3), Denomination.TWENTY_FIVE_CENTS, 10);
        assertChipRoll(pokerChipDistribution.get(4), Denomination.TEN_CENTS, 10);
        assertChipRoll(pokerChipDistribution.get(5), Denomination.FIVE_CENTS, 10);
    }

    @Test
    public void main_problemThree_shouldReturnOptimumDistribution() {
        Player actualPlayer = PokerDistributionAssignment.optimumPokerChipDistributionFor("data/problem3.txt");

        ImmutableList<ChipRoll> pokerChipDistribution = actualPlayer.getPokerChipDistribution();

        assertSize(6, pokerChipDistribution);
        assertChipRoll(pokerChipDistribution.get(0), 4, Color.RED, Denomination.ONE_DOLLAR);
        assertChipRoll(pokerChipDistribution.get(1), 4, Color.BLUE, Denomination.FIFTY_CENT);
        assertChipRoll(pokerChipDistribution.get(2), 10, Color.BLACK, Denomination.TWENTY_FIVE_CENTS);
        assertChipRoll(pokerChipDistribution.get(3), 9, Color.GREEN, Denomination.TEN_CENTS);
        assertChipRoll(pokerChipDistribution.get(4), 10, Color.YELLOW, Denomination.FIVE_CENTS);
        assertChipRoll(pokerChipDistribution.get(5), 10, Color.TAUPE, Denomination.ONE_CENT);
    }
}
