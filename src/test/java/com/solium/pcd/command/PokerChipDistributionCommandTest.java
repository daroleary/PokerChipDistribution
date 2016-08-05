package com.solium.pcd.command;

import com.google.common.collect.ImmutableList;
import com.solium.pcd.domain.ChipRoll;
import com.solium.pcd.domain.Color;
import com.solium.pcd.domain.Denomination;
import com.solium.pcd.domain.Player;
import com.solium.pcd.exception.AlgorithmException;
import com.solium.pcd.exception.CalculationException;
import com.solium.pcd.exception.MapperException;
import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.util.TestCase;
import org.junit.Test;

public class PokerChipDistributionCommandTest extends TestCase {

    @Test
    public void execute_withProblemOne_shouldReturnOptimumDistribution() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        Player actualPlayer = new PokerChipDistributionCommand().execute(TestCase.getProblemOneTestCaseOneData());

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
    public void execute_withProblemOnePartTwo_shouldReturnOptimumDistribution() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        Player actualPlayer = new PokerChipDistributionCommand().execute(getTestCaseFourData());

        ImmutableList<ChipRoll> pokerChipDistribution = actualPlayer.getPokerChipDistribution();

        assertSize(3, pokerChipDistribution);
        assertChipRoll(pokerChipDistribution.get(0), Denomination.ONE_DOLLAR, 1);
        assertChipRoll(pokerChipDistribution.get(1), Denomination.SEVENTY_FIVE_CENT, 3);
        assertChipRoll(pokerChipDistribution.get(2), Denomination.TEN_CENTS, 1);
    }

    @Test
    public void execute_withProblemTwo_shouldReturnOptimumDistribution() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        Player actualPlayer = new PokerChipDistributionCommand().execute(TestCase.getProblemOneTestCaseTwoData());

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
    public void execute_withProblemThree_shouldReturnOptimumDistribution() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        Player actualPlayer = new PokerChipDistributionCommand().execute(TestCase.getProblemOneTestCaseThreeData());

        ImmutableList<ChipRoll> pokerChipDistribution = actualPlayer.getPokerChipDistribution();

        assertSize(6, pokerChipDistribution);
        assertChipRoll(pokerChipDistribution.get(0), 4, Color.RED, Denomination.ONE_DOLLAR);
        assertChipRoll(pokerChipDistribution.get(1), 4, Color.BLUE, Denomination.FIFTY_CENT);
        assertChipRoll(pokerChipDistribution.get(2), 10, Color.BLACK, Denomination.TWENTY_FIVE_CENTS);
        assertChipRoll(pokerChipDistribution.get(3), 9, Color.GREEN, Denomination.TEN_CENTS);
        assertChipRoll(pokerChipDistribution.get(4), 10, Color.YELLOW, Denomination.FIVE_CENTS);
        assertChipRoll(pokerChipDistribution.get(5), 10, Color.TAUPE, Denomination.ONE_CENT);
    }

    private ImmutableList<String> getTestCaseFourData() {

        ImmutableList.Builder<String> pokerDetails = new ImmutableList.Builder<>();

        pokerDetails.add("2/$1.00,3/$0.75,1/$0.10");
        pokerDetails.add("1");
        pokerDetails.add("$3.35");

        return pokerDetails.build();
    }
}
