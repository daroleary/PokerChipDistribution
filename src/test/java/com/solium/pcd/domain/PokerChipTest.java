package com.solium.pcd.domain;

import com.solium.pcd.exception.PokerChipException;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class PokerChipTest {

    private static final BigDecimal ONE = BigDecimal.ONE;
    private static final BigDecimal TWO = BigDecimal.valueOf(2);
    private static final BigDecimal ZERO = BigDecimal.ZERO;
    private static final BigDecimal THREE = BigDecimal.valueOf(3);

    @Test
    public void shouldBeValidPokerChipObject() throws PokerChipException {

        PokerChip chip = new PokerChip(ONE, 1);

        assertEquals(ONE, chip.getDenomination());
        assertEquals(1, chip.getQuantity());
    }

    @Test
    public void shouldBeValidPokerChipObjectTestTwo() throws PokerChipException {

        PokerChip chip = new PokerChip(Color.GREEN, ONE, 1);

        assertEquals(ONE, chip.getDenomination());
        assertEquals(1, chip.getQuantity());
        assertEquals(Color.GREEN, chip.getColor());
    }

    @Test
    public void shouldBeValidPokerChipObjectTestThree() throws PokerChipException {

        PokerChip chip = new PokerChip(Color.GREEN, ONE, 1);
        chip.setBuyInQuantity(1);

        assertEquals(ONE, chip.getDenomination());
        assertEquals(1, chip.getQuantity());
        assertEquals(Color.GREEN, chip.getColor());
        assertEquals(1, chip.getBuyInQuantity());
        assertEquals(0, chip.getQuantityRemaining());
    }

    @Test
    public void shouldBeValidPokerChipObjectTestFour() throws PokerChipException {

        PokerChip chip = new PokerChip(Color.GREEN, ONE, 2);
        chip.setBuyInQuantity(1);
        chip.setQuantitySetAside(1);

        assertEquals(ONE, chip.getDenomination());
        assertEquals(1, chip.getQuantity());
        assertEquals(Color.GREEN, chip.getColor());
        assertEquals(1, chip.getBuyInQuantity());
        assertEquals(0, chip.getQuantityRemaining());
        assertEquals(1, chip.getQuantitySetAside());
    }

    @Test
    public void shouldBeValidPokerChipObjectTestFive() throws PokerChipException {

        PokerChip chip = new PokerChip(Color.GREEN, ONE, 1);
        chip.setColor(Color.BLUE);
        chip.setDenomination(TWO);
        chip.setQuantity(2);

        assertEquals(TWO, chip.getDenomination());
        assertEquals(2, chip.getQuantity());
        assertEquals(Color.BLUE, chip.getColor());
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForConstructor() throws PokerChipException {

        new PokerChip(null, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionForConstructor() throws PokerChipException {

        new PokerChip(BigDecimal.valueOf(0.00), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionForConstructorTestTwo() throws PokerChipException {

        new PokerChip(ONE, 0);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowIllegalArgumentExceptionForConstructorTestThree() throws PokerChipException {

        new PokerChip(null, ONE, 0);
    }

    @Test(expected = PokerChipException.class)
    public void shouldThrowPokerChipExceptionForQuantityOfZero() throws PokerChipException {

        PokerChip chip = getGreenPokerChipOfOne();
        chip.setQuantity(0);
    }

    @Test(expected = PokerChipException.class)
    public void shouldThrowPokerChipExceptionWhenQuantityIsLessThanBuyInQuantity() throws PokerChipException {

        PokerChip chip = new PokerChip(Color.GREEN, ONE, 1);
        chip.setBuyInQuantity(1);
        chip.setQuantity(0);
    }

    @Test(expected = PokerChipException.class)
    public void shouldThrowPokerChipExceptionWhenBuyInQuantityIsGreaterThanQuantity() throws PokerChipException {

        PokerChip chip = new PokerChip(Color.GREEN, ONE, 1);
        chip.setBuyInQuantity(2);
    }

    @Test(expected = PokerChipException.class)
    public void shouldThrowPokerChipExceptionWhenQuantitySetAsideIsGreaterThanPreviousQuantitySetAsideAndQuantity() throws PokerChipException {

        PokerChip chip = new PokerChip(Color.GREEN, ONE, 1);
        chip.setQuantitySetAside(2);
    }

    @Test(expected = PokerChipException.class)
    public void shouldThrowPokerChipExceptionWhenQuantitySetAsideIsGreaterThanQuantity() throws PokerChipException {

        PokerChip chip = new PokerChip(Color.GREEN, ONE, 2);
        chip.setQuantitySetAside(1);
        assertEquals(1, chip.getQuantity());
        chip.setQuantitySetAside(3);
    }

    @Test
    public void overBuyInQuantityUpToMax_maxQuantityIsZero_returnsZero() throws PokerChipException {

        PokerChip chip = getGreenPokerChipOfOne();
        int overBuyInQuantity = chip.overBuyInQuantityUpToMax(ZERO);
        assertEquals(0, overBuyInQuantity);
    }

    @Test
    public void overBuyInQuantityUpToMax_maxQuantityIsLessThanBuyIn_returnsMaxQuantity() throws PokerChipException {

        PokerChip chip = new PokerChip(Color.GREEN, ONE, 2);
        chip.setBuyInQuantity(2);

        int overBuyInQuantity = chip.overBuyInQuantityUpToMax(ONE);
        assertEquals(1, overBuyInQuantity);
    }

    @Test
    public void overBuyInQuantityUpToMax_maxQuantityIsGreaterThanBuyIn_returnsBuyIn() throws PokerChipException {

        PokerChip chip = new PokerChip(Color.GREEN, ONE, 2);
        chip.setBuyInQuantity(2);

        int overBuyInQuantity = chip.overBuyInQuantityUpToMax(THREE);
        assertEquals(2, overBuyInQuantity);
    }

    @Test
    public void buyInQuantityUpToMax_maxQuantityIsZero_quantityIsOne_returnsOne() throws PokerChipException {

        PokerChip chip = getGreenPokerChipOfOne();
        int overBuyInQuantity = chip.buyInQuantityUpToMax(ZERO);
        assertEquals(1, overBuyInQuantity);
    }

    @Test
    public void buyInQuantityUpToMax_maxQuantityIsLessThanQuantity_returnsMaxQuantity() throws PokerChipException {

        PokerChip chip = new PokerChip(Color.GREEN, ONE, 2);

        int overBuyInQuantity = chip.buyInQuantityUpToMax(ONE);
        assertEquals(1, overBuyInQuantity);
    }

    @Test
    public void buyInQuantityUpToMax_maxQuantityIsGreaterThanBuyIn_returnsQuantity() throws PokerChipException {

        PokerChip chip = getGreenPokerChipOfOne();

        int overBuyInQuantity = chip.buyInQuantityUpToMax(THREE);
        assertEquals(1, overBuyInQuantity);
    }

    @Test
    public void buyInQuantityFor_buyInQuantityIsZero_quantityIsTwo_returnsBuyInQuantityPlusOne() throws PokerChipException {

        PokerChip pokerChip = Mockito.spy(new PokerChip(Color.GREEN, ONE, 2));

        BigDecimal remainingBuyIn = ONE;
        Mockito.when(pokerChip.buyInQuantityUpToMax(remainingBuyIn))
                .thenReturn(0);

        int buyInQuantity = pokerChip.buyInQuantityFor(remainingBuyIn);
        assertEquals(1, buyInQuantity);
    }

    @Test
    public void buyInQuantityFor_buyInQuantityIsOne_remainingBuyInIsZero_returnsBuyInQuantity() throws PokerChipException {

        PokerChip pokerChip = Mockito.spy(new PokerChip(Color.GREEN, ONE, 2));

        BigDecimal remainingBuyIn = ZERO;
        Mockito.when(pokerChip.buyInQuantityUpToMax(remainingBuyIn))
                .thenReturn(1);

        int buyInQuantity = pokerChip.buyInQuantityFor(remainingBuyIn);
        assertEquals(1, buyInQuantity);
    }

    @Test
    public void buyInQuantityFor_buyInQuantityIsOne_quantityIsOne_returnsBuyInQuantity() throws PokerChipException {

        PokerChip pokerChip = Mockito.spy(new PokerChip(Color.GREEN, ONE, 1));

        Mockito.when(pokerChip.buyInQuantityUpToMax(ONE))
                .thenReturn(1);

        int buyInQuantity = pokerChip.buyInQuantityFor(ONE);
        assertEquals(1, buyInQuantity);
    }

    private PokerChip getGreenPokerChipOfOne() {
        try {
            return new PokerChip(Color.GREEN, ONE, 1);
        } catch (PokerChipException e) {
            throw new RuntimeException(e);
        }
    }
}
