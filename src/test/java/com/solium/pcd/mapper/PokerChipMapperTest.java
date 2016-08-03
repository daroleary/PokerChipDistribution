package com.solium.pcd.mapper;

import com.google.common.collect.ImmutableSortedMap;
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

import static org.junit.Assert.assertEquals;

public class PokerChipMapperTest {

    @Test
    public void mapFrom_problemOne_shouldReturnCorrectPokerTable() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        PokerTable actualPokerTable = new PokerChipMapper().mapFrom(TestCase.getProblemOneTestCaseOneData());
        assertEquals(Algorithm.BASIC, actualPokerTable.getAlgorithm());
        assertEquals(Amount.of(10.0), actualPokerTable.getBuyIn());

        ImmutableSortedMap<Denomination, ChipRoll> pokerChipCollection = actualPokerTable.getPokerChipCollection();
        assertEquals(6, pokerChipCollection.size());
        assertPokerChipFor(pokerChipCollection, Denomination.FIVE_CENTS, 10);
        assertPokerChipFor(pokerChipCollection, Denomination.TEN_CENTS, 10);
        assertPokerChipFor(pokerChipCollection, Denomination.TWENTY_FIVE_CENTS, 10);
        assertPokerChipFor(pokerChipCollection, Denomination.FIFTY_CENT, 10);
        assertPokerChipFor(pokerChipCollection, Denomination.ONE_DOLLAR, 5);
        assertPokerChipFor(pokerChipCollection, Denomination.TWO_DOLLARS, 5);
    }

    @Test
    public void mapFrom_problemTwo_shouldReturnCorrectPokerTable() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        PokerTable actualPokerTable = new PokerChipForBonusOneMapper().mapFrom(TestCase.getProblemOneTestCaseTwoData());
        assertEquals(Algorithm.BONUS_ONE, actualPokerTable.getAlgorithm());
        assertEquals(Amount.of(10.0), actualPokerTable.getBuyIn());

        ImmutableSortedMap<Denomination, ChipRoll> pokerChipCollection = actualPokerTable.getPokerChipCollection();
        assertEquals(6, pokerChipCollection.size());
        assertPokerChipFor(pokerChipCollection, Denomination.FIVE_CENTS, 10);
        assertPokerChipFor(pokerChipCollection, Denomination.TEN_CENTS, 10);
        assertPokerChipFor(pokerChipCollection, Denomination.TWENTY_FIVE_CENTS, 10);
        assertPokerChipFor(pokerChipCollection, Denomination.FIFTY_CENT, 10);
        assertPokerChipFor(pokerChipCollection, Denomination.ONE_DOLLAR, 5);
        assertPokerChipFor(pokerChipCollection, Denomination.TWO_DOLLARS, 5);
    }

    @Test
    public void shouldReturnOptimumDistributionForProblemThree() throws AlgorithmException, CalculationException, MapperException, PokerChipException {

        PokerTable actualPokerTable = new PokerChipForBonusTwoMapper().mapFrom(TestCase.getProblemOneTestCaseThreeData());
        assertEquals(Algorithm.BONUS_TWO, actualPokerTable.getAlgorithm());
        assertEquals(Amount.of(10.0), actualPokerTable.getBuyIn());

        ImmutableSortedMap<Denomination, ChipRoll> pokerChipCollection = actualPokerTable.getPokerChipCollection();
        assertEquals(6, pokerChipCollection.size());
        assertPokerChipFor(pokerChipCollection, getPokerChip(Denomination.ONE_CENT, Color.TAUPE), 10);
        assertPokerChipFor(pokerChipCollection, getPokerChip(Denomination.FIVE_CENTS, Color.YELLOW), 10);
        assertPokerChipFor(pokerChipCollection, getPokerChip(Denomination.TEN_CENTS, Color.GREEN), 10);
        assertPokerChipFor(pokerChipCollection, getPokerChip(Denomination.TWENTY_FIVE_CENTS, Color.BLACK), 10);
        assertPokerChipFor(pokerChipCollection, getPokerChip(Denomination.FIFTY_CENT, Color.BLUE), 5);
        assertPokerChipFor(pokerChipCollection, getPokerChip(Denomination.ONE_DOLLAR, Color.RED), 5);
    }

    private void assertPokerChipFor(ImmutableSortedMap<Denomination, ChipRoll> pokerChipCollection,
                                    Denomination ExpectedDenomination,
                                    int expectedQuantity) {
        PokerChip expectedPokerChip = getPokerChipWithUnknownColor(ExpectedDenomination);
        assertPokerChipFor(pokerChipCollection, expectedPokerChip, expectedQuantity);
    }

    private void assertPokerChipFor(ImmutableSortedMap<Denomination, ChipRoll> pokerChipCollection,
                                    PokerChip expectedPokerChip,
                                    int expectedQuantity) {

        ChipRoll chipRoll = pokerChipCollection.get(expectedPokerChip.getDenomination());

        assertEquals(expectedQuantity, chipRoll.getQuantity());

        PokerChip actualPokerChip = chipRoll.getPokerChip();
        assertEquals(expectedPokerChip, chipRoll.getPokerChip());
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
