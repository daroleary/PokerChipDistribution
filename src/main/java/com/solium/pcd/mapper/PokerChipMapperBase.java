package com.solium.pcd.mapper;

import com.solium.pcd.domain.Color;
import com.solium.pcd.domain.PokerChip;
import com.solium.pcd.domain.PokerChips;
import com.solium.pcd.exception.MapperException;
import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.math.Amount;
import com.solium.pcd.util.Constants;
import com.solium.pcd.util.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class PokerChipMapperBase {

    void checkNumberOfLines(List<String> inputs, int numberOfLines) throws MapperException {
        if (inputs.size() != numberOfLines) {
            throw new MapperException(String.format("Input parameter count is incorrect, should be %d but is %d", numberOfLines, inputs.size()));
        }
    }

    private void validateChipBreakdownRegex(final String chipsBreakdown, final Pattern regex) throws MapperException {

        String[] chipBreakdownArray = chipsBreakdown.split(",");
        if (chipBreakdownArray.length == 0) {
            throw new MapperException(String.format("Chip breakdown invalid, should be of the format 00/$0.00 but is [%s]", chipsBreakdown));
        }

        for (String chipBreakDown : chipBreakdownArray) {
            Matcher m = regex.matcher(chipBreakDown);

            if (!m.find()) {
                throw new MapperException(String.format("Chip breakdown invalid, should be of the format 00/$0.00 but is [%s]", chipBreakDown));
            }
        }
    }

    private void validateNumberOfPlayersRegex(final String numberOfPlayers, final Pattern regex) throws MapperException {
        if (!Util.regexMatches(numberOfPlayers, regex)) {
            throw new MapperException(String.format("Input number of players is invalid, should be a number but is %s", numberOfPlayers));
        }
    }

    private void validateCurrencyRegex(final String buyIn, final Pattern regex) throws MapperException {
        if (!Util.regexMatches(buyIn, regex)) {
            throw new MapperException(String.format("Input buy in is invalid, should be of the format $0.00 but is %s", buyIn));
        }
    }

    void validateRegularInput(List<String> inputs) throws MapperException {
        Iterator<String> listItr = inputs.iterator();
        String chipBreakdown = listItr.next();
        String numberOfPlayers = listItr.next();
        String buyIn = listItr.next();

        validateChipBreakdownRegex(chipBreakdown, Constants.REGULAR_CHIP_BREAKDOWN_REGEX);

        validateNumberOfPlayersRegex(numberOfPlayers, Constants.INTEGER_REGEX);

        validateCurrencyRegex(buyIn, Constants.CURRENCY_REGEX);
    }

    void validateBonusTwoInput(List<String> inputs) throws MapperException {

        Iterator<String> listItr = inputs.iterator();
        String chipBreakdown = listItr.next();
        String numberOfPlayers = listItr.next();
        String buyIn = listItr.next();

        validateChipBreakdownRegex(chipBreakdown, Constants.BONUS_TWO_CHIP_BREAKDOWN_REGEX);

        validateNumberOfPlayersRegex(numberOfPlayers, Constants.INTEGER_REGEX);

        validateCurrencyRegex(buyIn, Constants.CURRENCY_REGEX);
    }

    private final List<PokerChip> getChipBreakdowns(final String chipsBreakDown, final int numberOfPlayers) throws MapperException, PokerChipException {

        String[] chipBreakdownArray = chipsBreakDown.split(",");
        if (chipBreakdownArray.length == 0) {
            throw new MapperException(String.format("Input chip breakdown is invalid, should be in format of 00/$0.00,.. but is %s", chipsBreakDown));
        }

        List<PokerChip> pokerChips = new ArrayList<>();

        for (String chipBreakDown : chipBreakdownArray) {
            Matcher m = Constants.REGULAR_CHIP_BREAKDOWN_REGEX.matcher(chipBreakDown);

            if (m.find()) {
                int quantity = getQuantity(m.group(1), numberOfPlayers);
                Amount denomination = Amount.of(Double.parseDouble(m.group(2)));

                pokerChips.add(new PokerChip(denomination, quantity));
            } else {
                System.out.println(String.format("Chip breakdown invalid, should be of the format 00/$0.00 but is [%s]", chipBreakDown));
            }
        }

        return pokerChips;
    }

    private int getIntegerFrom(String group) {
        return Integer.parseInt(group);
    }

    private final List<PokerChip> getBonusTwoChipBreakdowns(String chipsBreakDown, final int numberOfPlayers) throws MapperException, PokerChipException {

        String[] chipBreakdownArray = chipsBreakDown.split(",");
        if (chipBreakdownArray.length == 0) {
            throw new MapperException(String.format("Input chip breakdown is invalid, should be in format of 00/Color,.. but is %s", chipsBreakDown));
        }

        List<PokerChip> pokerChips = new ArrayList<>();

        int denominationIndex = chipBreakdownArray.length;

        for (String chipBreakDown : chipBreakdownArray) {
            Matcher m = Constants.BONUS_TWO_CHIP_BREAKDOWN_REGEX.matcher(chipBreakDown);

            if (m.find()) {

                denominationIndex--;

                int quantity = getQuantity(m.group(1), numberOfPlayers);
                Amount denomination = Constants.DENOMINATIONS_AVAILABLE.get(denominationIndex);
                Color color = Color.of(m.group(2));

                pokerChips.add(new PokerChip(color, denomination, quantity));
            } else {
                System.out.println(String.format("Chip breakdown invalid, should be of the format 00/Color but is [%s]", chipBreakDown));
            }
        }

        return pokerChips;
    }

    private int getQuantity(String group, int numberOfPlayers) {
        Amount quantity = Amount.of(getIntegerFrom(group)).divide(numberOfPlayers);
        return quantity.intValue();
    }

    final PokerChips getPokerList(List<String> pokerDetails) throws MapperException, PokerChipException {

        Iterator<String> listItr = pokerDetails.iterator();
        String chipBreakdown = listItr.next();
        int numberOfPlayers = getIntegerFrom(listItr.next());
        Amount buyIn = Amount.of(listItr.next());

        List<PokerChip> list = getChipBreakdowns(chipBreakdown, numberOfPlayers);

        PokerChips pokerList = new PokerChips(list);
        pokerList.setBuyInAmount(buyIn);
        pokerList.setPlayerCount(numberOfPlayers);

        return pokerList;
    }

    final PokerChips getBonusTwoPokerList(List<String> pokerDetails) throws MapperException, PokerChipException {

        Iterator<String> listItr = pokerDetails.iterator();
        String chipBreakdown = listItr.next();
        int numberOfPlayers = getIntegerFrom(listItr.next());
        Amount buyIn = Amount.of(listItr.next());

        List<PokerChip> list = getBonusTwoChipBreakdowns(chipBreakdown, numberOfPlayers);

        PokerChips pokerList = new PokerChips(list);
        pokerList.setBuyInAmount(buyIn);
        pokerList.setPlayerCount(numberOfPlayers);

        return pokerList;
    }
}
