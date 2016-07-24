package com.solium.pcd.bo;

import com.google.common.collect.ImmutableList;
import com.solium.pcd.domain.PokerChips;
import com.solium.pcd.exception.AlgorithmException;
import com.solium.pcd.exception.CalculationException;
import com.solium.pcd.exception.MapperException;
import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.mapper.PokerChipForBonusTwoMapper;
import com.solium.pcd.util.TestUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PokerChipDistributionForBonusTwoStrategyTest {

    @Test
    public void shouldReturnOptimumDistributionForProblemThree() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        PokerChips actual = new PokerChipDistributionForBonusTwoStrategy()
                .calculate(new PokerChipForBonusTwoMapper().mapFrom(getTestCaseOneData()));

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

    @Test
    public void shouldReturnOptimumDistributionOfMaxBuyInForProblemThree() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        PokerChips actual = new PokerChipDistributionForBonusTwoStrategy()
                .calculate(new PokerChipForBonusTwoMapper().mapFrom(getTestCaseTwoData()));

        assertEquals("$1.00", actual.get(0).getDenominationInDollars());
        assertEquals(5, actual.get(0).getBuyInQuantity());
        assertEquals("$0.50", actual.get(1).getDenominationInDollars());
        assertEquals(5, actual.get(1).getBuyInQuantity());
        assertEquals("$0.25", actual.get(2).getDenominationInDollars());
        assertEquals(10, actual.get(2).getBuyInQuantity());
        assertEquals("$0.10", actual.get(3).getDenominationInDollars());
        assertEquals(10, actual.get(3).getBuyInQuantity());
        assertEquals("$0.05", actual.get(4).getDenominationInDollars());
        assertEquals(10, actual.get(4).getBuyInQuantity());
        assertEquals("$0.01", actual.get(5).getDenominationInDollars());
        assertEquals(10, actual.get(5).getBuyInQuantity());
    }

    @Test
    public void shouldReturnOptimumDistributionOfMinBuyInForProblemThree() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        PokerChips actual = new PokerChipDistributionForBonusTwoStrategy()
                .calculate(new PokerChipForBonusTwoMapper().mapFrom(getTestCaseThreeData()));

        assertEquals("$1.00", actual.get(0).getDenominationInDollars());
        assertEquals(0, actual.get(0).getBuyInQuantity());
        assertEquals("$0.50", actual.get(1).getDenominationInDollars());
        assertEquals(0, actual.get(1).getBuyInQuantity());
        assertEquals("$0.25", actual.get(2).getDenominationInDollars());
        assertEquals(0, actual.get(2).getBuyInQuantity());
        assertEquals("$0.10", actual.get(3).getDenominationInDollars());
        assertEquals(0, actual.get(3).getBuyInQuantity());
        assertEquals("$0.05", actual.get(4).getDenominationInDollars());
        assertEquals(0, actual.get(4).getBuyInQuantity());
        assertEquals("$0.01", actual.get(5).getDenominationInDollars());
        assertEquals(1, actual.get(5).getBuyInQuantity());
    }

    @Test(expected = CalculationException.class)
    public void shouldThrowCalculationExceptionForGreaterThanAllowedBuyInForProblemThree() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        new PokerChipDistributionForBonusTwoStrategy()
                .calculate(new PokerChipForBonusTwoMapper().mapFrom(getTestCaseFourData()));
    }

    private ImmutableList<String> getTestCaseOneData() throws PokerChipException {

        String chipBreakdown = "50/Red,50/Blue,100/Black,100/Green,100/Yellow,100/Taupe";
        return TestUtil.getPokerChipDistributionListForBonusOneAndTwo("B2", chipBreakdown, "$10.00", "10");
    }

    private ImmutableList<String> getTestCaseTwoData() throws PokerChipException {

        String chipBreakdown = "50/Red,50/Blue,100/Black,100/Green,100/Yellow,100/Taupe";
        return TestUtil.getPokerChipDistributionListForBonusOneAndTwo("B2", chipBreakdown, "$11.60", "10");
    }

    private ImmutableList<String> getTestCaseThreeData() throws PokerChipException {

        String chipBreakdown = "50/Red,50/Blue,100/Black,100/Green,100/Yellow,100/Taupe";
        return TestUtil.getPokerChipDistributionListForBonusOneAndTwo("B2", chipBreakdown, "$0.01", "10");
    }

    private ImmutableList<String> getTestCaseFourData() throws PokerChipException {

        String chipBreakdown = "50/Red,50/Blue,100/Black,100/Green,100/Yellow,100/Taupe";
        return TestUtil.getPokerChipDistributionListForBonusOneAndTwo("B2", chipBreakdown, "$11.70", "10");
    }
}
