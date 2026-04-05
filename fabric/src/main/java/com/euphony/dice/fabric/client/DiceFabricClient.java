package com.euphony.dice.fabric.client;

import com.euphony.dice.DiceClient;
import com.euphony.dice.entity.D6Model;
import com.euphony.dice.entity.DiceEntityRenderer;
import com.euphony.dice.fabric.DiceFabricRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;

public final class DiceFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRenderers.register(DiceFabricRegistry.DICE_ENTITY, DiceEntityRenderer::new);
        ModelLayerRegistry.registerModelLayer(D6Model.LAYER_LOCATION, D6Model::createBodyLayer);
        DiceClient.init();
    }
}
