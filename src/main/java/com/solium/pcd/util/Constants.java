package com.solium.pcd.util;

import com.google.common.collect.ImmutableList;
import com.solium.pcd.math.Amount;

import java.util.regex.Pattern;

public interface Constants {

    int NUMBER_OF_DECIMAL_PLACES = 2;
    int BONUS_TWO_MIN_QUANTITY = 1;

    Pattern BONUS_ONE_REGEX = Pattern.compile("^B1$");
    Pattern BONUS_TWO_REGEX = Pattern.compile("^B2$");
    Pattern BONUS_TWO_CHIP_BREAKDOWN_REGEX = Pattern.compile("^(\\d+)/(\\w+)$");
    Pattern CURRENCY_REGEX = Pattern.compile("^\\$\\d+\\.\\d{2}$");
    Pattern INTEGER_REGEX = Pattern.compile("^\\d+$");
    Pattern REGULAR_CHIP_BREAKDOWN_REGEX = Pattern.compile("^(\\d+)/\\$(\\d+\\.\\d{2})$");

    ImmutableList<Amount> DENOMINATIONS_AVAILABLE = new ImmutableList.Builder<Amount>()
            .add(Amount.of(0.01))
            .add(Amount.of(0.05))
            .add(Amount.of(0.10))
            .add(Amount.of(0.25))
            .add(Amount.of(0.50))
            .add(Amount.of(1.00))
            .add(Amount.of(2.00))
            .add(Amount.of(5.00))
            .add(Amount.of(10.00))
            .add(Amount.of(20.00))
            .add(Amount.of(50.00))
            .add(Amount.of(100.00))
            .add(Amount.of(1000.00))
            .build();

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
