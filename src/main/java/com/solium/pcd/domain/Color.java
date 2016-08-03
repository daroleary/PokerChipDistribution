package com.solium.pcd.domain;

import com.google.common.base.Preconditions;

import java.text.MessageFormat;

public enum Color {
    RED("Red"),
    BLUE("Blue"),
    BLACK("Black"),
    GREEN("Green"),
    YELLOW("Yellow"),
    TAUPE("Taupe"),
    UNKNOWN("Unknown");

    private final String _colorName;

    Color(String colorName) {
        _colorName = colorName;
    }

    public static Color of(String colorName) {
        Preconditions.checkNotNull(colorName, "Inputted color must not be null.");

        for (Color color : Color.values()) {
            if (color.name().equalsIgnoreCase(colorName)) {
                return color;
            }
        }

        throw new IllegalArgumentException(MessageFormat.format("Unable to get a color from: {0}", colorName));
    }

    public String getColorName() {
        return _colorName;
    }

    @Override
    public String toString() {
        return "Color{" + name() +
                "}";
    }
}
