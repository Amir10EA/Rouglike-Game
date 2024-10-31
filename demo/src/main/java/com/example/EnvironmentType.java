package com.example;

import java.util.Random;

public enum EnvironmentType {
    STORMY,
    SANDY,
    LAVA,
    FOREST,
    ICY,
    NORMAL,
    UNKNOWN; //for testing default case

    private static final EnvironmentType[] VALUES = values();
    private static final Random RANDOM = new Random();

    public static EnvironmentType getRandomEnvironment() {
        return VALUES[RANDOM.nextInt(VALUES.length)];
    }
}
