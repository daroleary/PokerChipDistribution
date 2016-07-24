package com.solium.pcd.bo;

import com.google.common.collect.ImmutableList;
import com.solium.pcd.domain.PokerChips;
import com.solium.pcd.exception.AlgorithmException;
import com.solium.pcd.exception.CalculationException;
import com.solium.pcd.exception.MapperException;
import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.mapper.PokerChipForBonusOneMapper;
import com.solium.pcd.util.TestUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PokerChipDistributionForBonusOneStrategyTest {

    @Test
    public void shouldReturnOptimumDistributionForProblemTwo() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        PokerChips actual = new PokerChipDistributionForBonusOneStrategy()
                .calculate(new PokerChipForBonusOneMapper().mapFrom(getTestCaseOneData()));

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
    public void shouldReturnOptimumDistributionOfMaxBuyInForProblemTwo() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        PokerChips actual = new PokerChipDistributionForBonusOneStrategy()
                .calculate(new PokerChipForBonusOneMapper().mapFrom(getTestCaseTwoData()));

        assertEquals("$2.00", actual.get(0).getDenominationInDollars());
        assertEquals(5, actual.get(0).getBuyInQuantity());
        assertEquals("$1.00", actual.get(1).getDenominationInDollars());
        assertEquals(5, actual.get(1).getBuyInQuantity());
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
    public void shouldReturnOptimumDistributionOfMinBuyInForProblemTwo() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        PokerChips actual = new PokerChipDistributionForBonusOneStrategy()
                .calculate(new PokerChipForBonusOneMapper().mapFrom(getTestCaseThreeData()));

        assertEquals("$2.00", actual.get(0).getDenominationInDollars());
        assertEquals(1, actual.get(0).getBuyInQuantity());
        assertEquals("$1.00", actual.get(1).getDenominationInDollars());
        assertEquals(1, actual.get(1).getBuyInQuantity());
        assertEquals("$0.50", actual.get(2).getDenominationInDollars());
        assertEquals(1, actual.get(2).getBuyInQuantity());
        assertEquals("$0.25", actual.get(3).getDenominationInDollars());
        assertEquals(1, actual.get(3).getBuyInQuantity());
        assertEquals("$0.10", actual.get(4).getDenominationInDollars());
        assertEquals(1, actual.get(4).getBuyInQuantity());
        assertEquals("$0.05", actual.get(5).getDenominationInDollars());
        assertEquals(1, actual.get(5).getBuyInQuantity());
    }

    @Test(expected = CalculationException.class)
    public void shouldThrowCalculationExceptionForGreaterThanAllowedBuyInForProblemTwo() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        new PokerChipDistributionForBonusOneStrategy()
                .calculate(new PokerChipForBonusOneMapper().mapFrom(getTestCaseFourData()));
    }

    @Test(expected = CalculationException.class)
    public void shouldThrowCalculationExceptionForLessThanAllowedBuyInForProblemTwo() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        new PokerChipDistributionForBonusOneStrategy()
                .calculate(new PokerChipForBonusOneMapper().mapFrom(getTestCaseFiveData()));
    }

    private ImmutableList<String> getTestCaseOneData() throws PokerChipException {

        String chipBreakdown = "50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05";
        return TestUtil.getPokerChipDistributionListForBonusOneAndTwo("B1", chipBreakdown, "$10.00", "10");
    }

    private ImmutableList<String> getTestCaseTwoData() throws PokerChipException {

        String chipBreakdown = "50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05";
        return TestUtil.getPokerChipDistributionListForBonusOneAndTwo("B1", chipBreakdown, "$24.00", "10");
    }

    private ImmutableList<String> getTestCaseThreeData() throws PokerChipException {

        String chipBreakdown = "50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05";
        return TestUtil.getPokerChipDistributionListForBonusOneAndTwo("B1", chipBreakdown, "$3.90", "10");
    }

    private ImmutableList<String> getTestCaseFourData() throws PokerChipException {

        String chipBreakdown = "50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05";
        return TestUtil.getPokerChipDistributionListForBonusOneAndTwo("B1", chipBreakdown, "$25.00", "10");
    }

    private ImmutableList<String> getTestCaseFiveData() throws PokerChipException {

        String chipBreakdown = "50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05";
        return TestUtil.getPokerChipDistributionListForBonusOneAndTwo("B1", chipBreakdown, "$3.80", "10");
    }
}
