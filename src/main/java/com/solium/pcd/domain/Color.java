package com.solium.pcd.domain;

import com.google.common.base.Preconditions;

import java.text.MessageFormat;

public enum Color {
    RED,
    BLUE,
    BLACK,
    GREEN,
    YELLOW,
    TAUPE,
    UNKNOWN;

    public static Color of(String colorName) {
        Preconditions.checkNotNull(colorName);

        for (Color color : Color.values()) {
            if (color.toString().equalsIgnoreCase(colorName)) {
                return color;
            }
        }

        throw new IllegalArgumentException(MessageFormat.format("Unable to get a color from: {0}", colorName));
    }
}
