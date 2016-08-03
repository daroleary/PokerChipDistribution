package com.solium.pcd.mapper;

import com.solium.pcd.domain.PokerTable;
import com.solium.pcd.exception.MapperException;
import com.solium.pcd.exception.PokerChipException;

import java.util.List;

public interface IMapper {

    PokerTable mapFrom(List<String> pokerDetails) throws MapperException, PokerChipException;

    void validateInput(List<String> pokerDetails) throws MapperException;

}
