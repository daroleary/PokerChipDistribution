package com.solium.pcd.mapper;

import com.google.common.collect.ImmutableList;
import com.solium.pcd.domain.Algorithm;
import com.solium.pcd.domain.ChipRoll;
import com.solium.pcd.domain.Color;
import com.solium.pcd.domain.Denomination;
import com.solium.pcd.domain.PokerChip;
import com.solium.pcd.domain.PokerTable;
import com.solium.pcd.exception.AlgorithmException;
import com.solium.pcd.exception.CalculationException;
import com.solium.pcd.exception.MapperException;
import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.math.Amount;
import com.solium.pcd.util.TestCase;
import org.junit.Test;

public class PokerChipMapperTest extends TestCase {

    @Test
    public void mapFrom_problemOne_shouldReturnCorrectPokerTable() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        PokerTable actualPokerTable = new PokerChipMapper().mapFrom(getProblemOneTestCaseOneData());
        assertEquals(Algorithm.BASIC, actualPokerTable.getAlgorithm());
        assertEquals(Amount.of(10.0), actualPokerTable.getBuyIn());

        ImmutableList<ChipRoll> pokerChipCollection = actualPokerTable.getPokerChipCollection();
        assertSize(6, pokerChipCollection);
        assertChipRoll(pokerChipCollection.get(0), Denomination.FIVE_CENTS, 10);
        assertChipRoll(pokerChipCollection.get(1), Denomination.TEN_CENTS, 10);
        assertChipRoll(pokerChipCollection.get(2), Denomination.TWENTY_FIVE_CENTS, 10);
        assertChipRoll(pokerChipCollection.get(3), Denomination.FIFTY_CENT, 10);
        assertChipRoll(pokerChipCollection.get(4), Denomination.ONE_DOLLAR, 5);
        assertChipRoll(pokerChipCollection.get(5), Denomination.TWO_DOLLARS, 5);
    }

    @Test
    public void mapFrom_problemTwo_shouldReturnCorrectPokerTable() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        PokerTable actualPokerTable = new PokerChipForBonusOneMapper().mapFrom(getProblemOneTestCaseTwoData());
        assertEquals(Algorithm.BONUS_ONE, actualPokerTable.getAlgorithm());
        assertEquals(Amount.of(10.0), actualPokerTable.getBuyIn());

        ImmutableList<ChipRoll> pokerChipCollection = actualPokerTable.getPokerChipCollection();
        assertSize(6, pokerChipCollection);
        assertChipRoll(pokerChipCollection.get(0), Denomination.FIVE_CENTS, 10);
        assertChipRoll(pokerChipCollection.get(1), Denomination.TEN_CENTS, 10);
        assertChipRoll(pokerChipCollection.get(2), Denomination.TWENTY_FIVE_CENTS, 10);
        assertChipRoll(pokerChipCollection.get(3), Denomination.FIFTY_CENT, 10);
        assertChipRoll(pokerChipCollection.get(4), Denomination.ONE_DOLLAR, 5);
        assertChipRoll(pokerChipCollection.get(5), Denomination.TWO_DOLLARS, 5);
    }

    @Test
    public void shouldReturnOptimumDistributionForProblemThree() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        PokerTable actualPokerTable = new PokerChipForBonusTwoMapper().mapFrom(getProblemOneTestCaseThreeData());
        assertEquals(Algorithm.BONUS_TWO, actualPokerTable.getAlgorithm());
        assertEquals(Amount.of(10.0), actualPokerTable.getBuyIn());

        ImmutableList<ChipRoll> pokerChipCollection = actualPokerTable.getPokerChipCollection();
        assertSize(6, pokerChipCollection);
        assertChipRoll(pokerChipCollection.get(0), 10, Color.TAUPE, Denomination.ONE_CENT);
        assertChipRoll(pokerChipCollection.get(1), 10, Color.YELLOW, Denomination.FIVE_CENTS);
        assertChipRoll(pokerChipCollection.get(2), 10, Color.GREEN, Denomination.TEN_CENTS);
        assertChipRoll(pokerChipCollection.get(3), 10, Color.BLACK, Denomination.TWENTY_FIVE_CENTS);
        assertChipRoll(pokerChipCollection.get(4), 5, Color.BLUE, Denomination.FIFTY_CENT);
        assertChipRoll(pokerChipCollection.get(5), 5, Color.RED, Denomination.ONE_DOLLAR);
    }

    private PokerChip getPokerChipWithUnknownColor(Denomination denomination) {
        return getPokerChip(denomination, Color.UNKNOWN);
    }

    private PokerChip getPokerChip(Denomination denomination,
                                   Color color) {
        return PokerChip.newBuilder()
                .setDenomination(denomination)
                .setColor(color)
                .build();
    }
}
