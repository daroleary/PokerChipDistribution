package com.solium.pcd.domain;

import com.google.common.base.Preconditions;
import com.solium.pcd.exception.PokerChipException;
import com.solium.pcd.math.Amount;

import java.text.MessageFormat;

public class PokerChip {

    private Color _color;
    private int _quantity = 0;
    private int _buyInQuantity = 0;
    private int _quantityRemaining = 0;
    private int _quantitySetAside = 0;
    private Amount _denomination = Amount.ZERO;

    /**
     * @param denomination
     * @param quantity
     * @return PokerChipDistribution object
     * @throws PokerChipException
     */
    public PokerChip(final Amount denomination, final int quantity) throws PokerChipException {
        this(Color.UNKNOWN, denomination, quantity);
    }

    /**
     * @param color
     * @param denomination
     * @param quantity
     * @return PokerChip object
     * @throws PokerChipException
     */
    public PokerChip(final Color color,
                     final Amount denomination,
                     final int quantity) throws PokerChipException {

        Preconditions.checkNotNull(color, "Inputted colour must not be null.");
        Preconditions.checkNotNull(denomination, "Inputted denomiation must not be null.");

        if (denomination.lessThanOrEqual(Amount.ZERO)) {
            throw new IllegalArgumentException(MessageFormat.format("Inputted denomiation must be greater than zero, actual is [{0}].", denomination.toString()));
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException(MessageFormat.format("Inputted quantity must be greater than zero, actual is [{0}].", quantity));
        }

        setColor(color);
        setDenomination(denomination);
        setQuantity(quantity);
        setQuantityRemaining(quantity);
    }

    /**
     * @param denomination
     * @param quantity
     * @return
     * @throws PokerChipException
     */
    static PokerChip of(final double denomination,
                        final int quantity) throws PokerChipException {
        return new PokerChip(Amount.of(denomination), quantity);
    }

    /**
     * @param color
     * @param denomination
     * @param quantity
     * @return
     * @throws PokerChipException
     */
    static PokerChip of(final Color color,
                        final double denomination,
                        final int quantity) throws PokerChipException {
        return new PokerChip(color, Amount.of(denomination), quantity);
    }

    /**
     * @param remainingBuyIn
     * @return calculated buyInQuantity
     */
    public int buyInQuantityFor(final Amount remainingBuyIn) {

        Amount buyInQuantity = buyInQuantityUpToMax(remainingBuyIn);
        Amount quantity = Amount.of(getQuantity());

        if (remainingBuyIn.greaterThan(Amount.ZERO) &&
                (quantity.subtract(buyInQuantity).greaterThan(Amount.ZERO))) {
            buyInQuantity = buyInQuantity.add(Amount.ONE);
        }

        return buyInQuantity.intValue();
    }

    /**
     * @param remainingBuyIn
     * @return max quantity up to the remaining buy in equivalent
     */
    Amount buyInQuantityUpToMax(Amount remainingBuyIn) {
        Amount maxQuantity = Amount.of(remainingBuyIn.divide(getDenomination()));

        Amount quantity = Amount.of(getQuantity());
        if (maxQuantity.greaterThan(Amount.ZERO)
                && maxQuantity.lessThan(quantity)) {
            quantity = maxQuantity;
        }
        return quantity;
    }

    /**
     * @param overBuyIn
     * @return max quantity up to over buy in equivalent
     */
    public int overBuyInQuantityUpToMax(Amount overBuyIn) {
        Amount maxQuantity = Amount.of(overBuyIn.divide(getDenomination()));

        if (maxQuantity.greaterThan(Amount.ZERO)) {
            int buyInQuantity = getBuyInQuantity();
            if (maxQuantity.greaterThan(buyInQuantity)) {
                maxQuantity = Amount.of(buyInQuantity);
            }
        }
        return maxQuantity.intValue();
    }

    /**
     * @param color the _color to set
     */
    final void setColor(final Color color) {
        _color = color;
    }

    /**
     * @return the _color
     */
    public final Color getColor() {
        return _color;
    }

    /**
     * @param denomination the _denomination to set
     */
    void setDenomination(final Amount denomination) {
        _denomination = denomination;
    }

    /**
     * @return the _denomination
     */
    public final Amount getDenomination() {
        return _denomination;
    }

    /**
     * @return Denomination in dollars formatted
     */
    public final String getDenominationInDollars() {
        return getDenomination().getDollarAmount();
    }

    /**
     * @param quantity the quantity to set
     * @throws PokerChipException
     */
    final void setQuantity(final int quantity) throws PokerChipException {

        if (quantity + getQuantitySetAside() < 1) {
            throw new PokerChipException(MessageFormat.format("Unable to set a quantity of [{0}] which together with the quantitySetAside is less than 1", quantity));
        }

        if (quantity < getBuyInQuantity()) {
            throw new PokerChipException(MessageFormat.format("Unable to set a quantity of [{0}] which is less than the current buyInQuantity of [%d]", quantity, getBuyInQuantity()));
        }

        _quantity = quantity;
        setQuantityRemaining(getQuantity() - getBuyInQuantity());
    }

    /**
     * @return the quantity
     */
    public final int getQuantity() {
        return _quantity;
    }

    /**
     * @param buyInQuantity
     * @throws PokerChipException
     */
    public void setBuyInQuantity(int buyInQuantity) throws PokerChipException {

        if (buyInQuantity > getQuantity()) {
            throw new PokerChipException(MessageFormat.format("Unable to set a buyInQuantity of {0}, which is greater than the current available quantity of {1}.",
                    getBuyInQuantity(), getQuantity()));
        }

        _buyInQuantity = buyInQuantity;
        setQuantityRemaining(getQuantity() - getBuyInQuantity());
    }

    /**
     * @return buyInQuantity
     */
    public int getBuyInQuantity() {
        return _buyInQuantity;
    }

    /**
     * @return buyInAmount
     */
    Amount getBuyInAmount() {
        return getDenomination().multiply(getBuyInQuantity());
    }

    /**
     * @return quantityRemaining
     */
    int getQuantityRemaining() {
        return _quantityRemaining;
    }

    private int getTotalAvailableQuantity() {
        return getQuantity() + getQuantitySetAside();
    }

    /**
     * @param quantityRemaining
     */
    private void setQuantityRemaining(int quantityRemaining) {
        _quantityRemaining = quantityRemaining;
    }

    public int getQuantitySetAside() {
        return _quantitySetAside;
    }

    public void setQuantitySetAside(int quantitySetAside) throws PokerChipException {

        if (quantitySetAside > getTotalAvailableQuantity()) {
            throw new PokerChipException(MessageFormat.format("Unable to set a quantitySetAside of [{0}] which is greater than the current available quantity of [%d]", getQuantitySetAside(), getQuantity()));
        }

        if (quantitySetAside < getQuantitySetAside()) {
            int quantityToSetAsideDiff = getQuantitySetAside() - quantitySetAside;
            setQuantity(getQuantity() + quantityToSetAsideDiff);
        } else if (quantitySetAside > getQuantitySetAside()) {
            int quantityToSetAsideDiff = quantitySetAside - getQuantitySetAside();
            setQuantity(getQuantity() - quantityToSetAsideDiff);
        }
        _quantitySetAside = quantitySetAside;
    }

    public void applyQuantitySetAside() throws PokerChipException {
        int quantitySetAside = getQuantitySetAside();
        if (quantitySetAside > 0) {
            setQuantitySetAside(0);
            setBuyInQuantity(getBuyInQuantity() + quantitySetAside);
        }
    }
}
