package com.euphony.dice.neoforge.datagen;

import com.euphony.dice.Dice;
import com.euphony.dice.registries.DiceRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class ModelGenerator extends ModelProvider {
    public ModelGenerator(PackOutput packOutput) {
        super(packOutput, Dice.MOD_ID);
    }

    protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
        itemModel(itemModels, DiceRegistry.BLACK_D6.get());
        itemModel(itemModels, DiceRegistry.BLUE_D6.get());
        itemModel(itemModels, DiceRegistry.GREEN_D6.get());
        itemModel(itemModels, DiceRegistry.PURPLE_D6.get());
        itemModel(itemModels, DiceRegistry.RED_D6.get());
        itemModel(itemModels, DiceRegistry.YELLOW_D6.get());

        itemModel(itemModels, DiceRegistry.ORANGE_D6.get());
        itemModel(itemModels, DiceRegistry.LIGHT_BLUE_D6.get());
        itemModel(itemModels, DiceRegistry.LIME_D6.get());
        itemModel(itemModels, DiceRegistry.MAGENTA_D6.get());
        itemModel(itemModels, DiceRegistry.PINK_D6.get());

        itemModel(itemModels, DiceRegistry.GRAY_D6.get());
        itemModel(itemModels, DiceRegistry.LIGHT_GRAY_D6.get());
        itemModel(itemModels, DiceRegistry.CYAN_D6.get());
        itemModel(itemModels, DiceRegistry.BROWN_D6.get());
        itemModel(itemModels, DiceRegistry.WHITE_D6.get());
    }

    public void itemModel(ItemModelGenerators itemModels, Item item) {
        this.itemModel(itemModels, item, ModelTemplates.FLAT_ITEM);
    }

    public void itemModel(ItemModelGenerators itemModels, Item item, ModelTemplate template) {
        Identifier itemId = BuiltInRegistries.ITEM.getKey(item);
        Identifier textureLoc = Identifier.fromNamespaceAndPath(itemId.getNamespace(), "item/" + itemId.getPath());
        TextureMapping textureMapping = new TextureMapping().put(TextureSlot.LAYER0, textureLoc);
        itemModels.itemModelOutput.accept(item, new BlockModelWrapper.Unbaked(template.create(item, textureMapping, itemModels.modelOutput), Collections.emptyList()));
    }

    public void itemModel(ItemModelGenerators itemModels, Item item, String loc) {
        this.itemModel(itemModels, item, ModelTemplates.FLAT_ITEM, loc);
    }

    public void itemModel(ItemModelGenerators itemModels, Item item, ModelTemplate template, String loc) {
        Identifier itemId = BuiltInRegistries.ITEM.getKey(item);
        Identifier textureLoc = Identifier.fromNamespaceAndPath(itemId.getNamespace(), "item/" + loc);
        TextureMapping textureMapping = new TextureMapping().put(TextureSlot.LAYER0, textureLoc);
        itemModels.itemModelOutput.accept(item, new BlockModelWrapper.Unbaked(template.create(item, textureMapping, itemModels.modelOutput), Collections.emptyList()));
    }
}
