package com.solium.pcd.domain;

import com.google.common.base.Preconditions;

public final class ChipRoll {

    private final int _quantity;
    private final PokerChip _pokerChip;

    public ChipRoll(Builder builder) {

        PokerChip pokerChip = builder._pokerChip;

        Preconditions.checkNotNull(pokerChip, "PokerChip must not be null.");

        if (builder._quantity < 0) {
            throw new IllegalArgumentException("Quantity must be greater than or equal zero");
        }

        _quantity = builder._quantity;
        _pokerChip = pokerChip;
    }

    public int getQuantity() {
        return _quantity;
    }

    public PokerChip getPokerChip() {
        return _pokerChip;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private int _quantity;
        private PokerChip _pokerChip;

        private Builder() {
        }

        public Builder setQuantity(int quantity) {
            _quantity = quantity;
            return this;
        }

        public Builder setPokerChip(PokerChip pokerChip) {
            _pokerChip = pokerChip;
            return this;
        }

        private Builder(ChipRoll chipRoll) {
            _quantity = chipRoll.getQuantity();
            _pokerChip = chipRoll.getPokerChip();
        }

        public ChipRoll build() {
            return new ChipRoll(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChipRoll chipRoll = (ChipRoll) o;

        if (_quantity != chipRoll._quantity) return false;
        return _pokerChip != null ? _pokerChip.equals(chipRoll._pokerChip) : chipRoll._pokerChip == null;

    }

    @Override
    public int hashCode() {
        int result = _quantity;
        result = 31 * result + (_pokerChip != null ? _pokerChip.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChipRoll{" +
                "quantity=" + _quantity +
                ", pokerChip=" + _pokerChip +
                '}';
    }
}
