package com.solium.pcd.mapper;

import com.solium.pcd.domain.PokerChips;
import com.solium.pcd.exception.MapperException;
import com.solium.pcd.exception.PokerChipException;

import java.util.List;

public interface IMapper {

    PokerChips mapFrom(List<String> pokerListDetails) throws MapperException, PokerChipException;

    void validateInput(List<String> pokerList) throws MapperException;
}
