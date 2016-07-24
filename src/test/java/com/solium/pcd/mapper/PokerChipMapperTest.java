package com.solium.pcd.mapper;

import com.solium.pcd.domain.Color;
import com.solium.pcd.domain.PokerChips;
import com.solium.pcd.exception.AlgorithmException;
import com.solium.pcd.exception.CalculationException;
import com.solium.pcd.exception.MapperException;
import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.util.Constants.Algorithm;
import com.solium.pcd.util.TestCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PokerChipMapperTest {

    @Test
    public void shouldReturnOptimumDistributionForProblemOne() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        PokerChips actual = new PokerChipMapper().mapFrom(TestCase.getTestCaseOneData());

        assertEquals(Algorithm.BASIC, actual.getAlgorithm());
        assertEquals("$0.05", actual.get(0).getDenominationInDollars());
        assertEquals(10, actual.get(0).getQuantity());
        assertEquals("$0.10", actual.get(1).getDenominationInDollars());
        assertEquals(10, actual.get(1).getQuantity());
        assertEquals("$0.25", actual.get(2).getDenominationInDollars());
        assertEquals(10, actual.get(2).getQuantity());
        assertEquals("$0.50", actual.get(3).getDenominationInDollars());
        assertEquals(10, actual.get(3).getQuantity());
        assertEquals("$1.00", actual.get(4).getDenominationInDollars());
        assertEquals(5, actual.get(4).getQuantity());
        assertEquals("$2.00", actual.get(5).getDenominationInDollars());
        assertEquals(5, actual.get(5).getQuantity());
    }

    @Test
    public void shouldReturnOptimumDistributionForProblemTwo() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        PokerChips actual = new PokerChipForBonusOneMapper().mapFrom(TestCase.getTestCaseTwoData());

        assertEquals(Algorithm.BONUS_ONE, actual.getAlgorithm());
        assertEquals("$0.05", actual.get(0).getDenominationInDollars());
        assertEquals(10, actual.get(0).getQuantity());
        assertEquals("$0.10", actual.get(1).getDenominationInDollars());
        assertEquals(10, actual.get(1).getQuantity());
        assertEquals("$0.25", actual.get(2).getDenominationInDollars());
        assertEquals(10, actual.get(2).getQuantity());
        assertEquals("$0.50", actual.get(3).getDenominationInDollars());
        assertEquals(10, actual.get(3).getQuantity());
        assertEquals("$1.00", actual.get(4).getDenominationInDollars());
        assertEquals(5, actual.get(4).getQuantity());
        assertEquals("$2.00", actual.get(5).getDenominationInDollars());
        assertEquals(5, actual.get(5).getQuantity());
    }

    @Test
    public void shouldReturnOptimumDistributionForProblemThree() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        PokerChips actual = new PokerChipForBonusTwoMapper().mapFrom(TestCase.getTestCaseThreeData());

        assertEquals(Algorithm.BONUS_TWO, actual.getAlgorithm());
        assertEquals("$0.01", actual.get(0).getDenominationInDollars());
        assertEquals(10, actual.get(0).getQuantity());
        assertEquals(Color.TAUPE, actual.get(0).getColor());
        assertEquals("$0.05", actual.get(1).getDenominationInDollars());
        assertEquals(10, actual.get(1).getQuantity());
        assertEquals(Color.YELLOW, actual.get(1).getColor());
        assertEquals("$0.10", actual.get(2).getDenominationInDollars());
        assertEquals(10, actual.get(2).getQuantity());
        assertEquals(Color.GREEN, actual.get(2).getColor());
        assertEquals("$0.25", actual.get(3).getDenominationInDollars());
        assertEquals(10, actual.get(3).getQuantity());
        assertEquals(Color.BLACK, actual.get(3).getColor());
        assertEquals("$0.50", actual.get(4).getDenominationInDollars());
        assertEquals(5, actual.get(4).getQuantity());
        assertEquals(Color.BLUE, actual.get(4).getColor());
        assertEquals("$1.00", actual.get(5).getDenominationInDollars());
        assertEquals(5, actual.get(5).getQuantity());
        assertEquals(Color.RED, actual.get(5).getColor());
    }
}
