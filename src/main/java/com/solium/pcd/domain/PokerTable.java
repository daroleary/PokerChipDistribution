package com.solium.pcd.domain;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSortedMap;
import com.solium.pcd.contract.Contract;
import com.solium.pcd.math.Amount;

public class PokerTable {

    private final Algorithm _algorithm;
    private final Amount _buyIn;
    private final ImmutableSortedMap<Denomination, ChipRoll> _pokerChipCollection;

    public PokerTable(Builder builder) {

        Algorithm algorithm = builder._algorithm;
        Amount buyIn = builder._buyIn;
        ImmutableSortedMap<Denomination, ChipRoll> pokerChipCollection = builder._pokerChipCollection;

        Preconditions.checkNotNull(algorithm, "Algorithm must not be null.");
        Preconditions.checkNotNull(buyIn, "Buy in must not be null.");
        Preconditions.checkNotNull(pokerChipCollection, "Poker chip collection must not be null.");

        Contract.requires(buyIn.greaterThanOrEqual(Amount.ZERO), "Buy in must be greater or equal than zero");

        _algorithm = algorithm;
        _buyIn = buyIn;
        _pokerChipCollection = pokerChipCollection;
    }

    public Algorithm getAlgorithm() {
        return _algorithm;
    }

    public Amount getBuyIn() {
        return _buyIn;
    }

    public ImmutableSortedMap<Denomination, ChipRoll> getPokerChipCollection() {
        return _pokerChipCollection;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {

        private Algorithm _algorithm;
        private Amount _buyIn;
        private ImmutableSortedMap<Denomination, ChipRoll> _pokerChipCollection;

        private Builder() {
        }

        public Builder setAlgorithm(Algorithm algorithm) {
            _algorithm = algorithm;
            return this;
        }

        public Builder setBuyIn(Amount buyIn) {
            _buyIn = buyIn;
            return this;
        }

        public Builder setPokerChipCollection(ImmutableSortedMap<Denomination, ChipRoll> pokerChipCollection) {
            _pokerChipCollection = pokerChipCollection;
            return this;
        }

        private Builder(PokerTable pokerTable) {
            _algorithm = pokerTable.getAlgorithm();
            _buyIn = pokerTable.getBuyIn();
            _pokerChipCollection = pokerTable.getPokerChipCollection();
        }

        public PokerTable build() {
            return new PokerTable(this);
        }
    }
}
