package com.euphony.dice.entity;

import com.euphony.dice.Dice;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class DiceEntityRenderer extends EntityRenderer<DiceEntity, DiceRenderState> {
	private static final ResourceLocation D6_TEX = ResourceLocation.fromNamespaceAndPath(Dice.MOD_ID, "textures/entity/d6.png");
	private static D6Model d6Model;
	
	private final Minecraft minecraft;
	
	public DiceEntityRenderer(Context ctx) {
		super(ctx);
		this.minecraft = Minecraft.getInstance();
		d6Model = new D6Model(ctx.bakeLayer(D6Model.LAYER_LOCATION));
	}

	@Override
	public DiceRenderState createRenderState() {
		return new DiceRenderState();
	}
	@Override
	public void render(DiceRenderState diceRenderState, PoseStack stack, MultiBufferSource buffer, int packedLight) {
		DiceModel model = switch (diceRenderState.diceType) {
			case 6 -> d6Model;
			default -> throw new IllegalArgumentException("Unexpected value: " + diceRenderState.rolled);
		};
		
		boolean flag = !diceRenderState.isInvisible;
		
		RenderType rendertype = getRenderType(diceRenderState, model, flag);
		if (rendertype != null) {
			model.setupRotation(diceRenderState);
			int color = ((255) << 24) | ((diceRenderState.red & 255) << 16) | ((diceRenderState.green & 255) << 8) | (diceRenderState.blue & 255);
			model.renderToBuffer(stack, buffer.getBuffer(rendertype), packedLight, OverlayTexture.NO_OVERLAY, color);
		}
		
		super.render(diceRenderState, stack, buffer, packedLight);
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

	public ResourceLocation getTextureLocation(DiceRenderState dice) {
		return switch (dice.diceType) {
			case 6 -> D6_TEX;
			default -> throw new IllegalArgumentException("Unexpected value: " + dice.diceType);
		};
	}
	
	private RenderType getRenderType(DiceRenderState dice, DiceModel model, boolean flag1) {
		ResourceLocation resourcelocation = getTextureLocation(dice);
		if (flag1) {
			return model.renderType(resourcelocation);
		}
		
		return null;
	}
}
