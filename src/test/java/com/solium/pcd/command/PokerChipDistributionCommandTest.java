package com.solium.pcd.command;

import com.solium.pcd.domain.PokerChips;
import com.solium.pcd.exception.AlgorithmException;
import com.solium.pcd.exception.CalculationException;
import com.solium.pcd.exception.MapperException;
import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.util.Constants.Algorithm;
import com.solium.pcd.util.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PokerChipDistributionCommandTest {

    @Test
    public void shouldReturnOptimumDistributionForProblemOne() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        PokerChips actual = new PokerChipDistributionCommand().execute(TestCase.getTestCaseOneData());

        assertEquals(Algorithm.BASIC, actual.getAlgorithm());
        assertEquals("$2.00", actual.get(0).getDenominationInDollars());
        assertEquals(0, actual.get(0).getBuyInQuantity());
        assertEquals("$1.00", actual.get(1).getDenominationInDollars());
        assertEquals(1, actual.get(1).getBuyInQuantity());
        assertEquals("$0.50", actual.get(2).getDenominationInDollars());
        assertEquals(10, actual.get(2).getBuyInQuantity());
        assertEquals("$0.25", actual.get(3).getDenominationInDollars());
        assertEquals(10, actual.get(3).getBuyInQuantity());
        assertEquals("$0.10", actual.get(4).getDenominationInDollars());
        assertEquals(10, actual.get(4).getBuyInQuantity());
        assertEquals("$0.05", actual.get(5).getDenominationInDollars());
        assertEquals(10, actual.get(5).getBuyInQuantity());
    }

    @Test
    public void shouldReturnOptimumDistributionForProblemOnePartTwo() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        PokerChips actual = new PokerChipDistributionCommand().execute(getTestCaseFourData());

        assertEquals(Algorithm.BASIC, actual.getAlgorithm());
        assertEquals("$1.00", actual.get(0).getDenominationInDollars());
        assertEquals(1, actual.get(0).getBuyInQuantity());
        assertEquals("$0.75", actual.get(1).getDenominationInDollars());
        assertEquals(3, actual.get(1).getBuyInQuantity());
        assertEquals("$0.10", actual.get(2).getDenominationInDollars());
        assertEquals(1, actual.get(2).getBuyInQuantity());
    }

    @Test
    public void shouldReturnOptimumDistributionForProblemTwo() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        PokerChips actual = new PokerChipDistributionCommand().execute(TestCase.getTestCaseTwoData());

        assertEquals(Algorithm.BONUS_ONE, actual.getAlgorithm());
        assertEquals("$2.00", actual.get(0).getDenominationInDollars());
        assertEquals(1, actual.get(0).getBuyInQuantity());
        assertEquals("$1.00", actual.get(1).getDenominationInDollars());
        assertEquals(1, actual.get(1).getBuyInQuantity());
        assertEquals("$0.50", actual.get(2).getDenominationInDollars());
        assertEquals(6, actual.get(2).getBuyInQuantity());
        assertEquals("$0.25", actual.get(3).getDenominationInDollars());
        assertEquals(10, actual.get(3).getBuyInQuantity());
        assertEquals("$0.10", actual.get(4).getDenominationInDollars());
        assertEquals(10, actual.get(4).getBuyInQuantity());
        assertEquals("$0.05", actual.get(5).getDenominationInDollars());
        assertEquals(10, actual.get(5).getBuyInQuantity());
    }

    @Test
    public void shouldReturnOptimumDistributionForProblemThree() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        PokerChips actual = new PokerChipDistributionCommand().execute(TestCase.getTestCaseThreeData());

        assertEquals(Algorithm.BONUS_TWO, actual.getAlgorithm());
        assertEquals("$1.00", actual.get(0).getDenominationInDollars());
        assertEquals(4, actual.get(0).getBuyInQuantity());
        assertEquals("$0.50", actual.get(1).getDenominationInDollars());
        assertEquals(4, actual.get(1).getBuyInQuantity());
        assertEquals("$0.25", actual.get(2).getDenominationInDollars());
        assertEquals(10, actual.get(2).getBuyInQuantity());
        assertEquals("$0.10", actual.get(3).getDenominationInDollars());
        assertEquals(9, actual.get(3).getBuyInQuantity());
        assertEquals("$0.05", actual.get(4).getDenominationInDollars());
        assertEquals(10, actual.get(4).getBuyInQuantity());
        assertEquals("$0.01", actual.get(5).getDenominationInDollars());
        assertEquals(10, actual.get(5).getBuyInQuantity());
    }

    private List<String> getTestCaseFourData() {

        List<String> pokerDetailsList = new ArrayList<>();

        pokerDetailsList.add("2/$1.00,3/$0.75,1/$0.10");
        pokerDetailsList.add("1");
        pokerDetailsList.add("$3.35");

        return pokerDetailsList;
    }
}
