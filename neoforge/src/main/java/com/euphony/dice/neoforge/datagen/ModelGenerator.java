package com.euphony.dice.neoforge.datagen;

import com.euphony.dice.Dice;
import com.euphony.dice.registries.DiceConstants;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class ModelGenerator extends ModelProvider {
    public ModelGenerator(PackOutput packOutput) {
        super(packOutput, Dice.MOD_ID);
    }

    protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
		for (DiceConstants.DiceItemDefinition itemDefinition : DiceConstants.DICE_ITEMS) {
			itemModel(itemModels, createDatagenItem(itemDefinition.id()));
		}
    }

    public void itemModel(ItemModelGenerators itemModels, Item item) {
        this.itemModel(itemModels, item, ModelTemplates.FLAT_ITEM);
    }

    public void itemModel(ItemModelGenerators itemModels, Item item, ModelTemplate template) {
        itemModels.generateFlatItem(item, template);
    }

	private Item createDatagenItem(String id) {
		return new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Dice.MOD_ID, id))));
	}
}
