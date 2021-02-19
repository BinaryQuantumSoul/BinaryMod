package com.quantumsoul.binarymod.entity;

import com.quantumsoul.binarymod.init.EntityInit;
import com.quantumsoul.binarymod.init.SoundInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import static com.quantumsoul.binarymod.util.WorldUtils.onBinDimLivingFall;

public class WormEntity extends MonsterEntity
{
    private static final DataParameter<Boolean> DUPLICATE = EntityDataManager.createKey(WormEntity.class, DataSerializers.BOOLEAN);

    public WormEntity(World worldIn)
    {
        this(EntityInit.WORM.get(), worldIn);
    }
    public WormEntity(EntityType<? extends MonsterEntity> type, World worldIn)
    {
        super(type, worldIn);
    }

    //=================================================== AI ===================================================
    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.9D, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this, PlayerEntity.class));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier)
    {
        return onBinDimLivingFall(world, this.getPosition(), () -> super.onLivingFall(distance, damageMultiplier));
    }

    @Override
    public void remove(boolean keepData)
    {
        if (!world.isRemote && this.getDuplicate() && this.getHealth() <= 0.0F && !this.removed)
        {
            double x = rand.nextBoolean() ? 0.5D : -0.5D;
            double z = rand.nextBoolean() ? 0.5D : -0.5D;
            WormEntity entity = (WormEntity) this.getType().create(world);

            if (this.hasCustomName())
                entity.setCustomName(this.getCustomName());
            if (this.isNoDespawnRequired())
                entity.enablePersistence();
            entity.setInvulnerable(this.isInvulnerable());

            entity.setSecond();
            entity.setLocationAndAngles(getPosX() + x, getPosY() + 0.5D, getPosZ() + z, rand.nextFloat() * 360.0F, 0.0F);
            world.addEntity(entity);
        }

        super.remove(keepData);
    }

    @Override
    protected boolean canDropLoot()
    {
        return !this.getDuplicate();
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return this.canDropLoot() ? this.getType().getLootTable() : LootTables.EMPTY;
    }

    //=================================================== DATA ===================================================
    @Override
    protected void registerData()
    {
        super.registerData();
        this.getDataManager().register(DUPLICATE, rand.nextInt(3) == 0);
    }

    public boolean getDuplicate()
    {
        return this.getDataManager().get(DUPLICATE);
    }

    public void setSecond()
    {
        this.getDataManager().set(DUPLICATE, false);
    }

    //=================================================== SOUND ===================================================
    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundInit.WORM_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundInit.WORM_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundInit.WORM_DEATH.get();
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.2F;
    }
}
