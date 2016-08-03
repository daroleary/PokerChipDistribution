package com.solium.pcd.bo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedMap;
import com.solium.pcd.domain.ChipRoll;
import com.solium.pcd.domain.Denomination;
import com.solium.pcd.domain.Player;
import com.solium.pcd.exception.AlgorithmException;
import com.solium.pcd.exception.CalculationException;
import com.solium.pcd.exception.MapperException;
import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.mapper.PokerChipForBonusOneMapper;
import com.solium.pcd.util.TestCase;
import com.solium.pcd.util.TestUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PokerChipDistributionForBonusOneStrategyTest extends TestCase {

    @Rule
    public ExpectedException _expectedException = ExpectedException.none();

    @Test
    public void calculate_withProblemTwo_shouldReturnOptimumDistribution() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        Player actualPlayer = new PokerChipDistributionForBonusOneStrategy()
                .calculate(new PokerChipForBonusOneMapper().mapFrom(getTestCaseOneData()));

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
    public void calculate_withMaxBuyInForProblemTwo_shouldReturnOptimumDistribution() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        Player actualPlayer = new PokerChipDistributionForBonusOneStrategy()
                .calculate(new PokerChipForBonusOneMapper().mapFrom(getTestCaseTwoData()));

        ImmutableSortedMap<Denomination, ChipRoll> pokerChipDistribution = actualPlayer.getPokerChipDistribution();

        assertEquals(6, pokerChipDistribution.size());
        assertChipRoll(pokerChipDistribution, Denomination.TWO_DOLLARS, 5);
        assertChipRoll(pokerChipDistribution, Denomination.ONE_DOLLAR, 5);
        assertChipRoll(pokerChipDistribution, Denomination.FIFTY_CENT, 10);
        assertChipRoll(pokerChipDistribution, Denomination.TWENTY_FIVE_CENTS, 10);
        assertChipRoll(pokerChipDistribution, Denomination.TEN_CENTS, 10);
        assertChipRoll(pokerChipDistribution, Denomination.FIVE_CENTS, 10);
    }

    @Test
    public void calculate_withMinBuyInForProblemTwo_shouldReturnOptimumDistribution() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        Player actualPlayer = new PokerChipDistributionForBonusOneStrategy()
                .calculate(new PokerChipForBonusOneMapper().mapFrom(getTestCaseThreeData()));

        ImmutableSortedMap<Denomination, ChipRoll> pokerChipDistribution = actualPlayer.getPokerChipDistribution();

        assertEquals(6, pokerChipDistribution.size());
        assertChipRoll(pokerChipDistribution, Denomination.TWO_DOLLARS, 1);
        assertChipRoll(pokerChipDistribution, Denomination.ONE_DOLLAR, 1);
        assertChipRoll(pokerChipDistribution, Denomination.FIFTY_CENT, 1);
        assertChipRoll(pokerChipDistribution, Denomination.TWENTY_FIVE_CENTS, 1);
        assertChipRoll(pokerChipDistribution, Denomination.TEN_CENTS, 1);
        assertChipRoll(pokerChipDistribution, Denomination.FIVE_CENTS, 1);
    }

    @Test
    public void calculate_withGreaterThanAllowedBuyInForProblemTwo_shouldThrowCalculationException() throws AlgorithmException, CalculationException, MapperException, PokerChipException {
        _expectedException.expect(CalculationException.class);
        new PokerChipDistributionForBonusOneStrategy()
                .calculate(new PokerChipForBonusOneMapper().mapFrom(getTestCaseFourData()));
    }

    @Test
    public void calculate_withLessThanAllowedBuyInForProblemTwo_shouldThrowCalculationException() throws AlgorithmException, CalculationException, MapperException, PokerChipException {
        _expectedException.expect(IllegalArgumentException.class);
        _expectedException.expectMessage("Buy in must be greater or equal than zero");
        new PokerChipDistributionForBonusOneStrategy()
                .calculate(new PokerChipForBonusOneMapper().mapFrom(getTestCaseFiveData()));
    }

    private ImmutableList<String> getTestCaseOneData() {

        String chipBreakdown = "50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05";
        return TestUtil.getPokerChipDistributionListForBonusOneAndTwo("B1", chipBreakdown, "$10.00", "10");
    }

    private ImmutableList<String> getTestCaseTwoData() {

        String chipBreakdown = "50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05";
        return TestUtil.getPokerChipDistributionListForBonusOneAndTwo("B1", chipBreakdown, "$24.00", "10");
    }

    private ImmutableList<String> getTestCaseThreeData() {

        String chipBreakdown = "50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05";
        return TestUtil.getPokerChipDistributionListForBonusOneAndTwo("B1", chipBreakdown, "$3.90", "10");
    }

    private ImmutableList<String> getTestCaseFourData() {

        String chipBreakdown = "50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05";
        return TestUtil.getPokerChipDistributionListForBonusOneAndTwo("B1", chipBreakdown, "$25.00", "10");
    }

    private ImmutableList<String> getTestCaseFiveData() {

        String chipBreakdown = "50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05";
        return TestUtil.getPokerChipDistributionListForBonusOneAndTwo("B1", chipBreakdown, "$3.80", "10");
    }
}
