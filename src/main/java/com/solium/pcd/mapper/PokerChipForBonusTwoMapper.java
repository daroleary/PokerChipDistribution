package com.solium.pcd.mapper;

import com.solium.pcd.domain.PokerChips;
import com.solium.pcd.exception.MapperException;
import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.util.Constants.Algorithm;

import java.util.List;

public class PokerChipForBonusTwoMapper extends PokerChipMapperBase implements IMapper {

    private static final int REQUIRED_NUMBER_OF_LINES = 4;

    @Override
    public PokerChips mapFrom(List<String> pokerDetails) throws MapperException, PokerChipException {

        validateInput(pokerDetails);

        PokerChips pokerChips = getBonusTwoPokerList(pokerDetails.subList(1, (pokerDetails.size())));
        pokerChips.setAlgorithm(Algorithm.BONUS_TWO);

        return pokerChips;
    }

    @Override
    public void validateInput(List<String> pokerDetails) throws MapperException {

        checkNumberOfLines(pokerDetails, REQUIRED_NUMBER_OF_LINES);

        validateBonusTwoInput(pokerDetails.subList(1, (pokerDetails.size())));
    }
}
