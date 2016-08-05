package com.solium.pcd.bo;

import com.google.common.collect.ImmutableList;
import com.solium.pcd.domain.Algorithm;
import com.solium.pcd.domain.ChipRoll;
import com.solium.pcd.domain.Color;
import com.solium.pcd.domain.Denomination;
import com.solium.pcd.domain.PokerChip;
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
public class PokerChipDistributionStrategyBaseTest {

    @Rule
    public ExpectedException _expectedException = ExpectedException.none();

    private List<Object[]> getValidDataForSetAside() {

        ImmutableList<ChipRoll> pokerChipRollsBeforeSetAside =
                ImmutableList.of(chipRollFrom(5, Denomination.ONE_CENT),
                                 chipRollFrom(3, Denomination.FIVE_CENTS));

        ImmutableList<ChipRoll> pokerChipRollsAfterSetAside =
                ImmutableList.of(chipRollFrom(4, Denomination.ONE_CENT),
                                 chipRollFrom(2, Denomination.FIVE_CENTS));

        final ImmutableList.Builder<Object[]> cases = ImmutableList.builder();
        cases.add(new Object[]{"1 set aside for all chipRolls", Algorithm.BONUS_ONE, pokerChipRollsBeforeSetAside, pokerChipRollsAfterSetAside});
        return cases.build();
    }

    @SuppressWarnings("TestMethodWithIncorrectSignature")
    @Test
    @TestCaseName(value = "Expecting exception with {0}")
    @Parameters(method = "getValidDataForSetAside")
    public void setPokerChipsAsideIfBonusOneAlgorithm_withParameters_returnsCorrectPokerChipsSetAside(@SuppressWarnings("UnusedParameters") String testName,
                                                                                                      Algorithm algorithm,
                                                                                                      ImmutableList<ChipRoll> denominationToChipRolls,
                                                                                                      ImmutableList<ChipRoll> expectedChipRollsSetAside) {

        TestPokerChipDistributionStrategyBase pokerChipDistributionStrategyBase = getTestPokerChipDistributionStrategyBase();

        ImmutableList<ChipRoll> actualDenominationToChipRollSetAside =
                pokerChipDistributionStrategyBase.setPokerChipsAsideIfBonusOneAlgorithm(denominationToChipRolls, 1);
        assertEquals(expectedChipRollsSetAside, actualDenominationToChipRollSetAside);
    }

    @Test
    public void setPokerChipsAsideIfBonusOneAlgorithm_insufficientQuantityToSetAside_throwsIllegalArgumentException() {

        ImmutableList<ChipRoll> pokerChipRollsAfterSetAside =
                ImmutableList.of(chipRollFrom(1, Denomination.ONE_CENT));

        TestPokerChipDistributionStrategyBase pokerChipDistributionStrategyBase = getTestPokerChipDistributionStrategyBase();

        _expectedException.expect(IllegalArgumentException.class);
        _expectedException.expectMessage(
                "Unable to set a quantitySetAside of [2] which is greater than the current available quantity of [1]");
        pokerChipDistributionStrategyBase.setPokerChipsAsideIfBonusOneAlgorithm(pokerChipRollsAfterSetAside, 2);
    }

    private ChipRoll chipRollFrom(int quantity, Denomination denomination) {
        PokerChip pokerChip = PokerChip.newBuilder()
                .setColor(Color.UNKNOWN)
                .setDenomination(denomination)
                .build();

        return ChipRoll.newBuilder()
                .setQuantity(quantity)
                .setPokerChip(pokerChip)
                .build();
    }

    private TestPokerChipDistributionStrategyBase getTestPokerChipDistributionStrategyBase() {
        return new TestPokerChipDistributionStrategyBase();
    }

    private class TestPokerChipDistributionStrategyBase extends PokerChipDistributionStrategyBase {
    }
}
