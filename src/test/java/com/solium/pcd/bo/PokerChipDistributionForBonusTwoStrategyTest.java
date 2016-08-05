package com.solium.pcd.bo;

import com.google.common.collect.ImmutableList;
import com.solium.pcd.domain.ChipRoll;
import com.solium.pcd.domain.Color;
import com.solium.pcd.domain.Denomination;
import com.solium.pcd.domain.Player;
import com.solium.pcd.exception.AlgorithmException;
import com.solium.pcd.exception.CalculationException;
import com.solium.pcd.exception.MapperException;
import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.mapper.PokerChipForBonusTwoMapper;
import com.solium.pcd.util.TestCase;
import com.solium.pcd.util.TestUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PokerChipDistributionForBonusTwoStrategyTest extends TestCase {

    @Rule
    public ExpectedException _expectedException = ExpectedException.none();

    @Test
    public void calculate_withProblemThree_shouldReturnOptimumDistribution() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        Player actualPlayer = new PokerChipDistributionForBonusTwoStrategy()
                .calculate(new PokerChipForBonusTwoMapper().mapFrom(getTestCaseOneData()));

        ImmutableList<ChipRoll> pokerChipDistribution = actualPlayer.getPokerChipDistribution();

        assertSize(6, pokerChipDistribution);
        assertChipRoll(pokerChipDistribution.get(0), 4, Color.RED, Denomination.ONE_DOLLAR);
        assertChipRoll(pokerChipDistribution.get(1), 4, Color.BLUE, Denomination.FIFTY_CENT);
        assertChipRoll(pokerChipDistribution.get(2), 10, Color.BLACK, Denomination.TWENTY_FIVE_CENTS);
        assertChipRoll(pokerChipDistribution.get(3), 9, Color.GREEN, Denomination.TEN_CENTS);
        assertChipRoll(pokerChipDistribution.get(4), 10, Color.YELLOW, Denomination.FIVE_CENTS);
        assertChipRoll(pokerChipDistribution.get(5), 10, Color.TAUPE, Denomination.ONE_CENT);
    }

    @Test
    public void calculate_withMaxBuyInForProblemThree_shouldReturnOptimumDistribution() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        Player actualPlayer = new PokerChipDistributionForBonusTwoStrategy()
                .calculate(new PokerChipForBonusTwoMapper().mapFrom(getTestCaseTwoData()));

        ImmutableList<ChipRoll> pokerChipDistribution = actualPlayer.getPokerChipDistribution();

        assertSize(6, pokerChipDistribution);
        assertChipRoll(pokerChipDistribution.get(0), 5, Color.RED, Denomination.ONE_DOLLAR);
        assertChipRoll(pokerChipDistribution.get(1), 5, Color.BLUE, Denomination.FIFTY_CENT);
        assertChipRoll(pokerChipDistribution.get(2), 10, Color.BLACK, Denomination.TWENTY_FIVE_CENTS);
        assertChipRoll(pokerChipDistribution.get(3), 10, Color.GREEN, Denomination.TEN_CENTS);
        assertChipRoll(pokerChipDistribution.get(4), 10, Color.YELLOW, Denomination.FIVE_CENTS);
        assertChipRoll(pokerChipDistribution.get(5), 10, Color.TAUPE, Denomination.ONE_CENT);
    }

    @Test
    public void calculate_withMinBuyInForProblemThree_shouldReturnOptimumDistribution() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        Player actualPlayer = new PokerChipDistributionForBonusTwoStrategy()
                .calculate(new PokerChipForBonusTwoMapper().mapFrom(getTestCaseThreeData()));

        ImmutableList<ChipRoll> pokerChipDistribution = actualPlayer.getPokerChipDistribution();

        assertSize(6, pokerChipDistribution);
        assertChipRoll(pokerChipDistribution.get(0), 0, Color.RED, Denomination.ONE_DOLLAR);
        assertChipRoll(pokerChipDistribution.get(1), 0, Color.BLUE, Denomination.FIFTY_CENT);
        assertChipRoll(pokerChipDistribution.get(2), 0, Color.BLACK, Denomination.TWENTY_FIVE_CENTS);
        assertChipRoll(pokerChipDistribution.get(3), 0, Color.GREEN, Denomination.TEN_CENTS);
        assertChipRoll(pokerChipDistribution.get(4), 0, Color.YELLOW, Denomination.FIVE_CENTS);
        assertChipRoll(pokerChipDistribution.get(5), 1, Color.TAUPE, Denomination.ONE_CENT);
    }

    @Test
    public void calculate_withGreaterThanAllowedBuyInForProblemThree_shouldThrowCalculationException() throws Exception {
        _expectedException.expect(CalculationException.class);
        new PokerChipDistributionForBonusTwoStrategy()
                .calculate(new PokerChipForBonusTwoMapper().mapFrom(getTestCaseFourData()));
    }

    private ImmutableList<String> getTestCaseOneData() {

        String chipBreakdown = "50/Red,50/Blue,100/Black,100/Green,100/Yellow,100/Taupe";
        return TestUtil.getPokerChipDistributionListForBonusOneAndTwo("B2", chipBreakdown, "$10.00", "10");
    }

    private ImmutableList<String> getTestCaseTwoData() {

        String chipBreakdown = "50/Red,50/Blue,100/Black,100/Green,100/Yellow,100/Taupe";
        return TestUtil.getPokerChipDistributionListForBonusOneAndTwo("B2", chipBreakdown, "$11.60", "10");
    }

    private ImmutableList<String> getTestCaseThreeData() {

        String chipBreakdown = "50/Red,50/Blue,100/Black,100/Green,100/Yellow,100/Taupe";
        return TestUtil.getPokerChipDistributionListForBonusOneAndTwo("B2", chipBreakdown, "$0.01", "10");
    }

    private ImmutableList<String> getTestCaseFourData() {

        String chipBreakdown = "50/Red,50/Blue,100/Black,100/Green,100/Yellow,100/Taupe";
        return TestUtil.getPokerChipDistributionListForBonusOneAndTwo("B2", chipBreakdown, "$11.70", "10");
    }
}
