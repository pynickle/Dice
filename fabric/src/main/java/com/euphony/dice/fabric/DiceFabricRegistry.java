package com.euphony.dice.fabric;

import com.euphony.dice.Dice;
import com.euphony.dice.entity.DiceEntity;
import com.euphony.dice.item.DiceItem;
import com.euphony.dice.registries.DiceConstants;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class DiceFabricRegistry {
	public static final EntityType<DiceEntity> DICE_ENTITY = EntityType.Builder.<DiceEntity>of(DiceEntity::new, MobCategory.MISC)
			.sized(0.3125f, 0.3125f)
			.build(ResourceKey.create(Registries.ENTITY_TYPE, DiceConstants.DICE_ENTITY_IDENTIFIER));
	public static final ResourceKey<CreativeModeTab> DICE_TAB_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), DiceConstants.DICE_TAB_IDENTIFIER);
	public static final CreativeModeTab DICE_TAB = FabricCreativeModeTab.builder()
			.title(Component.translatable("item_group." + Dice.MOD_ID + ".dice"))
			.icon(() -> new ItemStack(getRegisteredItem(DiceConstants.WHITE_D6.id())))
			.displayItems((_, output) -> DiceConstants.DICE_ITEMS.forEach((itemDefinition) -> output.accept(getRegisteredItem(itemDefinition.id()))))
			.build();

	private DiceFabricRegistry() {
	}

	public static void register() {
		for (DiceConstants.DiceItemDefinition itemDefinition : DiceConstants.DICE_ITEMS) {
			registerItem(itemDefinition);
		}

		Registry.register(BuiltInRegistries.ENTITY_TYPE, DiceConstants.DICE_ENTITY_IDENTIFIER, DICE_ENTITY);
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, DICE_TAB_KEY, DICE_TAB);
	}

	private static Item registerItem(DiceConstants.DiceItemDefinition itemDefinition) {
		Item item = new DiceItem(itemDefinition.color(), (byte) itemDefinition.diceType(), () -> DICE_ENTITY, createItemProperties(itemDefinition.id()));
		return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(Dice.MOD_ID, itemDefinition.id()), item);
	}

	private static Item getRegisteredItem(String id) {
		return BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath(Dice.MOD_ID, id));
	}

	private static Item.Properties createItemProperties(String id) {
		return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Dice.MOD_ID, id)));
	}
}
