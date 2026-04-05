package com.euphony.dice.neoforge;

import com.euphony.dice.Dice;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

@Mod(Dice.MOD_ID)
@EventBusSubscriber(modid = Dice.MOD_ID)
public final class DiceNeoForge {
    public DiceNeoForge(IEventBus modBus) {
        DiceNeoForgeRegistries.ITEMS.register(modBus);
        DiceNeoForgeRegistries.ENTITY_TYPES.register(modBus);
        DiceNeoForgeRegistries.CREATIVE_MODE_TABS.register(modBus);

        // Run our common setup.
        Dice.init();
    }
}
