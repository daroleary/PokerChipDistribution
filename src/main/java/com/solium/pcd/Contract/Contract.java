package com.solium.pcd.contract;

public class Contract {

    private Contract() {
    }

    public static void requires(boolean predicate, String s, Object... objects) {
        if (!predicate) {
            throw new IllegalArgumentException(String.format(s, objects));
        }
    }

    public static void fail(String s, Object... objects) {
        requires(false, s, objects);
    }

    public static <T extends Exception> void requires(boolean predicate, T e) throws T {
        if (!predicate) {
            throw e;
        }
    }
}
