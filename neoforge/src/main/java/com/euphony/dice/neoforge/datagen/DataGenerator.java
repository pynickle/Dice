package com.euphony.dice.neoforge.datagen;

import com.euphony.dice.Dice;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = Dice.MOD_ID)
public class DataGenerator {
    @SubscribeEvent
    public static void generate(GatherDataEvent.Client event) {
        net.minecraft.data.DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        generator.addProvider(true, new ModelGenerator(output));
    }
}
