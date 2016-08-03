package com.solium.pcd.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.solium.pcd.domain.Algorithm;
import com.solium.pcd.domain.Color;
import com.solium.pcd.domain.Denomination;
import com.solium.pcd.math.Amount;

import java.util.regex.Pattern;

public interface Constants {

    int BONUS_ONE_MIN_QUANTITY = 1;

    Pattern BONUS_ONE_REGEX = Pattern.compile("^B1$");
    Pattern BONUS_TWO_REGEX = Pattern.compile("^B2$");
    Pattern BONUS_TWO_CHIP_BREAKDOWN_REGEX = Pattern.compile("^(\\d+)/(\\w+)$");
    Pattern CURRENCY_REGEX = Pattern.compile("^\\$\\d+\\.\\d{2}$");
    Pattern INTEGER_REGEX = Pattern.compile("^\\d+$");
    Pattern REGULAR_CHIP_BREAKDOWN_REGEX = Pattern.compile("^(\\d+)/\\$(\\d+\\.\\d{2})$");

    ImmutableMap<Algorithm, Pattern> ALGORITHM_TO_CHIP_BREAKDOWN_REGEX
            = new ImmutableMap.Builder<Algorithm, Pattern>()
            .put(Algorithm.BASIC, REGULAR_CHIP_BREAKDOWN_REGEX)
            .put(Algorithm.BONUS_ONE, REGULAR_CHIP_BREAKDOWN_REGEX)
            .put(Algorithm.BONUS_TWO, BONUS_TWO_CHIP_BREAKDOWN_REGEX)
            .build();

    ImmutableMap<Color, Denomination> COLOR_TO_DENOMINATION
            = new ImmutableMap.Builder<Color, Denomination>()
            .put(Color.TAUPE, Denomination.ONE_CENT)
            .put(Color.YELLOW, Denomination.FIVE_CENTS)
            .put(Color.GREEN, Denomination.TEN_CENTS)
            .put(Color.BLACK, Denomination.TWENTY_FIVE_CENTS)
            .put(Color.BLUE, Denomination.FIFTY_CENT)
            .put(Color.RED, Denomination.ONE_DOLLAR)
            .build();

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
}
