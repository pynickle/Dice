package com.euphony.dice.neoforge.datagen;

import com.euphony.dice.Dice;
import com.euphony.dice.registries.DiceRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.numeric.UseCycle;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

import static net.minecraft.client.data.models.BlockModelGenerators.*;

public class ModelGenerator extends ModelProvider {
    public ModelGenerator(PackOutput packOutput) {
        super(packOutput, Dice.MOD_ID);
    }

    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
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
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
        ResourceLocation textureLoc = ResourceLocation.fromNamespaceAndPath(itemId.getNamespace(), "item/" + itemId.getPath());
        TextureMapping textureMapping = new TextureMapping().put(TextureSlot.LAYER0, textureLoc);
        itemModels.itemModelOutput.accept(item, new BlockModelWrapper.Unbaked(template.create(item, textureMapping, itemModels.modelOutput), Collections.emptyList()));
    }

    public void itemModel(ItemModelGenerators itemModels, Item item, String loc) {
        this.itemModel(itemModels, item, ModelTemplates.FLAT_ITEM, loc);
    }

    public void itemModel(ItemModelGenerators itemModels, Item item, ModelTemplate template, String loc) {
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
        ResourceLocation textureLoc = ResourceLocation.fromNamespaceAndPath(itemId.getNamespace(), "item/" + loc);
        TextureMapping textureMapping = new TextureMapping().put(TextureSlot.LAYER0, textureLoc);
        itemModels.itemModelOutput.accept(item, new BlockModelWrapper.Unbaked(template.create(item, textureMapping, itemModels.modelOutput), Collections.emptyList()));
    }
}
