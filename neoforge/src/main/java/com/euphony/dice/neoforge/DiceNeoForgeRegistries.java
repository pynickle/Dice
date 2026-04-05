package com.euphony.dice.neoforge;

import com.euphony.dice.Dice;
import com.euphony.dice.entity.DiceEntity;
import com.euphony.dice.item.DiceItem;
import com.euphony.dice.registries.DiceConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public final class DiceNeoForgeRegistries {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Dice.MOD_ID);
    public static final DeferredRegister.Entities ENTITY_TYPES = DeferredRegister.createEntities(Dice.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Dice.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<DiceEntity>> DICE_ENTITY = ENTITY_TYPES.register(DiceConstants.DICE_ENTITY_ID, () -> EntityType.Builder.<DiceEntity>of(DiceEntity::new, MobCategory.MISC)
            .sized(0.3125f, 0.3125f)
            .build(ResourceKey.create(Registries.ENTITY_TYPE, DiceConstants.DICE_ENTITY_IDENTIFIER)));
    public static final List<DeferredItem<Item>> DICE_ITEMS = DiceConstants.DICE_ITEMS.stream()
            .map(DiceNeoForgeRegistries::registerItem)
            .toList();
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_MODE_TAB = CREATIVE_MODE_TABS.register(DiceConstants.DICE_TAB_ID,
            () -> createDiceTab(DICE_ITEMS.getFirst(), DICE_ITEMS));

    private DiceNeoForgeRegistries() {
    }

    private static DeferredItem<Item> registerItem(DiceConstants.DiceItemDefinition itemDefinition) {
        return ITEMS.registerItem(itemDefinition.id(), properties -> new DiceItem(itemDefinition.color(), (byte) itemDefinition.diceType(), DICE_ENTITY::get, properties));
    }

    private static CreativeModeTab createDiceTab(Supplier<? extends Item> iconItem, List<? extends Supplier<? extends Item>> items) {
        return CreativeModeTab.builder()
                .title(Component.translatable("item_group." + Dice.MOD_ID + ".dice"))
                .icon(() -> new ItemStack(iconItem.get()))
                .displayItems((_, output) -> items.forEach(itemSupplier -> output.accept(itemSupplier.get())))
                .build();
    }
}
