package com.solium.pcd.domain;

import com.google.common.base.Preconditions;

public final class PokerChip {

    private final Color _color;
    private final Denomination _denomination;

    public PokerChip(Builder builder) {

        Color color = builder._color;
        Denomination denomination = builder._denomination;

        Preconditions.checkNotNull(color, "Colour must not be null.");
        Preconditions.checkNotNull(denomination, "Denomiation must not be null.");

        _color = color;
        _denomination = denomination;
    }

    public Color getColor() {
        return _color;
    }

    public Denomination getDenomination() {
        return _denomination;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Color _color;
        private Denomination _denomination;

        private Builder() {
            _color = Color.UNKNOWN;
        }

        public Builder setColor(Color color) {
            _color = color;
            return this;
        }

        public Builder setDenomination(Denomination denomination) {
            _denomination = denomination;
            return this;
        }

        private Builder(PokerChip pokerChip) {
            _color = pokerChip.getColor();
            _denomination = pokerChip.getDenomination();
        }

        public PokerChip build() {
            return new PokerChip(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PokerChip pokerChip = (PokerChip) o;

        if (_color != pokerChip._color) return false;
        return _denomination == pokerChip._denomination;

    }

    @Override
    public int hashCode() {
        int result = _color != null ? _color.hashCode() : 0;
        result = 31 * result + (_denomination != null ? _denomination.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PokerChip{" +
                "color=" + _color +
                ", denomination=" + _denomination +
                '}';
    }
}
