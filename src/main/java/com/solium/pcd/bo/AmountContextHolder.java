package com.solium.pcd.bo;

import com.google.common.base.Preconditions;
import com.solium.pcd.math.Amount;

class AmountContextHolder {

    private static final ThreadLocal<Amount> _amountContextHolder =
            new ThreadLocal<>();

    private AmountContextHolder(Amount amount) {
        setAmount(amount);
    }

    public static AmountContextHolder of(Amount amount) {
        return new AmountContextHolder(amount);
    }

    /**
     * Reset the AmountContext for the current thread.
     */
    public void resetAmountContext() {
        _amountContextHolder.remove();
    }

    public Amount getAmount() {
        return _amountContextHolder.get();
    }

    public void setAmount(Amount amount) {
        Preconditions.checkNotNull(amount, "Amount must not be null");

        _amountContextHolder.set(amount);
    }
}
