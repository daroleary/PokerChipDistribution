package com.solium.pcd.util;

import com.google.common.base.Preconditions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class Util {

    private static final int MONETARY_CHARACTER_OFFSET_INDEX = 1;
    private static final String DOLLAR_FORMAT = "#0.00";

    private Util() {
    }

    /**
     * rounds value to desired decimal places.
     *
     * @param val                   big decimal value
     * @return big decimal with specified decimal places
     */
    public static BigDecimal roundToDecimalPlaces(final BigDecimal val) {

        int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
        return val.setScale(Constants.NUMBER_OF_DECIMAL_PLACES, ROUNDING_MODE);
    }

    /**
     * @param currency
     * @return big decimal value of given currency
     */
    public static BigDecimal convertAmountToBigDecimal(final String currency) {
        return roundToDecimalPlaces(new BigDecimal(currency));
    }

    /**
     * @param currency
     * @return big decimal value of given currency
     */
    public static BigDecimal convertMonetaryAmountToBigDecimal(final String currency) {
        return roundToDecimalPlaces(new BigDecimal(currency.substring(MONETARY_CHARACTER_OFFSET_INDEX)));
    }

    public static BigDecimal divideFor(final BigDecimal val1, final BigDecimal val2) {
        return val1.divide(val2, Constants.NUMBER_OF_DECIMAL_PLACES, RoundingMode.HALF_UP);
    }

    public static BigDecimal subtractFor(final BigDecimal val1, final BigDecimal val2) {
        return roundToDecimalPlaces(val1.subtract(val2));
    }

    private static int compareToZero(final BigDecimal val) {
        Preconditions.checkNotNull(val);
        return val.compareTo(new BigDecimal(0.00));
    }

    public static boolean isGreaterThanZero(final BigDecimal val) {
        return compareToZero(val) > 0;
    }

    public static boolean isLessThanZero(final BigDecimal val) {
        return compareToZero(val) < 0;
    }

    public static boolean isZero(final BigDecimal val) {
        return compareToZero(val) == 0;
    }

    public static boolean isNotZero(final BigDecimal val) {
        return compareToZero(val) != 0;
    }

    /**
     * @param val
     * @return dollar amount as String
     */
    public static String getDollarAmount(final BigDecimal val) {

        DecimalFormat currency = new DecimalFormat(DOLLAR_FORMAT);

        return String.format("$%s", currency.format(val));
    }

    /**
     * @param param
     * @param regex
     * @return boolean value to determine if regex matches
     */
    public static boolean regexMatches(String param, Pattern regex) {
        return regex.matcher(param).matches();
    }
}
