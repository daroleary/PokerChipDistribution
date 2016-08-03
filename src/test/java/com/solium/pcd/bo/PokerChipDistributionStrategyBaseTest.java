package com.solium.pcd.bo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedMap;
import com.solium.pcd.domain.Algorithm;
import com.solium.pcd.domain.ChipRoll;
import com.solium.pcd.domain.Color;
import com.solium.pcd.domain.Denomination;
import com.solium.pcd.domain.DenominationComparator;
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

        ImmutableSortedMap<Denomination, ChipRoll> pokerChipRollsBeforeSetAside =
                denominationToChipRoll(chipRollFrom(5, Denomination.ONE_CENT),
                                       chipRollFrom(3, Denomination.FIVE_CENTS));

        ImmutableSortedMap<Denomination, ChipRoll> pokerChipRollsAfterSetAside =
                denominationToChipRoll(chipRollFrom(4, Denomination.ONE_CENT),
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
                                                                                                      ImmutableSortedMap<Denomination, ChipRoll> denominationToChipRolls,
                                                                                                      ImmutableSortedMap<Denomination, ChipRoll> expectedChipRollsSetAside) {

        TestPokerChipDistributionStrategyBase pokerChipDistributionStrategyBase = getTestPokerChipDistributionStrategyBase();

        ImmutableSortedMap<Denomination, ChipRoll> actualDenominationToChipRollSetAside =
                pokerChipDistributionStrategyBase.setPokerChipsAsideIfBonusOneAlgorithm(denominationToChipRolls, 1);
        assertEquals(expectedChipRollsSetAside, actualDenominationToChipRollSetAside);
    }

    @Test
    public void setPokerChipsAsideIfBonusOneAlgorithm_insufficientQuantityToSetAside_throwsIllegalArgumentException() {

        ImmutableSortedMap<Denomination, ChipRoll> pokerChipRollsAfterSetAside =
                denominationToChipRoll(chipRollFrom(1, Denomination.ONE_CENT));

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

    private ImmutableSortedMap<Denomination, ChipRoll> denominationToChipRoll(ChipRoll... chipRolls) {
        ImmutableSortedMap.Builder<Denomination, ChipRoll> pokerChipRollsAfterSetAside
                = new ImmutableSortedMap.Builder<>(new DenominationComparator());

        for (ChipRoll chipRoll : chipRolls) {
            PokerChip pokerChip = chipRoll.getPokerChip();
            pokerChipRollsAfterSetAside.put(pokerChip.getDenomination(), chipRoll);
        }

        return pokerChipRollsAfterSetAside.build();
    }

    private TestPokerChipDistributionStrategyBase getTestPokerChipDistributionStrategyBase() {
        return new TestPokerChipDistributionStrategyBase();
    }

    private class TestPokerChipDistributionStrategyBase extends PokerChipDistributionStrategyBase {
    }
}
