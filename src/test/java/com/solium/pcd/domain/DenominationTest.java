package com.solium.pcd.domain;

import com.google.common.collect.ImmutableList;
import com.solium.pcd.math.Amount;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class DenominationTest {

    @Rule
    public ExpectedException _expectedException = ExpectedException.none();

    private List<Object[]> getValidDataForDenomination() {

        final ImmutableList.Builder<Object[]> cases = ImmutableList.builder();
        cases.add(new Object[]{"0.01 is ONE_CENT", "0.01", Denomination.ONE_CENT});
        cases.add(new Object[]{"0.05 is FIVE_CENTS", "0.05", Denomination.FIVE_CENTS});
        cases.add(new Object[]{"0.10 is TEN_CENTS", "0.10", Denomination.TEN_CENTS});
        cases.add(new Object[]{"0.25 is TWENTY_FIVE_CENTS", "0.25", Denomination.TWENTY_FIVE_CENTS});
        cases.add(new Object[]{"0.50 is FIFTY_CENT", "0.50", Denomination.FIFTY_CENT});
        cases.add(new Object[]{"1.00 is ONE_DOLLAR", "1.00", Denomination.ONE_DOLLAR});
        cases.add(new Object[]{"2.00 is TWO_DOLLARS", "2.00", Denomination.TWO_DOLLARS});
        cases.add(new Object[]{"5.00 is FIVE_DOLLARS", "5.00", Denomination.FIVE_DOLLARS});
        cases.add(new Object[]{"10.00 is TEN_DOLLARS", "10.00", Denomination.TEN_DOLLARS});
        cases.add(new Object[]{"20.00 is TWENTY_DOLLARS", "20.00", Denomination.TWENTY_DOLLARS});
        cases.add(new Object[]{"50.00 is FIFTY_DOLLARS", "50.00", Denomination.FIFTY_DOLLARS});
        cases.add(new Object[]{"100.00 is ONE_HUNDRED_DOLLARS", "100.00", Denomination.ONE_HUNDRED_DOLLARS});
        cases.add(new Object[]{"1000.00 is ONE_THOUSAND_DOLLARS", "1000.00", Denomination.ONE_THOUSAND_DOLLARS});
        return cases.build();
    }

    @SuppressWarnings("TestMethodWithIncorrectSignature")
    @Test
    @TestCaseName(value = "Return actual Denomination with {0}")
    @Parameters(method = "getValidDataForDenomination")
    public void of_withParameters_returnsCorrectDenomination(@SuppressWarnings("UnusedParameters") String testName,
                                                             String amount,
                                                             Denomination expectedDenomination) {

        assertEquals(expectedDenomination, Denomination.of(amount));
    }

    private List<Object[]> getInvalidDataForDenominationFromAmountName() {

        final ImmutableList.Builder<Object[]> cases = ImmutableList.builder();
        cases.add(new Object[]{"Amount of null", null, NullPointerException.class, "Amount must not be null."});
        cases.add(new Object[]{"Amount is not present", "0.00", IllegalArgumentException.class, "Unable to get a denomination from: 0.00"});
        return cases.build();
    }

    @SuppressWarnings("TestMethodWithIncorrectSignature")
    @Test
    @TestCaseName(value = "Expecting Exception with {0}")
    @Parameters(method = "getInvalidDataForDenominationFromAmountName")
    public <E extends Exception> void of_amountName_withParameters_throwsException(@SuppressWarnings("UnusedParameters") String testName,
                                                                        String amount,
                                                                        Class<E> exception,
                                                                        String expectedExceptionMessage) {

        _expectedException.expect(exception);
        _expectedException.expectMessage(expectedExceptionMessage);
        Denomination.of(amount);
    }

    private List<Object[]> getInvalidDataForDenominationFromAmount() {

        final ImmutableList.Builder<Object[]> cases = ImmutableList.builder();
        cases.add(new Object[]{"Amount of null", null, NullPointerException.class, "Amount must not be null."});
        cases.add(new Object[]{"Amount is 0.00", Amount.of(0.00), IllegalArgumentException.class, "Unable to get a denomination from: Amount{numberOfUnits=0.0}"});
        cases.add(new Object[]{"Amount is -0.01", Amount.of(-0.01), IllegalArgumentException.class, "Unable to get a denomination from: Amount{numberOfUnits=-0.01}"});
        return cases.build();
    }

    @SuppressWarnings("TestMethodWithIncorrectSignature")
    @Test
    @TestCaseName(value = "Expecting Exception with {0}")
    @Parameters(method = "getInvalidDataForDenominationFromAmount")
    public <E extends Exception> void of_amount_withParameters_throwsException(@SuppressWarnings("UnusedParameters") String testName,
                                                                        Amount amount,
                                                                        Class<E> exception,
                                                                        String expectedExceptionMessage) {

        _expectedException.expect(exception);
        _expectedException.expectMessage(expectedExceptionMessage);
        Denomination.of(amount);
    }
}
