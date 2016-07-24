package com.solium.pcd.domain;

import com.solium.pcd.exception.AlgorithmException;
import com.solium.pcd.util.Constants.Algorithm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PokerChips extends ArrayList<PokerChip> {

    private static final long serialVersionUID = 1L;
    private int _playerCount = 0;
    private BigDecimal _buyInAmount = new BigDecimal("0.00");

    private Algorithm _algorithm = Algorithm.BASIC;

    public PokerChips(List<PokerChip> pokerChips) {
        Collections.sort(pokerChips, new PokerChipComparator());
        addAll(pokerChips);
    }

    /**
     * @param playerCount the playerCount to set
     */
    public final void setPlayerCount(final int playerCount) {
        _playerCount = playerCount;
    }

    /**
     * @return the playerCount
     */
    public final int getPlayerCount() {
        return _playerCount;
    }

    /**
     * @param buyInAmount the buyInAmount to set
     */
    public final void setBuyInAmount(final BigDecimal buyInAmount) {
        _buyInAmount = buyInAmount;
    }

    /**
     * @return the buyInAmount
     */
    public final BigDecimal getBuyInAmount() {
        return _buyInAmount;
    }

    public final BigDecimal getRemainingBuyIn() {
        return getBuyInAmount().subtract(getTotalBuyInAmount());
    }

    private BigDecimal getTotalBuyInAmount() {
        return stream()
                .map(PokerChip::getBuyInAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * @param algorithm the algorithm used for the program
     */
    public void setAlgorithm(Algorithm algorithm) {
        _algorithm = algorithm;
    }

    /**
     * @return Algorithm enumeration
     */
    public Algorithm getAlgorithm() {
        return _algorithm;
    }

    public void getResult() throws AlgorithmException {

        System.out.println("Output:");

        switch (getAlgorithm()) {
            case BASIC:
            case BONUS_ONE:
                for (PokerChip result : this) {
                    System.out.println(String.format("%s - %s", result.getDenominationInDollars(), result.getBuyInQuantity()));
                }
                break;
            case BONUS_TWO:
                for (PokerChip result : this) {
                    System.out.println(String.format("%s - %s - %d", result.getColor(), result.getDenominationInDollars(), result.getBuyInQuantity()));
                }
                break;

            default:
                throw new AlgorithmException("Encoding passed is not valid");
        }
    }
}
