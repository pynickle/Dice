package com.euphony.dice.neoforge;

import com.euphony.dice.Dice;
import com.euphony.dice.entity.D6Model;
import com.euphony.dice.entity.DiceEntityRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Dice.MOD_ID, value = Dist.CLIENT)
public final class DiceNeoForgeClient {
    private DiceNeoForgeClient() {
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(DiceNeoForgeRegistries.DICE_ENTITY.get(), DiceEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(D6Model.LAYER_LOCATION, D6Model::createBodyLayer);
    }
}
