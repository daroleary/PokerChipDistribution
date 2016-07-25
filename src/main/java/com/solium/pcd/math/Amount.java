package com.solium.pcd.math;

import java.text.DecimalFormat;

public class Amount implements Comparable<Amount> {

    public static final Amount ZERO = of(0);
    public static final Amount ONE = of(1);

    public static final double MAX_QUANTITY = 10000000000.0;

    private static final String DOLLAR_FORMAT = "#0.00";

    private final long _numberOfUnitsInMillionths;

    private Amount(long numberOfUnitsInMillionths) {
        _numberOfUnitsInMillionths = numberOfUnitsInMillionths;
    }

    public static Amount of(double numberOfUnits) {
        return new Amount(FixedPointUtil.getValueInMillionthsFromDouble(numberOfUnits));
    }

    private static Amount of(long numberOfUnits) {
        return new Amount(FixedPointUtil.getValueInMillionthsFromLong(numberOfUnits));
    }

    public static Amount of(String numberOfUnits) {
        return new Amount(FixedPointUtil.getValueInMillionthsFromString(numberOfUnits));
    }

    private double getNumberOfUnits() {
        return FixedPointUtil.getValue(_numberOfUnitsInMillionths);
    }

    public int intValue() {
        return (int) getNumberOfUnits();
    }

    public long valueInMillionths() {
        return _numberOfUnitsInMillionths;
    }

    public String getDollarAmount() {

        DecimalFormat currency = new DecimalFormat(DOLLAR_FORMAT);

        return String.format("$%s", currency.format(getNumberOfUnits()));
    }

    public boolean greaterThan(double other) {
        return greaterThan(of(other));
    }

    public boolean greaterThan(Amount other) {
        return (_numberOfUnitsInMillionths > other._numberOfUnitsInMillionths);
    }

    public boolean greaterThanOrEqual(Amount other) {
        return (_numberOfUnitsInMillionths >= other._numberOfUnitsInMillionths);
    }

    public boolean lessThan(double other) {
        return lessThan(of(other));
    }

    public boolean lessThan(Amount other) {
        return (_numberOfUnitsInMillionths < other._numberOfUnitsInMillionths);
    }

    public boolean lessThanOrEqual(Amount other) {
        return (_numberOfUnitsInMillionths <= other._numberOfUnitsInMillionths);
    }

    ///////////////////////////////////////
    ///////////////////////////////////////
    //math functions below

    public Amount add(Amount other) {
        long thisValueInMillionths = _numberOfUnitsInMillionths;
        long otherValueInMillionths = other._numberOfUnitsInMillionths;

        if (thisValueInMillionths == 0) {
            return other;
        } else if (otherValueInMillionths == 0) {
            return this;
        } else {
            long resultValueInMillionths = FixedPointUtil.add(thisValueInMillionths, otherValueInMillionths);
            return new Amount(resultValueInMillionths);
        }
    }

    public Amount subtract(Amount other) {
        long thisValueInMillionths = _numberOfUnitsInMillionths;
        long otherValueInMillionths = other._numberOfUnitsInMillionths;

        if (otherValueInMillionths == 0) {
            return this;
        } else if (thisValueInMillionths == otherValueInMillionths) {
            return ZERO;
        } else {
            long resultValueInMillionths = FixedPointUtil.subtract(thisValueInMillionths, otherValueInMillionths);
            return new Amount(resultValueInMillionths);
        }
    }

    public Amount multiply(long multiplier) {
        if (multiplier == 1) {
            return this;
        } else {
            long resultValueInMillionths = FixedPointUtil.multiplyByLong(_numberOfUnitsInMillionths, multiplier);
            return new Amount(resultValueInMillionths);
        }
    }

    public Amount multiply(Amount multiplier) {
        return multiply(multiplier.getNumberOfUnits());
    }

    private Amount multiply(double multiplier) {
        if (multiplier == 1.0d) {
            return this;
        } else {
            long resultValueInMillionths = FixedPointUtil.multiplyByDouble(_numberOfUnitsInMillionths, multiplier);
            return new Amount(resultValueInMillionths);
        }
    }

    public Amount divide(long divisor) {
        if (divisor == 1) {
            return this;
        } else {
            long resultValueInMillionths = FixedPointUtil.divideByLong(_numberOfUnitsInMillionths, divisor);
            return new Amount(resultValueInMillionths);
        }
    }

    public Amount divide(double divisor) {
        if (divisor == 1.0d) {
            return this;
        } else {
            long resultValueInMillionths = FixedPointUtil.divideByDouble(_numberOfUnitsInMillionths, divisor);
            return new Amount(resultValueInMillionths);
        }
    }

    public double divide(Amount other) {
        return ((double) _numberOfUnitsInMillionths) / ((double) other._numberOfUnitsInMillionths);
    }


    public Amount negate() {
        if (_numberOfUnitsInMillionths == 0) {
            return this;
        } else {
            return new Amount(_numberOfUnitsInMillionths * -1);
        }
    }

    public Amount abs() {
        if (_numberOfUnitsInMillionths >= 0) {
            return this;
        } else {
            return new Amount(_numberOfUnitsInMillionths * -1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Amount)) {
            return false;
        }

        Amount that = (Amount) o;

        return _numberOfUnitsInMillionths == that._numberOfUnitsInMillionths;
    }

    @Override
    public int hashCode() {
        return (int) (_numberOfUnitsInMillionths ^ (_numberOfUnitsInMillionths >>> 32));
    }

    @Override
    public int compareTo(Amount other) {
        long thisVal = _numberOfUnitsInMillionths;
        long otherVal = other._numberOfUnitsInMillionths;

        if (thisVal < otherVal) {
            return -1;
        } else if (thisVal > otherVal) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Amount{" +
                "numberOfUnits=" + getNumberOfUnits() +
                '}';
    }
}
