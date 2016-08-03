package com.solium.pcd.domain;

import com.google.common.collect.ImmutableList;
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
public class ChipRollTest {

    @Rule
    public ExpectedException _expectedException = ExpectedException.none();

    @Test
    public void build_withValidChipRoll_returnsChipRollOfOnePokerChip() {

        PokerChip pokerChip = getPokerChip();

        ChipRoll chipRoll = ChipRoll.newBuilder()
                .setQuantity(1)
                .setPokerChip(pokerChip)
                .build();

        assertEquals(1, chipRoll.getQuantity());
        assertEquals(pokerChip, chipRoll.getPokerChip());
    }

    private List<Object[]> getInvalidDataForChipRoll() {

        final ImmutableList.Builder<Object[]> cases = ImmutableList.builder();
        cases.add(new Object[]{"PokerChip of null", 1, null, NullPointerException.class, "PokerChip must not be null."});
        cases.add(new Object[]{"Quantity is minus 1", -1, getPokerChip(), IllegalArgumentException.class, "Quantity must be greater than or equal zero"});
        return cases.build();
    }

    @SuppressWarnings("TestMethodWithIncorrectSignature")
    @Test
    @TestCaseName(value = "Expecting exception with {0}")
    @Parameters(method = "getInvalidDataForChipRoll")
    public <E extends Exception> void build_withParameters_throwsException(@SuppressWarnings("UnusedParameters") String testName,
                                                                           int quantity,
                                                                           PokerChip pokerChip,
                                                                           Class<E> exception,
                                                                           String expectedExceptionMessage) {

        _expectedException.expect(exception);
        _expectedException.expectMessage(expectedExceptionMessage);
        ChipRoll chipRoll = ChipRoll.newBuilder()
                .setQuantity(quantity)
                .setPokerChip(pokerChip)
                .build();
    }

    private PokerChip getPokerChip() {
        return PokerChip.newBuilder()
                .setColor(Color.UNKNOWN)
                .setDenomination(Denomination.ONE_CENT)
                .build();
    }
}
