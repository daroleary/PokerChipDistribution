package com.solium.pcd.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class ColorTest {

    @Rule
    public ExpectedException _expectedException = ExpectedException.none();

    @Test
    public void of_colorNameIsNull_throwsNullPointerException() throws Exception {

        _expectedException.expect(NullPointerException.class);
        Color.of(null);
    }

    @Test
    public void of_colorNameIsNotAColor_throwsIllegalArgumentException() throws Exception {

        _expectedException.expect(IllegalArgumentException.class);
        _expectedException.expectMessage("Unable to get a color from: a");
        Color.of("a");
    }

    @Test
    public void of_greenColorWithAllCases_returnsGreenColor() throws Exception {

        Color actualColor = Color.of("gReeN");
        assertEquals(Color.GREEN, actualColor);
    }

    @Test
    public void of_allColors_returnsCorrectColor() throws Exception {

        assertEquals(Color.RED, Color.of("red"));
        assertEquals(Color.BLUE, Color.of("blue"));
        assertEquals(Color.BLACK, Color.of("black"));
        assertEquals(Color.GREEN, Color.of("green"));
        assertEquals(Color.YELLOW, Color.of("yellow"));
        assertEquals(Color.TAUPE, Color.of("taupe"));
        assertEquals(Color.UNKNOWN, Color.of("unknown"));
    }
}