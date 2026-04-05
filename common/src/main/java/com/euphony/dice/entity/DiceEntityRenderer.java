package com.euphony.dice.entity;

import com.euphony.dice.Dice;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class DiceEntityRenderer extends EntityRenderer<DiceEntity, DiceRenderState> {
	private static final Identifier D6_TEX = Identifier.fromNamespaceAndPath(Dice.MOD_ID, "textures/entity/d6.png");
	private static D6Model d6Model;
	
	private final Minecraft minecraft;
	
	public DiceEntityRenderer(Context ctx) {
		super(ctx);
		this.minecraft = Minecraft.getInstance();
		d6Model = new D6Model(ctx.bakeLayer(D6Model.LAYER_LOCATION));
	}

	@Override
	public @NotNull DiceRenderState createRenderState() {
		return new DiceRenderState();
	}

	@Override
	public void submit(DiceRenderState diceRenderState, PoseStack stack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
		DiceModel model = switch (diceRenderState.diceType) {
			case 6 -> d6Model;
			default -> throw new IllegalArgumentException("Unexpected value: " + diceRenderState.rolled);
		};
		
		boolean flag = !diceRenderState.isInvisible;
		
		RenderType renderType = getRenderType(diceRenderState, model, flag);
		if (renderType != null) {
			model.setupRotation(diceRenderState);
			int color = ((255) << 24) | ((diceRenderState.red & 255) << 16) | ((diceRenderState.green & 255) << 8) | (diceRenderState.blue & 255);
            submitNodeCollector.submitModel(model, diceRenderState, stack, renderType, diceRenderState.lightCoords, OverlayTexture.NO_OVERLAY, color, null, diceRenderState.outlineColor, null);
		}
		
		super.submit(diceRenderState, stack, submitNodeCollector, cameraRenderState);
	}

	@Override
	public void extractRenderState(DiceEntity entity, DiceRenderState entityRenderState, float f) {
		super.extractRenderState(entity, entityRenderState, f);
		entityRenderState.diceType = entity.getDiceType();
		entityRenderState.rolled = entity.getRolled();

		entityRenderState.red = entity.getRed();
		entityRenderState.blue = entity.getBlue();
		entityRenderState.green = entity.getGreen();
	}

	public Identifier getTextureLocation(DiceRenderState dice) {
		return switch (dice.diceType) {
			case 6 -> D6_TEX;
			default -> throw new IllegalArgumentException("Unexpected value: " + dice.diceType);
		};
	}
	
	private RenderType getRenderType(DiceRenderState dice, DiceModel model, boolean flag1) {
		Identifier resourcelocation = getTextureLocation(dice);
		if (flag1) {
			return model.renderType(resourcelocation);
		}
		
		return null;
	}
}
