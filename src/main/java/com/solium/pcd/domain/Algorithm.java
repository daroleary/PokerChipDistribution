package com.solium.pcd.domain;

public enum Algorithm {
    BASIC(""),
    BONUS_ONE("B1"),
    BONUS_TWO("B2");

    private final String _algorithm;

    Algorithm(String algorithm) {
        _algorithm = algorithm;
    }

    String getAlgorithmType() {
        return _algorithm;
    }
}
