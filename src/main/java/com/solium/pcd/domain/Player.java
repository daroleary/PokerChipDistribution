package com.solium.pcd.domain;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.solium.pcd.exception.AlgorithmException;

import java.text.MessageFormat;
import java.util.function.Function;

public class Player {

    private final Algorithm _algorithm;
    private final ImmutableList<ChipRoll> _pokerChipDistribution;

    public Player(Builder builder) {

        Algorithm algorithm = builder._algorithm;
        ImmutableList<ChipRoll> pokerChipDistribution = builder._pokerChipDistribution;

        Preconditions.checkNotNull(algorithm, "Algorithm must not be null.");
        Preconditions.checkNotNull(pokerChipDistribution, "Poker chip distribution must not be null.");

        _algorithm = algorithm;
        _pokerChipDistribution = pokerChipDistribution;
    }

    public Algorithm getAlgorithm() {
        return _algorithm;
    }

    public ImmutableList<ChipRoll> getPokerChipDistribution() {
        return _pokerChipDistribution;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {

        private Algorithm _algorithm;
        private ImmutableList<ChipRoll> _pokerChipDistribution;

        private Builder() {
        }

        public Builder setAlgorithm(Algorithm algorithm) {
            _algorithm = algorithm;
            return this;
        }

        public Builder setPokerChipDistribution(ImmutableList<ChipRoll> pokerChipDistribution) {
            _pokerChipDistribution = pokerChipDistribution;
            return this;
        }

        private Builder(Player player) {
            _algorithm = player.getAlgorithm();
            _pokerChipDistribution = player.getPokerChipDistribution();
        }

        public Player build() {
            return new Player(this);
        }
    }

    public void getResult() throws AlgorithmException {

        System.out.println("Output:");

        switch (getAlgorithm()) {
            case BASIC:
            case BONUS_ONE:
                getPokerChipDistribution().stream()
                        .map(toNormalFormat())
                        .forEach(System.out::println);
                break;
            case BONUS_TWO:
                getPokerChipDistribution().stream()
                        .map(toBonusTwoFormat())
                        .forEach(System.out::println);
                break;

            default:
                throw new AlgorithmException("Encoding passed is not valid");
        }
    }

    private Function<ChipRoll, String> toNormalFormat() {
        return cr -> {
            String denominationInDollars = cr.getPokerChip()
                    .getDenomination()
                    .getAmount()
                    .getDollarAmount();
            return MessageFormat.format("{0} - {1}",
                                        denominationInDollars,
                                        cr.getQuantity());
        };
    }

    private Function<ChipRoll, String> toBonusTwoFormat() {
        return cr -> {
            PokerChip pokerChip = cr.getPokerChip();
            String denominationInDollars = pokerChip.getDenomination()
                    .getAmount()
                    .getDollarAmount();
            return MessageFormat.format("{0} - {1} - {2}",
                                        pokerChip.getColor().getColorName(),
                                        denominationInDollars,
                                        cr.getQuantity());
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (_algorithm != player._algorithm) return false;
        return _pokerChipDistribution != null ? _pokerChipDistribution.equals(player._pokerChipDistribution) : player._pokerChipDistribution == null;

    }

    @Override
    public int hashCode() {
        int result = _algorithm != null ? _algorithm.hashCode() : 0;
        result = 31 * result + (_pokerChipDistribution != null ? _pokerChipDistribution.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Player{" +
                "_algorithm=" + _algorithm +
                ", _pokerChipDistribution=" + _pokerChipDistribution +
                '}';
    }
}
