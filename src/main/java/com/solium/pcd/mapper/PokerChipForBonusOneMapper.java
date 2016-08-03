package com.solium.pcd.mapper;

import com.solium.pcd.domain.Algorithm;
import com.solium.pcd.domain.PokerTable;
import com.solium.pcd.exception.MapperException;
import com.solium.pcd.exception.PokerChipException;

import java.util.List;

public class PokerChipForBonusOneMapper extends PokerChipMapperBase implements IMapper {

    private static final int REQUIRED_NUMBER_OF_LINES = 4;
    private static final Algorithm BONUS_ONE_ALGORITHM = Algorithm.BONUS_ONE;

    @Override
    public PokerTable mapFrom(List<String> pokerDetails) throws MapperException, PokerChipException {

        validateInput(pokerDetails);
        return getPokerTable(pokerDetails.subList(1, (pokerDetails.size())),
                             BONUS_ONE_ALGORITHM);
    }

    @Override
    public void validateInput(List<String> pokerDetails) throws MapperException {

        checkNumberOfLines(pokerDetails, REQUIRED_NUMBER_OF_LINES);
        validateRegularInput(pokerDetails.subList(1, (pokerDetails.size())), BONUS_ONE_ALGORITHM);
    }
}
