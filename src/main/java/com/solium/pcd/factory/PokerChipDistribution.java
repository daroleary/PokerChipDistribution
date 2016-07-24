package com.solium.pcd.factory;

import com.solium.pcd.bo.IPokerChipDistributionStrategy;
import com.solium.pcd.domain.PokerChips;
import com.solium.pcd.exception.AlgorithmException;
import com.solium.pcd.exception.CalculationException;
import com.solium.pcd.exception.MapperException;
import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.mapper.IMapper;

import java.util.List;

class PokerChipDistribution implements IPokerChipDistribution {

    private IMapper _mapper;
    private IPokerChipDistributionStrategy _strategy;

    PokerChipDistribution(IMapper mapper, IPokerChipDistributionStrategy strategy) {
        _mapper = mapper;
        _strategy = strategy;
    }

    public PokerChips pokerDistributionFor(List<String> inputs) throws CalculationException, MapperException, PokerChipException, AlgorithmException {
        return _strategy.calculate(_mapper.mapFrom(inputs));
    }
}
