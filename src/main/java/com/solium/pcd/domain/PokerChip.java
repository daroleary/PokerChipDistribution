package com.solium.pcd.domain;

import com.google.common.base.Preconditions;
import com.solium.pcd.exception.PokerChipException;

import java.math.BigDecimal;
import java.text.MessageFormat;

import static com.solium.pcd.util.Util.divideFor;
import static com.solium.pcd.util.Util.getDollarAmount;
import static com.solium.pcd.util.Util.isGreaterThanZero;

public class PokerChip {

    private Color _color;
    private int _quantity = 0;
    private int _buyInQuantity = 0;
    private int _quantityRemaining = 0;
    private int _quantitySetAside = 0;

    private BigDecimal _denomination = new BigDecimal("0.00");

    /**
     * @param denomination
     * @param quantity
     * @return PokerChipDistribution object
     * @throws PokerChipException
     */
    public PokerChip(final BigDecimal denomination, final int quantity) throws PokerChipException {
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
                     final BigDecimal denomination,
                     final int quantity) throws PokerChipException {

        Preconditions.checkNotNull(color, "Inputted colour must not be null.");
        Preconditions.checkNotNull(denomination, "Inputted denomiation must not be null.");

        if (denomination.compareTo(new BigDecimal("0.00")) <= 0) {
            throw new IllegalArgumentException(String.format("Inputted denomiation must be greater than zero, actual is [%s].", denomination.toString()));
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException(String.format("Inputted quantity must be greater than zero, actual is [%s].", Integer.toString(quantity)));
        }

        setColor(color);
        setDenomination(denomination);
        setQuantity(quantity);
        setQuantityRemaining(quantity);
    }

    /**
     * @param remainingBuyIn
     * @return calculated buyInQuantity
     */
    public int buyInQuantityFor(final BigDecimal remainingBuyIn) {

        int buyInQuantity = buyInQuantityUpToMax(remainingBuyIn);

        if (isGreaterThanZero(remainingBuyIn) &&
                (getQuantity() - buyInQuantity > 0)) {
            buyInQuantity = buyInQuantity + 1;
        }

        return buyInQuantity;
    }

    /**
     * @param remainingBuyIn
     * @return max quantity up to the remaining buy in equivalent
     */
    int buyInQuantityUpToMax(BigDecimal remainingBuyIn) {
        int maxQuantity = divideFor(remainingBuyIn, getDenomination()).intValue();

        int quantity = getQuantity();
        if (maxQuantity > 0 && maxQuantity < quantity) {
            quantity = maxQuantity;
        }
        return quantity;
    }

    /**
     * @param overBuyIn
     * @return max quantity up to over buy in equivalent
     */
    public int overBuyInQuantityUpToMax(BigDecimal overBuyIn) {
        int maxQuantity = divideFor(overBuyIn, getDenomination()).intValue();

        if (maxQuantity > 0) {
            int buyInQuantity = getBuyInQuantity();
            if (maxQuantity > buyInQuantity) {
                maxQuantity = buyInQuantity;
            }
        }
        return maxQuantity;
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
    void setDenomination(final BigDecimal denomination) {
        _denomination = denomination;
    }

    /**
     * @return the _denomination
     */
    public final BigDecimal getDenomination() {
        return _denomination;
    }

    /**
     * @return Denomination in dollars formatted
     */
    public final String getDenominationInDollars() {
        return getDollarAmount(getDenomination());
    }

    /**
     * @param quantity the quantity to set
     * @throws PokerChipException
     */
    final void setQuantity(final int quantity) throws PokerChipException {

        if (quantity + getQuantitySetAside() < 1) {
            throw new PokerChipException(String.format("Unable to set a quantity of [%d] which together with the quantitySetAside is less than 1", quantity));
        }

        if (quantity < _buyInQuantity) {
            throw new PokerChipException(String.format("Unable to set a quantity of [%d] which is less than the current buyInQuantity of [%d]", quantity, _buyInQuantity));
        }

        _quantity = quantity;
        _quantityRemaining = _quantity - _buyInQuantity;
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

        if (buyInQuantity > _quantity) {
            throw new PokerChipException(MessageFormat.format("Unable to set a buyInQuantity of {0}, which is greater than the current available quantity of {1}.",
                    _buyInQuantity, _quantity));
        }

        _buyInQuantity = buyInQuantity;
        _quantityRemaining = _quantity - buyInQuantity;
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
    BigDecimal getBuyInAmount() {
        return getDenomination().multiply(new BigDecimal(getBuyInQuantity()));
    }

    /**
     * @return quantityRemaining
     */
    int getQuantityRemaining() {
        return _quantityRemaining;
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

        if (quantitySetAside > (_quantitySetAside + _quantity)) {
            throw new PokerChipException(String.format("Unable to set a quantitySetAside of [%d] which is greater than the current available quantity of [%d]", _quantitySetAside, _quantity));
        }

        if (quantitySetAside < _quantitySetAside) {
            setQuantity(_quantity + (_quantitySetAside - quantitySetAside));
        } else if (quantitySetAside > _quantitySetAside) {
            setQuantity(_quantity - (quantitySetAside - _quantitySetAside));
        }
        _quantitySetAside = quantitySetAside;
    }

    public void applyQuantitySetAside() throws PokerChipException {
        if (getQuantitySetAside() > 0) {
            int quantitySetAside = getQuantitySetAside();
            setQuantitySetAside(0);
            setBuyInQuantity(getBuyInQuantity() + quantitySetAside);
        }
    }
}
