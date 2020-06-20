package com.quantumsoul.binarymod.entity;

import com.quantumsoul.binarymod.init.BlockInit;
import com.quantumsoul.binarymod.init.EntityInit;
import com.quantumsoul.binarymod.init.SoundInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class VoidSoulEntity extends MonsterEntity implements IFlyingAnimal
{
    public VoidSoulEntity(World worldIn)
    {
        this(EntityInit.VOID_SOUL.get(), worldIn);
    }

    public VoidSoulEntity(EntityType<? extends MonsterEntity> type, World worldIn)
    {
        super(type, worldIn);
        this.moveController = new FlyingMovementController(this, 10, false);
    }

    //=================================================== AI ===================================================
    @Override
    public void registerGoals()
    {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 0.8D));

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this, PlayerEntity.class));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public void registerAttributes()
    {
        super.registerAttributes();
        this.getAttributes().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.8F);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4F);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3F);
    }

    @Override
    public PathNavigator createNavigator(World worldIn)
    {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, worldIn);
        flyingpathnavigator.setCanOpenDoors(true);
        flyingpathnavigator.setCanSwim(true);
        flyingpathnavigator.setCanEnterDoors(true);
        return flyingpathnavigator;
    }

    public static boolean canVoidSoulSpawn(EntityType<VoidSoulEntity> entity, IWorld world, SpawnReason spawnReason, BlockPos blockPos, Random random)
    {
        return world.getDifficulty() != Difficulty.PEACEFUL && (spawnReason == SpawnReason.SPAWNER || world.getBlockState(blockPos.down()) == BlockInit.VOID_BLOCK.get().getDefaultState());
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier)
    {
        return false;
    }

    @Override
    public boolean canDropLoot()
    {
        return false;
    }

    //=================================================== SOUND ===================================================
    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundInit.VOID_SOUL_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundInit.VOID_SOUL_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundInit.VOID_SOUL_DEATH.get();
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.7F;
    }
}
