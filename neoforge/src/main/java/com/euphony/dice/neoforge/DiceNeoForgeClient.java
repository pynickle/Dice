package com.euphony.dice.neoforge;

import com.euphony.dice.Dice;
import com.euphony.dice.DiceClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = Dice.MOD_ID, dist = Dist.CLIENT)
public class DiceNeoForgeClient {
    public DiceNeoForgeClient() {
        DiceClient.init();
    }
}
