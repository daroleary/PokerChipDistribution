package com.solium.pcd.command;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedMap;
import com.solium.pcd.domain.ChipRoll;
import com.solium.pcd.domain.Color;
import com.solium.pcd.domain.Denomination;
import com.solium.pcd.domain.Player;
import com.solium.pcd.domain.PokerChip;
import com.solium.pcd.exception.AlgorithmException;
import com.solium.pcd.exception.CalculationException;
import com.solium.pcd.exception.MapperException;
import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.util.TestCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PokerChipDistributionCommandTest {

    @Test
    public void execute_withProblemOne_shouldReturnOptimumDistribution() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        Player actualPlayer = new PokerChipDistributionCommand().execute(TestCase.getProblemOneTestCaseOneData());

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
    public void execute_withProblemOnePartTwo_shouldReturnOptimumDistribution() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        Player actualPlayer = new PokerChipDistributionCommand().execute(getTestCaseFourData());

        ImmutableSortedMap<Denomination, ChipRoll> pokerChipDistribution = actualPlayer.getPokerChipDistribution();

        assertEquals(3, pokerChipDistribution.size());
        assertChipRoll(pokerChipDistribution, Denomination.ONE_DOLLAR, 1);
        assertChipRoll(pokerChipDistribution, Denomination.SEVENTY_FIVE_CENT, 3);
        assertChipRoll(pokerChipDistribution, Denomination.TEN_CENTS, 1);
    }

    @Test
    public void execute_withProblemTwo_shouldReturnOptimumDistribution() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        Player actualPlayer = new PokerChipDistributionCommand().execute(TestCase.getProblemOneTestCaseTwoData());

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
    public void execute_withProblemThree_shouldReturnOptimumDistribution() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        Player actualPlayer = new PokerChipDistributionCommand().execute(TestCase.getProblemOneTestCaseThreeData());

        ImmutableSortedMap<Denomination, ChipRoll> pokerChipDistribution = actualPlayer.getPokerChipDistribution();

        assertEquals(6, pokerChipDistribution.size());
        assertChipRoll(pokerChipDistribution, 4, Color.RED, Denomination.ONE_DOLLAR);
        assertChipRoll(pokerChipDistribution, 4, Color.BLUE, Denomination.FIFTY_CENT);
        assertChipRoll(pokerChipDistribution, 10, Color.BLACK, Denomination.TWENTY_FIVE_CENTS);
        assertChipRoll(pokerChipDistribution, 9, Color.GREEN, Denomination.TEN_CENTS);
        assertChipRoll(pokerChipDistribution, 10, Color.YELLOW, Denomination.FIVE_CENTS);
        assertChipRoll(pokerChipDistribution, 10, Color.TAUPE, Denomination.ONE_CENT);
    }

    private ImmutableList<String> getTestCaseFourData() {

        ImmutableList.Builder<String> pokerDetails = new ImmutableList.Builder<>();

        pokerDetails.add("2/$1.00,3/$0.75,1/$0.10");
        pokerDetails.add("1");
        pokerDetails.add("$3.35");

        return pokerDetails.build();
    }

    private void assertChipRoll(ImmutableSortedMap<Denomination, ChipRoll> pokerChipDistribution,
                                Denomination expectedDenomination,
                                int expectedQuantity) {
        assertChipRoll(pokerChipDistribution,
                       expectedQuantity,
                       Color.UNKNOWN,
                       expectedDenomination);
    }

    private void assertChipRoll(ImmutableSortedMap<Denomination, ChipRoll> pokerChipDistribution,
                                int expectedQuantity,
                                Color color,
                                Denomination expectedDenomination) {

        PokerChip expectedPokerChip = PokerChip.newBuilder()
                .setColor(color)
                .setDenomination(expectedDenomination)
                .build();

        ChipRoll chipRoll = pokerChipDistribution.get(expectedPokerChip.getDenomination());
        assertEquals(expectedQuantity, chipRoll.getQuantity());
        assertEquals(expectedPokerChip, chipRoll.getPokerChip());
    }
}
