package com.solium.pcd.domain;

import com.google.common.collect.ImmutableList;
import com.solium.pcd.math.Amount;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class PokerTableTest {

    @Rule
    public ExpectedException _expectedException = ExpectedException.none();

    @Test
    public void build_withValidPokerTable_returnsPokerChipCollectionWithBuyInOfOne() {

        ImmutableList<ChipRoll> pokerChipDistribution = getPokerChipDistribution();

        PokerTable pokerTable = PokerTable.newBuilder()
                .setAlgorithm(Algorithm.BASIC)
                .setBuyIn(Amount.ONE)
                .setPokerChipCollection(pokerChipDistribution)
                .build();

        assertEquals(Algorithm.BASIC, pokerTable.getAlgorithm());
        assertEquals(Amount.ONE, pokerTable.getBuyIn());
        assertEquals(pokerChipDistribution, pokerTable.getPokerChipCollection());
    }

    private List<Object[]> getInvalidDataForPokerTable() {

        final ImmutableList.Builder<Object[]> cases = ImmutableList.builder();
        cases.add(new Object[]{"Algorithm of null", null, null, ImmutableList.<ChipRoll>of(), NullPointerException.class, "Algorithm must not be null."});
        cases.add(new Object[]{"Buy in of null", Algorithm.BASIC, null, ImmutableList.<ChipRoll>of(), NullPointerException.class, "Buy in must not be null."});
        cases.add(new Object[]{"Poker chip collection of null", Algorithm.BONUS_ONE, 1.00, null, NullPointerException.class, "Poker chip collection must not be null."});
        cases.add(new Object[]{"Buy in is minus 0.01", Algorithm.BONUS_TWO, -0.01, ImmutableList.<ChipRoll>of(), IllegalArgumentException.class, "Buy in must be greater or equal than zero"});
        return cases.build();
    }

    @SuppressWarnings("TestMethodWithIncorrectSignature")
    @Test
    @TestCaseName(value = "Expecting exception with {0}")
    @Parameters(method = "getInvalidDataForPokerTable")
    public <E extends Exception> void build_withParameters_throwsException(@SuppressWarnings("UnusedParameters") String testName,
                                                                           Algorithm algorithm,
                                                                           Double buyIn,
                                                                           ImmutableList<ChipRoll> pokerChipCollection,
                                                                           Class<E> exception,
                                                                           String expectedExceptionMessage) {

        _expectedException.expect(exception);
        _expectedException.expectMessage(expectedExceptionMessage);
        PokerTable.newBuilder()
                .setAlgorithm(algorithm)
                .setBuyIn(buyIn == null ? null : Amount.of(buyIn))
                .setPokerChipCollection(pokerChipCollection)
                .build();
    }

    private ImmutableList<ChipRoll> getPokerChipDistribution() {
        return ImmutableList.of(getPokerChip());
    }

    private ChipRoll getPokerChip() {
        return ChipRoll.newBuilder()
                .setQuantity(1)
                .setPokerChip(PokerChip.newBuilder()
                                      .setDenomination(Denomination.ONE_CENT)
                                      .setColor(Color.UNKNOWN)
                                      .build())
                .build();
    }
}
