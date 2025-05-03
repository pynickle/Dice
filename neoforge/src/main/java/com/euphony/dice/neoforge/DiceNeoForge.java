package com.euphony.dice.neoforge;

import com.euphony.dice.Dice;
import net.neoforged.fml.common.Mod;

@Mod(Dice.MOD_ID)
public final class DiceNeoForge {
    public DiceNeoForge() {
        // Run our common setup.
        Dice.init();
    }
}
