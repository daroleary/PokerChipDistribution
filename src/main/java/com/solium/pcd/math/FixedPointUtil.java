package com.solium.pcd.math;

import java.math.BigDecimal;

class FixedPointUtil {

    private static final long UNIT_MULTIPLIER = 1000000;  //10 ^ SCALE_FACTOR

    private static final double MAX_VALUE_DOUBLE = ((double) Long.MAX_VALUE) / ((double) UNIT_MULTIPLIER);
    private static final long MAX_VALUE_LONG = Long.MAX_VALUE / UNIT_MULTIPLIER;

    private static final double MIN_VALUE_DOUBLE = -MAX_VALUE_DOUBLE;
    private static final long MIN_VALUE_LONG = -MAX_VALUE_LONG;

    private FixedPointUtil() {
    }

    static long getValueInMillionthsFromDouble(double value) {
        if (value >= MAX_VALUE_DOUBLE) {
            throw new IllegalArgumentException("overflow error, " + value + " >= MAX_VALUE_DOUBLE");
        } else if (value <= MIN_VALUE_DOUBLE) {
            throw new IllegalArgumentException("overflow error, " + value + " <= MIN_VALUE_DOUBLE");
        }

        return Math.round(value * UNIT_MULTIPLIER);
    }

    static long getValueInMillionthsFromLong(long value) {
        if (value >= MAX_VALUE_LONG) {
            throw new IllegalArgumentException("overflow error, " + value + " >= MAX_VALUE_LONG");
        } else if (value <= MIN_VALUE_LONG) {
            throw new IllegalArgumentException("overflow error, " + value + " >= MIN_VALUE_LONG");
        }

        return value * UNIT_MULTIPLIER;
    }

    static long getValueInMillionthsFromString(final String denomination) {

        String denominationWithoutCurrency = denomination.replace("$", "");
        double value = new BigDecimal(denominationWithoutCurrency).doubleValue();
        return getValueInMillionthsFromDouble(value);
    }

    static double getValue(long valueInMillionths) {
        return ((double) valueInMillionths) / UNIT_MULTIPLIER;
    }

    ///////////////////////////////////////

    public static long add(long value1InMillionths, long value2InMillionths) {
        long resultInMillionths = value1InMillionths + value2InMillionths;

        if ((value1InMillionths > 0) && (value2InMillionths > 0)) {
            if (resultInMillionths < 0) {
                throw new IllegalArgumentException("overflow error, " + value1InMillionths + "+" + value2InMillionths + "=" + resultInMillionths);
            }
        }

        return resultInMillionths;
    }

    static long subtract(long value1InMillionths, long value2InMillionths) {
        long resultInMillionths = value1InMillionths - value2InMillionths;

        if ((value1InMillionths > 0) && (value2InMillionths < 0)) {
            if (resultInMillionths < 0) {
                throw new IllegalArgumentException("overflow error, " + value1InMillionths + "-" + value2InMillionths + "=" + resultInMillionths);
            }
        }

        return resultInMillionths;
    }

    static long multiplyByLong(long valueInMillionths, long multiplier) {
        long resultInMillionths = valueInMillionths * multiplier;

        return resultInMillionths;
    }

    static long multiplyByDouble(long valueInMillionths, double multiplier) {
        long resultInMillionths = Math.round(valueInMillionths * multiplier);

        if ((resultInMillionths == Long.MIN_VALUE) || (resultInMillionths == Long.MAX_VALUE)) {
            throw new IllegalArgumentException("overflow error, " + valueInMillionths + "*" + multiplier + "=" + resultInMillionths);
        }

        return resultInMillionths;
    }

    static long divideByLong(long valueInMillionths, long multiplier) {
        long resultInMillionths = valueInMillionths / multiplier;

        return resultInMillionths;
    }

    static long divideByDouble(long valueInMillionths, double divisor) {
        if (divisor == 0.0d) {
            throw new IllegalArgumentException("division by zero");
        }

        long resultInMillionths = Math.round(valueInMillionths / divisor);

        if ((resultInMillionths == Long.MIN_VALUE) || (resultInMillionths == Long.MAX_VALUE)) {
            throw new IllegalArgumentException("overflow error, " + valueInMillionths + "/" + divisor + "=" + resultInMillionths);
        }

        return resultInMillionths;
    }
}
