package com.solium.pcd.domain;

import com.google.common.collect.ImmutableList;
import com.solium.pcd.exception.PokerChipException;
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
public class PokerChipTest {

    @Rule
    public ExpectedException _expectedException = ExpectedException.none();

    private static final Denomination ONE_DOLLAR = Denomination.ONE_DOLLAR;

    @Test
    public void build_withValidPokerChip_returnsPokerChipOfOneDollarAndUnknownColor() throws PokerChipException {

        PokerChip chip = PokerChip.newBuilder()
                .setColor(Color.UNKNOWN)
                .setDenomination(ONE_DOLLAR)
                .build();

        assertEquals(ONE_DOLLAR, chip.getDenomination());
        assertEquals(Color.UNKNOWN, chip.getColor());
    }

    private List<Object[]> getInvalidDataForPokerChip() {
        final ImmutableList.Builder<Object[]> cases = ImmutableList.builder();
        cases.add(new Object[]{"Color of null", null, ONE_DOLLAR, "Colour must not be null."});
        cases.add(new Object[]{"Denomination of null", Color.UNKNOWN, null, "Denomiation must not be null."});
        return cases.build();
    }

    @SuppressWarnings("TestMethodWithIncorrectSignature")
    @Test
    @TestCaseName(value = "Expecting NullPointerException with {0}")
    @Parameters(method = "getInvalidDataForPokerChip")
    public void build_colorIsNull_throwsNullPointerException(@SuppressWarnings("UnusedParameters") String testName,
                                                             Color color,
                                                             Denomination denomination,
                                                             String expectedExceptionMessage) {

        _expectedException.expect(NullPointerException.class);
        _expectedException.expectMessage(expectedExceptionMessage);
        PokerChip chip = PokerChip.newBuilder()
                .setColor(color)
                .setDenomination(denomination)
                .build();

    }
}
