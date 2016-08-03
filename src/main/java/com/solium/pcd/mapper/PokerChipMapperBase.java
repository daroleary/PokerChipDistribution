package com.solium.pcd.mapper;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedMap;
import com.solium.pcd.contract.Contract;
import com.solium.pcd.domain.Algorithm;
import com.solium.pcd.domain.ChipRoll;
import com.solium.pcd.domain.Color;
import com.solium.pcd.domain.Denomination;
import com.solium.pcd.domain.DenominationComparator;
import com.solium.pcd.domain.PokerChip;
import com.solium.pcd.domain.PokerTable;
import com.solium.pcd.exception.MapperException;
import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.math.Amount;
import com.solium.pcd.util.Constants;
import com.solium.pcd.util.Util;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.solium.pcd.util.Constants.COLOR_TO_DENOMINATION;

abstract class PokerChipMapperBase {

    final PokerTable getPokerTable(List<String> pokerDetails,
                                   Algorithm algorithm) throws MapperException, PokerChipException {

        Iterator<String> listItr = pokerDetails.iterator();
        String chipBreakdown = listItr.next();
        int numberOfPlayers = getIntegerFrom(listItr.next());
        Amount buyIn = Amount.of(listItr.next());

        ImmutableSortedMap<Denomination, ChipRoll> pokerCollection
                = getChipBreakdowns(chipBreakdown,
                                    numberOfPlayers,
                                    algorithm);

        return PokerTable.newBuilder()
                .setAlgorithm(algorithm)
                .setBuyIn(buyIn)
                .setPokerChipCollection(pokerCollection)
                .build();
    }

    private ImmutableSortedMap<Denomination, ChipRoll> getChipBreakdowns(final String chipsBreakDown,
                                                                         final int numberOfPlayers,
                                                                         Algorithm algorithm) throws MapperException, PokerChipException {

        ImmutableList<String> chipBreakdowns = ImmutableList.copyOf(chipsBreakDown.split(","));
        if (chipBreakdowns.isEmpty()) {
            throw new MapperException(String.format(
                    "Input chip breakdown is invalid, should be in format of 00/$0.00,.. but is %s",
                    chipsBreakDown));
        }

        ImmutableSortedMap.Builder<Denomination, ChipRoll> chipRolls =
                ImmutableSortedMap.orderedBy(new DenominationComparator());

        chipRolls.putAll(
                getChipRolls(numberOfPlayers, chipBreakdowns, algorithm)
        );

        return chipRolls.build();
    }

    private Map<Denomination, ChipRoll> getChipRolls(int numberOfPlayers,
                                                     ImmutableList<String> chipBreakdowns,
                                                     Algorithm algorithm) {
        return chipBreakdowns.stream()
                .map(toChipRoll(numberOfPlayers, algorithm))
                .collect(denominationToChipRollLookup());
    }

    private ChipRoll getChipRollFrom(int numberOfPlayers,
                                     String chipBreakdown,
                                     Algorithm algorithm) {

        Matcher matcher = Constants.ALGORITHM_TO_CHIP_BREAKDOWN_REGEX.get(algorithm)
                .matcher(chipBreakdown);

        if (!matcher.find()) {
            Contract.fail(getFormatErrorByAlgorithm(chipBreakdown, algorithm));
        }

        int quantity = getQuantity(matcher.group(1), numberOfPlayers);
        Color color = getColorFrom(matcher, algorithm);
        Denomination denomination = getDenominationFrom(color, matcher, algorithm);

        return getChipRoll(quantity, color, denomination);
    }

    private String getFormatErrorByAlgorithm(String chipBreakdown, Algorithm algorithm) {
        String formatError;
        if (Algorithm.BONUS_TWO == algorithm) {
           formatError = MessageFormat.format("Chip breakdown invalid, should be of the format 00/Color but is [{0}]", chipBreakdown);
        } else {
            formatError = MessageFormat.format("Chip breakdown invalid, should be of the format 00/$0.00 but is [{0}]", chipBreakdown);
        }
        return formatError;
    }

    private Color getColorFrom(Matcher matcher, Algorithm algorithm) {
        Color color;
        if (algorithm == Algorithm.BONUS_TWO) {
            color = Color.of(matcher.group(2));
        } else {
            color = Color.UNKNOWN;
        }
        return color;
    }

    private Denomination getDenominationFrom(Color color,
                                             Matcher matcher,
                                             Algorithm algorithm) {
        Denomination denomination;
        if (algorithm == Algorithm.BONUS_TWO) {
            denomination = COLOR_TO_DENOMINATION.get(color);
        } else {
            denomination = Denomination.of(matcher.group(2));
        }
        return denomination;
    }

    private int getIntegerFrom(String group) {
        return Integer.parseInt(group);
    }

    private int getQuantity(String group, int numberOfPlayers) {
        Amount quantity = Amount.of(getIntegerFrom(group)).divide(numberOfPlayers);
        return quantity.intValue();
    }

    private ChipRoll getChipRoll(int quantity,
                                 Color color,
                                 Denomination denomination) {
        PokerChip pokerChip = PokerChip.newBuilder()
                .setDenomination(denomination)
                .setColor(color)
                .build();

        return ChipRoll.newBuilder()
                .setQuantity(quantity)
                .setPokerChip(pokerChip)
                .build();
    }

    private Collector<ChipRoll, ?, Map<Denomination, ChipRoll>> denominationToChipRollLookup() {
        return Collectors.toMap(chipRoll -> chipRoll.getPokerChip().getDenomination(),
                                Function.identity());
    }

    private Function<String, ChipRoll> toChipRoll(int numberOfPlayers, Algorithm algorithm) {
        return chipBreakdown ->
                getChipRollFrom(numberOfPlayers, chipBreakdown, algorithm);
    }

    void checkNumberOfLines(List<String> inputs, int numberOfLines) throws MapperException {
        if (inputs.size() != numberOfLines) {
            throw new MapperException(String.format("Input parameter count is incorrect, should be %d but is %d",
                                                    numberOfLines,
                                                    inputs.size()));
        }
    }

    private void validateChipBreakdownRegex(final String chipsBreakdown, final Pattern regex) throws MapperException {

        String[] chipBreakdownArray = chipsBreakdown.split(",");
        if (chipBreakdownArray.length == 0) {
            throw new MapperException(String.format(
                    "Chip breakdown invalid, should be of the format 00/$0.00 but is [%s]",
                    chipsBreakdown));
        }

        for (String chipBreakDown : chipBreakdownArray) {
            Matcher m = regex.matcher(chipBreakDown);

            if (!m.find()) {
                throw new MapperException(String.format(
                        "Chip breakdown invalid, should be of the format 00/$0.00 but is [%s]",
                        chipBreakDown));
            }
        }
    }

    private void validateNumberOfPlayersRegex(final String numberOfPlayers,
                                              final Pattern regex) throws MapperException {
        if (!Util.regexMatches(numberOfPlayers, regex)) {
            throw new MapperException(String.format("Input number of players is invalid, should be a number but is %s",
                                                    numberOfPlayers));
        }
    }

    private void validateCurrencyRegex(final String buyIn, final Pattern regex) throws MapperException {
        if (!Util.regexMatches(buyIn, regex)) {
            throw new MapperException(String.format("Input buy in is invalid, should be of the format $0.00 but is %s",
                                                    buyIn));
        }
    }

    void validateRegularInput(List<String> inputs, Algorithm algorithm) throws MapperException {
        Iterator<String> listItr = inputs.iterator();
        String chipBreakdown = listItr.next();
        String numberOfPlayers = listItr.next();
        String buyIn = listItr.next();

        Pattern chipBreakdownRegex = Constants.ALGORITHM_TO_CHIP_BREAKDOWN_REGEX.get(algorithm);
        validateChipBreakdownRegex(chipBreakdown, chipBreakdownRegex);

        validateNumberOfPlayersRegex(numberOfPlayers, Constants.INTEGER_REGEX);

        validateCurrencyRegex(buyIn, Constants.CURRENCY_REGEX);
    }
}
