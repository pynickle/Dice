package com.euphony.dice.utils;

import com.euphony.dice.entity.D6Model;
import com.euphony.dice.entity.DiceEntityRenderer;
import com.euphony.dice.registries.DiceRegistry;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;

public class ClientEvents {
	public static void register() {
		EntityRendererRegistry.register(DiceRegistry.DICE_ENTITY, DiceEntityRenderer::new);
		EntityModelLayerRegistry.register(D6Model.LAYER_LOCATION, D6Model::createBodyLayer);
	}
}
