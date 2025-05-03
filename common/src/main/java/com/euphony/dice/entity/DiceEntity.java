package com.euphony.dice.entity;

import com.euphony.dice.registries.DiceRegistry;
import dev.architectury.extensions.network.EntitySpawnExtension;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class DiceEntity extends Entity implements EntitySpawnExtension {
	private int r, g, b;
	private byte type, rolled;
	private int life;
	
	public DiceEntity(EntityType<? extends Entity> type, Level level) {
		super(type, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
	}

	@Override
	public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity serverEntity) {
		return NetworkManager.createAddEntityPacket(this, serverEntity);
	}

	public DiceEntity(Level world, Vec3 pos, Color color, byte type) {
		super(DiceRegistry.DICE_ENTITY.get(), world);
		setPos(pos);
		this.type = type;
		rolled = (byte) (1 + random.nextInt(type));
		r = color.getRed();
		g = color.getGreen();
		b = color.getBlue();
	}
	
	@Override
	public void tick() {
		super.tick();
		life++;

		if (life >= 20 * 10) {
			level().addParticle(ParticleTypes.POOF, getX(), getY(), getZ(), 0, 0.15625f, 0);
			remove(RemovalReason.KILLED);
		}
		
		if (!isAlive()) {
			return;
		}
		
		xo = getX();
		yo = getY();
		zo = getZ();
		
		Vec3 vec3 = getDeltaMovement();
		double e = vec3.y + (vec3.y < 0.06f ? 5e-4f : 0);
		if (isInWater()) {
			setDeltaMovement(vec3.x * 0.99f, e, vec3.z * 0.99f);
		} else if (isInLava()) {
			setDeltaMovement(vec3.x * 0.95f, e, vec3.z * 0.95f);
		} else if (!isNoGravity()) {
			setDeltaMovement(getDeltaMovement().add(0, -0.04, 0));
		}
		
		Level level = level();
		if (level.isClientSide) {
			noPhysics = false;
		} else {
			noPhysics = !level().noCollision(this, getBoundingBox().deflate(1e-7));
			if (noPhysics) {
				moveTowardsClosestSpace(getX(), (getBoundingBox().minY + getBoundingBox().maxY) / 2, getZ());
			}
		}
		
		if (!onGround() || getDeltaMovement().horizontalDistanceSqr() > 1e-5f || (tickCount + getId()) % 4 == 0) {
			move(MoverType.SELF, getDeltaMovement());
			
			float f1 = 0.98f;
			if (onGround()) {
				BlockPos groundPos = getBlockPosBelowThatAffectsMyMovement();
				f1 = level().getBlockState(groundPos).getBlock().getFriction() * 0.98f;
			}
			
			setDeltaMovement(getDeltaMovement().multiply(f1, 0.98, f1));
			if (onGround()) {
				Vec3 vec31 = getDeltaMovement();
				if (vec31.y < 0) {
					setDeltaMovement(vec31.multiply(1, -0.5, 1));
				}
			}
		}
		
		hasImpulse |= updateInWaterStateAndDoFluidPushing();
		
		if (!level.isClientSide) {
			double d0 = getDeltaMovement().subtract(vec3).lengthSqr();
			if (d0 > 0.01) {
				hasImpulse = true;
			}
		}
	}
	
	@Override
	protected @NotNull MovementEmission getMovementEmission() {
		return MovementEmission.NONE;
	}
	
	@Override
	public boolean canBeHitByProjectile() {
		return false;
	}
	
	@Override
	public boolean isAttackable() {
		return false;
	}
	
	public void shoot(double p_37266_, double p_37267_, double p_37268_, float p_37269_, float p_37270_) {
		Vec3 vec3 = (new Vec3(p_37266_, p_37267_, p_37268_)).normalize()
				.add(random.triangle(0, 0.0172275 * p_37270_), random.triangle(0, 0.0172275 * p_37270_), random.triangle(0, 0.0172275 * p_37270_)).scale(p_37269_);
		setDeltaMovement(vec3);
		setYRot((float) (Mth.atan2(vec3.x, vec3.z) * (180f / (float) Math.PI)));
		setXRot((float) (Mth.atan2(vec3.y, vec3.horizontalDistance()) * (180f / (float) Math.PI)));
		yRotO = getYRot();
		xRotO = getXRot();
	}
	
	public void shootFromRotation(Entity entity, float p_37253_, float p_37254_, float p_37255_, float p_37256_, float p_37257_) {
		shoot(-Mth.sin(p_37254_ * ((float) Math.PI / 180f)) * Mth.cos(p_37253_ * ((float) Math.PI / 180f)), -Mth.sin((p_37253_ + p_37255_) * ((float) Math.PI / 180F)),
				Mth.cos(p_37254_ * ((float) Math.PI / 180f)) * Mth.cos(p_37253_ * ((float) Math.PI / 180f)), p_37256_, p_37257_);
		Vec3 vec3 = entity.getDeltaMovement();
		setDeltaMovement(getDeltaMovement().add(vec3.x, entity.onGround() ? 0 : vec3.y, vec3.z));
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tag) {
		tag.putInt("R", r);
		tag.putInt("G", g);
		tag.putInt("B", b);
		tag.putByte("Type", type);
		tag.putByte("Rolled", rolled);
	}
	
	@Override
	protected void readAdditionalSaveData(CompoundTag tag) {
		Color defaultColor = Color.WHITE;

		r = tag.contains("R", 99) ? tag.getInt("R") : defaultColor.getRed();
		g = tag.contains("G", 99) ? tag.getInt("G") : defaultColor.getGreen();
		b = tag.contains("B", 99) ? tag.getInt("B") : defaultColor.getBlue();
		type = tag.contains("Type", 99) ? tag.getByte("Type") : 6;
		rolled = tag.contains("Rolled", 99) ? tag.getByte("Rolled") : (byte) (1 + random.nextInt(this.type));
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double dist) {
		return dist > 1;
	}
	
	public int getRed() {
		return r;
	}
	
	public int getGreen() {
		return g;
	}
	
	public int getBlue() {
		return b;
	}
	
	public byte getDiceType() {
		return type;
	}
	
	public byte getRolled() {
		return rolled;
	}

	@Override
	public void saveAdditionalSpawnData(FriendlyByteBuf buffer) {
		buffer.writeInt(r);
		buffer.writeInt(g);
		buffer.writeInt(b);
		buffer.writeByte(type);
		buffer.writeByte(rolled);
	}

	@Override
	public void loadAdditionalSpawnData(FriendlyByteBuf additionalData) {
		r = additionalData.readInt();
		g = additionalData.readInt();
		b = additionalData.readInt();
		type = additionalData.readByte();
		rolled = additionalData.readByte();
	}
}
