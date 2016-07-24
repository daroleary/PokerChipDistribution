package com.solium.pcd.util;

import com.google.common.collect.ImmutableList;
import org.junit.Assert;

import java.text.MessageFormat;
import java.util.Collection;

public class TestCase extends Assert {

    protected void assertSize(int expected, Collection<?> collection) {
        String errorMessage = MessageFormat.format("The collection should have had {0} items, instead it had {1} items.",
                expected, collection.size());
        assertEquals(errorMessage, expected, collection.size());
    }

    public static ImmutableList<String> getTestCaseOneData() {

        ImmutableList.Builder<String> pokerDetails = new ImmutableList.Builder<>();

        pokerDetails.add("50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05");
        pokerDetails.add("10");
        pokerDetails.add("$10.00");

        return pokerDetails.build();
    }

    public static ImmutableList<String> getTestCaseTwoData() {

        ImmutableList.Builder<String> pokerDetails = new ImmutableList.Builder<>();

        pokerDetails.add("B1");
        pokerDetails.add("50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05");
        pokerDetails.add("10");
        pokerDetails.add("$10.00");

        return pokerDetails.build();
    }

    public static ImmutableList<String> getTestCaseThreeData() {

        ImmutableList.Builder<String> pokerDetails = new ImmutableList.Builder<>();

        pokerDetails.add("B2");
        pokerDetails.add("50/Red,50/Blue,100/Black,100/Green,100/Yellow,100/Taupe");
        pokerDetails.add("10");
        pokerDetails.add("$10.00");

        return pokerDetails.build();
    }
}
