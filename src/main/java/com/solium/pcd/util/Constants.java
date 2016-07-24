package com.solium.pcd.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public interface Constants {

    int BONUS_TWO_MIN_QUANTITY = 1;
    int NUMBER_OF_DECIMAL_PLACES = 2;

    Pattern BONUS_ONE_REGEX = Pattern.compile("^B1$");
    Pattern BONUS_TWO_REGEX = Pattern.compile("^B2$");
    Pattern BONUS_TWO_CHIP_BREAKDOWN_REGEX = Pattern.compile("^(\\d+)/(\\w+)$");
    Pattern CURRENCY_REGEX = Pattern.compile("^\\$\\d+\\.\\d{2}$");
    Pattern INTEGER_REGEX = Pattern.compile("^\\d+$");
    Pattern REGULAR_CHIP_BREAKDOWN_REGEX = Pattern.compile("^(\\d+)/\\$(\\d+\\.\\d{2})$");

    BigDecimal[] availableDenominations = {new BigDecimal("0.01"), new BigDecimal("0.05"), new BigDecimal("0.10"),
            new BigDecimal("0.25"), new BigDecimal("0.50"), new BigDecimal("1.00"), new BigDecimal("2.00"), new BigDecimal("5.00"),
            new BigDecimal("10.00"), new BigDecimal("20.00"), new BigDecimal("50.00"), new BigDecimal("100.00"), new BigDecimal("1000.00")};

    enum Algorithm {
        BASIC(""),
        BONUS_ONE("B1"),
        BONUS_TWO("B2");

        private final String _algorithm;

        Algorithm(String algorithm) {
            _algorithm = algorithm;
        }

        String getAlgorithmType() {
            return _algorithm;
        }
    }
}
