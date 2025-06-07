package com.euphony.dice.registries;

import com.euphony.dice.Dice;
import com.euphony.dice.entity.DiceEntity;
import com.euphony.dice.item.DiceItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public class DiceRegistry {
	public static final Color WHITE = Color.WHITE;
	public static final Color ORANGE = new Color(230, 158, 52);
	public static final Color MAGENTA = new Color(219, 122, 213);
	public static final Color LIGHT_BLUE = new Color(143, 185, 244);
	public static final Color YELLOW = new Color(255, 218, 57);
	public static final Color LIME = new Color(129, 206, 27);
	public static final Color PINK = new Color(246, 137, 172);
	public static final Color GRAY = new Color(112, 112, 112);
	public static final Color LIGHT_GRAY = new Color(162, 162, 162);
	public static final Color CYAN = new Color(81, 211, 185);
	public static final Color PURPLE = new Color(176, 100, 216);
	public static final Color BLUE = new Color(74, 124, 215);
	public static final Color BROWN = new Color(151, 103, 70);
	public static final Color GREEN = new Color(86, 150, 49);
	public static final Color RED = new Color(210, 68, 63);
	public static final Color BLACK = new Color(82, 82, 82);

	private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Dice.MOD_ID, Registries.CREATIVE_MODE_TAB);
	private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Dice.MOD_ID, Registries.ENTITY_TYPE);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Dice.MOD_ID, Registries.ITEM);

	public static void register() {
		// CREATIVE_TABS.register();
		ENTITIES.register();
		ITEMS.register();
		CREATIVE_TABS.register();
	}

	public static final RegistrySupplier<EntityType<DiceEntity>> DICE_ENTITY = ENTITIES.register("dice_entity",
			() -> EntityType.Builder.<DiceEntity>of(DiceEntity::new, MobCategory.MISC).sized(0.3125f, 0.3125f)
					.build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Dice.MOD_ID, "dice_entity"))));

	public static final RegistrySupplier<Item> WHITE_D6 = registerItem("white_d6", WHITE, 6);
	public static final RegistrySupplier<Item> ORANGE_D6 = registerItem("orange_d6", ORANGE, 6);
	public static final RegistrySupplier<Item> MAGENTA_D6 = registerItem("magenta_d6", MAGENTA, 6);
	public static final RegistrySupplier<Item> LIGHT_BLUE_D6 = registerItem("light_blue_d6", LIGHT_BLUE, 6);
	public static final RegistrySupplier<Item> YELLOW_D6 = registerItem("yellow_d6", YELLOW, 6);
	public static final RegistrySupplier<Item> LIME_D6 = registerItem("lime_d6", LIME, 6);
	public static final RegistrySupplier<Item> PINK_D6 = registerItem("pink_d6", PINK, 6);
	public static final RegistrySupplier<Item> GRAY_D6 = registerItem("gray_d6", GRAY, 6);
	public static final RegistrySupplier<Item> LIGHT_GRAY_D6 = registerItem("light_gray_d6", LIGHT_GRAY, 6);
	public static final RegistrySupplier<Item> CYAN_D6 = registerItem("cyan_d6", CYAN, 6);
	public static final RegistrySupplier<Item> PURPLE_D6 = registerItem("purple_d6", PURPLE, 6);
	public static final RegistrySupplier<Item> BLUE_D6 = registerItem("blue_d6", BLUE, 6);
	public static final RegistrySupplier<Item> BROWN_D6 = registerItem("brown_d6", BROWN, 6);
	public static final RegistrySupplier<Item> GREEN_D6 = registerItem("green_d6", GREEN, 6);
	public static final RegistrySupplier<Item> RED_D6 = registerItem("red_d6", RED, 6);
	public static final RegistrySupplier<Item> BLACK_D6 = registerItem("black_d6", BLACK, 6);

	public static RegistrySupplier<Item> registerItem(String id, Color color, int diceType) {
		return ITEMS.register(id, () -> new DiceItem(color, (byte) diceType, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Dice.MOD_ID, id)))));
	}

	public static final RegistrySupplier<CreativeModeTab> DICE_TAB = CREATIVE_TABS.register("dice",
			() -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
					.title(Component.translatable("item_group." + Dice.MOD_ID + ".dice"))
					.icon(() -> new ItemStack(WHITE_D6.get()))
					.displayItems((p, o) -> {})
					.build()
	);
}
