package com.euphony.dice;

import com.euphony.dice.registries.DiceRegistry;

public final class Dice {
    public static final String MOD_ID = "dice";

    public static void init() {
        DiceRegistry.register();
    }
}
