package com.solium.pcd.mapper;

import com.solium.pcd.domain.Algorithm;
import com.solium.pcd.domain.PokerTable;
import com.solium.pcd.exception.MapperException;
import com.solium.pcd.exception.PokerChipException;

import java.util.List;

public class PokerChipMapper extends PokerChipMapperBase implements IMapper {

    private static final int REQUIRED_NUMBER_OF_LINES = 3;
    private static final Algorithm BASIC_ALGORITHM = Algorithm.BASIC;

    @Override
    public PokerTable mapFrom(List<String> pokerDetails) throws MapperException, PokerChipException {

        validateInput(pokerDetails);
        return getPokerTable(pokerDetails, BASIC_ALGORITHM);
    }

    @Override
    public void validateInput(List<String> pokerDetails) throws MapperException {

        checkNumberOfLines(pokerDetails, REQUIRED_NUMBER_OF_LINES);

        validateRegularInput(pokerDetails, BASIC_ALGORITHM);
    }

}
