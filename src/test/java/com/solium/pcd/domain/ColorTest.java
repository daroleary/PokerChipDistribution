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
public class ColorTest {

    @Rule
    public ExpectedException _expectedException = ExpectedException.none();

    private List<Object[]> getValidDataForColor() {

        final ImmutableList.Builder<Object[]> cases = ImmutableList.builder();
        cases.add(new Object[]{"Green with different cases is Color GREEN", "gReeN", Color.GREEN});
        cases.add(new Object[]{"red", "red", Color.RED});
        cases.add(new Object[]{"blue", "blue", Color.BLUE});
        cases.add(new Object[]{"black", "black", Color.BLACK});
        cases.add(new Object[]{"green", "green", Color.GREEN});
        cases.add(new Object[]{"yellow", "yellow", Color.YELLOW});
        cases.add(new Object[]{"taupe", "taupe", Color.TAUPE});
        cases.add(new Object[]{"unknown", "unknown", Color.UNKNOWN});
        return cases.build();
    }

    @SuppressWarnings("TestMethodWithIncorrectSignature")
    @Test
    @TestCaseName(value = "Return actual color with {0}")
    @Parameters(method = "getValidDataForColor")
    public void of_greenColorWithAllCases_returnsGreenColor(@SuppressWarnings("UnusedParameters") String testName,
                                                            String colorName,
                                                            Color expectedColor) throws Exception {

        assertEquals(expectedColor, Color.of(colorName));
    }

    private List<Object[]> getInvalidDataForColor() {

        final ImmutableList.Builder<Object[]> cases = ImmutableList.builder();
        cases.add(new Object[]{"Color of null", null, NullPointerException.class, "Inputted color must not be null."});
        cases.add(new Object[]{"Color of 'a'", "a", IllegalArgumentException.class, "Unable to get a color from: a"});
        return cases.build();
    }

    @SuppressWarnings("TestMethodWithIncorrectSignature")
    @Test
    @TestCaseName(value = "Expecting Exception with {0}")
    @Parameters(method = "getInvalidDataForColor")
    public <E extends Exception> void of_withParameters_throwsException(@SuppressWarnings("UnusedParameters") String testName,
                                                                        String colorName,
                                                                        Class<E> exception,
                                                                        String expectedExceptionMessage) throws Exception {

        _expectedException.expect(exception);
        _expectedException.expectMessage(expectedExceptionMessage);
        Color.of(colorName);
    }
}