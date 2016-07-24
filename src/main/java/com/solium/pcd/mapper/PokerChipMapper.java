package com.solium.pcd.mapper;

import com.solium.pcd.domain.PokerChips;
import com.solium.pcd.exception.MapperException;
import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.util.Constants.Algorithm;

import java.util.List;

public class PokerChipMapper extends PokerChipMapperBase implements IMapper {

    private static final int REQUIRED_NUMBER_OF_LINES = 3;

    @Override
    public PokerChips mapFrom(List<String> pokerDetails) throws MapperException, PokerChipException {

        validateInput(pokerDetails);

        PokerChips pokerChips = getPokerList(pokerDetails);
        pokerChips.setAlgorithm(Algorithm.BASIC);

        return pokerChips;
    }

    @Override
    public void validateInput(List<String> inputs) throws MapperException {

        checkNumberOfLines(inputs, REQUIRED_NUMBER_OF_LINES);

        validateRegularInput(inputs);
    }

}
