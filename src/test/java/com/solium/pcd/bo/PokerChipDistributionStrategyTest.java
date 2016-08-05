package com.solium.pcd.bo;

import com.google.common.collect.ImmutableList;
import com.solium.pcd.domain.ChipRoll;
import com.solium.pcd.domain.Denomination;
import com.solium.pcd.domain.Player;
import com.solium.pcd.exception.AlgorithmException;
import com.solium.pcd.exception.CalculationException;
import com.solium.pcd.exception.MapperException;
import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.mapper.PokerChipMapper;
import com.solium.pcd.util.TestCase;
import com.solium.pcd.util.TestUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PokerChipDistributionStrategyTest extends TestCase {

    @Rule
    public ExpectedException _expectedException = ExpectedException.none();

    @Test
    public void calculate_withProblemOne_shouldReturnOptimumDistribution() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        Player actualPlayer = new PokerChipDistributionStrategy()
                .calculate(new PokerChipMapper().mapFrom(getTestCaseOneData()));

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
    public void calculate_withMaxBuyInForProblemOne_shouldReturnOptimumDistribution() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        Player actualPlayer = new PokerChipDistributionStrategy()
                .calculate(new PokerChipMapper().mapFrom(getTestCaseTwoData()));

        ImmutableList<ChipRoll> pokerChipDistribution = actualPlayer.getPokerChipDistribution();

        assertSize(6, pokerChipDistribution);
        assertChipRoll(pokerChipDistribution.get(0), Denomination.TWO_DOLLARS, 5);
        assertChipRoll(pokerChipDistribution.get(1), Denomination.ONE_DOLLAR, 5);
        assertChipRoll(pokerChipDistribution.get(2), Denomination.FIFTY_CENT, 10);
        assertChipRoll(pokerChipDistribution.get(3), Denomination.TWENTY_FIVE_CENTS, 10);
        assertChipRoll(pokerChipDistribution.get(4), Denomination.TEN_CENTS, 10);
        assertChipRoll(pokerChipDistribution.get(5), Denomination.FIVE_CENTS, 10);
    }


    @Test
    public void calculate_withMinBuyInForProblemOne_shouldReturnOptimumDistribution() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        Player actualPlayer = new PokerChipDistributionStrategy()
                .calculate(new PokerChipMapper().mapFrom(getTestCaseThreeData()));

        ImmutableList<ChipRoll> pokerChipDistribution = actualPlayer.getPokerChipDistribution();

        assertSize(6, pokerChipDistribution);
        assertChipRoll(pokerChipDistribution.get(0), Denomination.TWO_DOLLARS, 0);
        assertChipRoll(pokerChipDistribution.get(1), Denomination.ONE_DOLLAR, 0);
        assertChipRoll(pokerChipDistribution.get(2), Denomination.FIFTY_CENT, 0);
        assertChipRoll(pokerChipDistribution.get(3), Denomination.TWENTY_FIVE_CENTS, 0);
        assertChipRoll(pokerChipDistribution.get(4), Denomination.TEN_CENTS, 0);
        assertChipRoll(pokerChipDistribution.get(5), Denomination.FIVE_CENTS, 1);
    }

    @Test
    public void calculate_withGreaterThanAllowedBuyInForProblemOne_shouldThrowCalculationException() throws Exception {
        _expectedException.expect(CalculationException.class);
        new PokerChipDistributionStrategy()
                .calculate(new PokerChipMapper().mapFrom(getTestCaseFourData()));
    }

    @Test
    public void calculate_withLessThanAllowedBuyInForProblemOne_shouldThrowCalculationException() throws AlgorithmException, CalculationException, MapperException, PokerChipException {
        _expectedException.expect(CalculationException.class);
        new PokerChipDistributionStrategy()
                .calculate(new PokerChipMapper().mapFrom(getTestCaseFiveData()));
    }

    private ImmutableList<String> getTestCaseOneData() {

        String chipBreakdown = "50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05";
        return TestUtil.getPokerChipDistributionList(chipBreakdown, "$10.00", "10");
    }

    private ImmutableList<String> getTestCaseTwoData() {

        String chipBreakdown = "50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05";
        return TestUtil.getPokerChipDistributionList(chipBreakdown, "$24.00", "10");
    }

    private ImmutableList<String> getTestCaseThreeData() {

        String chipBreakdown = "50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05";
        return TestUtil.getPokerChipDistributionList(chipBreakdown, "$0.05", "10");
    }

    private ImmutableList<String> getTestCaseFourData() {

        String chipBreakdown = "50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05";
        return TestUtil.getPokerChipDistributionList(chipBreakdown, "$25.00", "10");
    }

    private ImmutableList<String> getTestCaseFiveData() {

        String chipBreakdown = "50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05";
        return TestUtil.getPokerChipDistributionList(chipBreakdown, "$0.04", "10");
    }
}
