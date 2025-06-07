package com.euphony.dice.entity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;

public abstract class DiceModel extends EntityModel<DiceRenderState> {
	protected DiceModel(ModelPart modelPart) {
		super(modelPart);
	}

	public abstract void setupRotation(DiceRenderState dice);
	protected abstract void setRotationAngle(float x, float y, float z);
}
