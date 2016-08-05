package com.solium.pcd.domain;

import com.google.common.collect.ImmutableList;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    @Rule
    public ExpectedException _expectedException = ExpectedException.none();

    @Test
    public void build_withValidPlayer_returnsPlayerWithExpectedPokerChipDistribution() {

        ImmutableList<ChipRoll> pokerChipDistribution = getPokerChipDistribution();

        Player player = Player.newBuilder()
                .setAlgorithm(Algorithm.BASIC)
                .setPokerChipDistribution(pokerChipDistribution)
                .build();

        assertEquals(pokerChipDistribution, player.getPokerChipDistribution());
    }

    @Test
    public void build_withNullPokerChipDistribution_throwsNullPointerException() {

        _expectedException.expect(NullPointerException.class);
        _expectedException.expectMessage("Poker chip distribution must not be null.");

        Player player = Player.newBuilder()
                .setAlgorithm(Algorithm.BASIC)
                .setPokerChipDistribution(null)
                .build();
    }

    private ImmutableList<ChipRoll> getPokerChipDistribution() {
        return ImmutableList.of(getPokerChip());
    }

    private ChipRoll getPokerChip() {
        return ChipRoll.newBuilder()
                .setQuantity(1)
                .setPokerChip(PokerChip.newBuilder()
                                      .setDenomination(Denomination.ONE_CENT)
                                      .setColor(Color.UNKNOWN)
                                      .build())
                .build();
    }
}
