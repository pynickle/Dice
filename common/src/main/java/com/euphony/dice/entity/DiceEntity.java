package com.euphony.dice.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class DiceEntity extends Entity {
	private static final EntityDataAccessor<Integer> RED = SynchedEntityData.defineId(DiceEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> GREEN = SynchedEntityData.defineId(DiceEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BLUE = SynchedEntityData.defineId(DiceEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Byte> TYPE = SynchedEntityData.defineId(DiceEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Byte> ROLLED = SynchedEntityData.defineId(DiceEntity.class, EntityDataSerializers.BYTE);

	private int life;
	
	public DiceEntity(EntityType<? extends Entity> type, Level level) {
		super(type, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(RED, Color.WHITE.getRed());
		builder.define(GREEN, Color.WHITE.getGreen());
		builder.define(BLUE, Color.WHITE.getBlue());
		builder.define(TYPE, (byte) 6);
		builder.define(ROLLED, (byte) 1);
	}

	@Override
	public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity serverEntity) {
		return new ClientboundAddEntityPacket(this, serverEntity);
	}

	public DiceEntity(EntityType<? extends DiceEntity> entityType, Level world, Vec3 pos, Color color, byte type) {
		super(entityType, world);
		setPos(pos);
		entityData.set(TYPE, type);
		entityData.set(ROLLED, (byte) (1 + random.nextInt(type)));
		entityData.set(RED, color.getRed());
		entityData.set(GREEN, color.getGreen());
		entityData.set(BLUE, color.getBlue());
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
		if (level.isClientSide()) {
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
		
		// hasImpulse |= updateInWaterStateAndDoFluidPushing();
		
		if (!level.isClientSide()) {
			double d0 = getDeltaMovement().subtract(vec3).lengthSqr();
			if (d0 > 0.01) {
				// hasImpulse = true;
			}
		}
	}
	
	@Override
	protected @NotNull MovementEmission getMovementEmission() {
		return MovementEmission.NONE;
	}

	@Override
	public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float f) {
		return false;
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
	protected void addAdditionalSaveData(ValueOutput valueOutput) {
		valueOutput.putInt("R", getRed());
		valueOutput.putInt("G", getGreen());
		valueOutput.putInt("B", getBlue());
		valueOutput.putByte("Type", getDiceType());
		valueOutput.putByte("Rolled", getRolled());
	}
	
	@Override
	protected void readAdditionalSaveData(ValueInput valueInput) {
		Color defaultColor = Color.WHITE;

		entityData.set(RED, valueInput.getInt("R").orElse(defaultColor.getRed()));
		entityData.set(GREEN, valueInput.getInt("G").orElse(defaultColor.getGreen()));
		entityData.set(BLUE, valueInput.getInt("B").orElse(defaultColor.getBlue()));
		entityData.set(TYPE, valueInput.getByteOr("Type", (byte) 6));
		entityData.set(ROLLED, valueInput.getByteOr("Rolled", (byte) (1 + random.nextInt(getDiceType()))));
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double dist) {
		return dist > 1;
	}
	
	public int getRed() {
		return entityData.get(RED);
	}
	
	public int getGreen() {
		return entityData.get(GREEN);
	}
	
	public int getBlue() {
		return entityData.get(BLUE);
	}
	
	public byte getDiceType() {
		return entityData.get(TYPE);
	}
	
	public byte getRolled() {
		return entityData.get(ROLLED);
	}
}
