package com.euphony.dice.item;

import com.euphony.dice.entity.DiceEntity;
import com.euphony.dice.registries.DiceRegistry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class DiceItem extends Item {
	private final Color color;
	private final byte diceType;
	
	public DiceItem(Color color, byte diceType) {
		super(new Properties().stacksTo(1).arch$tab(DiceRegistry.DICE_TAB));
		this.color = color;
		this.diceType = diceType;
	}
	
	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		
		if (!world.isClientSide) {
			DiceEntity dice = new DiceEntity(world, player.getEyePosition().subtract(0, 0.1f, 0), color, diceType);
			dice.shootFromRotation(player, player.getXRot(), player.getYHeadRot(), 0, 0.75f, 1);
			world.addFreshEntity(dice);
		}
		
		player.awardStat(Stats.ITEM_USED.get(this));
		world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WITCH_THROW, SoundSource.NEUTRAL, 0.4f,
				0.4f / (player.getRandom().nextFloat() * 0.4f + 0.8f));
		player.getCooldowns().addCooldown(this, 10);

		return InteractionResultHolder.sidedSuccess(itemstack, true); // SNWCreations: We don't have 'canRepair', is it added by Forge?
	}
}
