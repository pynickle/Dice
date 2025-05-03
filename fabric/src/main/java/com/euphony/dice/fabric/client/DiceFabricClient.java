package com.euphony.dice.fabric.client;

import com.euphony.dice.DiceClient;
import net.fabricmc.api.ClientModInitializer;

public final class DiceFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        DiceClient.init();
    }
}
