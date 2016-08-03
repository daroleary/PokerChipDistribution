package com.solium.pcd.domain;

import com.google.common.base.Preconditions;
import com.solium.pcd.contract.Contract;
import com.solium.pcd.math.Amount;

import java.text.MessageFormat;
import java.util.Optional;

public enum Denomination {

    ONE_CENT(Amount.of(0.01)),
    FIVE_CENTS(Amount.of(0.05)),
    TEN_CENTS(Amount.of(0.10)),
    TWENTY_FIVE_CENTS(Amount.of(0.25)),
    FIFTY_CENT(Amount.of(0.50)),
    SEVENTY_FIVE_CENT(Amount.of(0.75)),
    ONE_DOLLAR(Amount.of(1.00)),
    TWO_DOLLARS(Amount.of(2.00)),
    FIVE_DOLLARS(Amount.of(5.00)),
    TEN_DOLLARS(Amount.of(10.00)),
    TWENTY_DOLLARS(Amount.of(20.00)),
    FIFTY_DOLLARS(Amount.of(50.00)),
    ONE_HUNDRED_DOLLARS(Amount.of(100.00)),
    ONE_THOUSAND_DOLLARS(Amount.of(1000.00));

    private final Amount _amount;

    Denomination(Amount amount) {
        _amount = amount;
    }

    public Amount getAmount() {
        return _amount;
    }

    public static Denomination of(String amountName) {
        Preconditions.checkNotNull(amountName, "Amount must not be null.");

        Optional<Denomination> denomination = getOptionalDenomination(Amount.of(amountName));

        Contract.requires(denomination.isPresent(), MessageFormat.format("Unable to get a denomination from: {0}", amountName));
        //noinspection OptionalGetWithoutIsPresent
        return denomination.get();
    }

    private static Optional<Denomination> getOptionalDenomination(Amount amount) {

        Denomination denomination = null;
        for (Denomination denom : Denomination.values()) {
            if (denom.getAmount().equals((amount))) {
                denomination = denom;
            }
        }
        return Optional.ofNullable(denomination);
    }

    public static Denomination of(double amount) {
        return of(Amount.of(amount));
    }

    public static Denomination of(Amount amount) {
        Preconditions.checkNotNull(amount, "Amount must not be null.");

        Optional<Denomination> optionalDenomination = getOptionalDenomination(amount);

        Contract.requires(optionalDenomination.isPresent(), MessageFormat.format("Unable to get a denomination from: {0}", amount));
        //noinspection OptionalGetWithoutIsPresent
        return optionalDenomination.get();
    }
}
