package com.solium.pcd.util;

import com.google.common.collect.ImmutableList;
import com.solium.pcd.exception.PokerChipException;

public class TestUtil {

    private TestUtil() {
    }

    public static ImmutableList<String> getPokerChipDistributionList(final String chipBreakdown,
                                                                     final String buyInAmount,
                                                                     final String playerCount) {

        ImmutableList.Builder<String> pokerChipsInfo = new ImmutableList.Builder<>();
        pokerChipsInfo.add(chipBreakdown);
        pokerChipsInfo.add(playerCount);
        pokerChipsInfo.add(buyInAmount);

        return pokerChipsInfo.build();
    }

    public static ImmutableList<String> getPokerChipDistributionListForBonusOneAndTwo(final String problemType,
                                                                                      final String chipBreakdown,
                                                                                      final String buyInAmount,
                                                                                      final String playerCount) {

        ImmutableList.Builder<String> pokerChipsInfo = new ImmutableList.Builder<>();
        pokerChipsInfo.add(problemType);
        pokerChipsInfo.add(chipBreakdown);
        pokerChipsInfo.add(playerCount);
        pokerChipsInfo.add(buyInAmount);

        return pokerChipsInfo.build();
    }
}
