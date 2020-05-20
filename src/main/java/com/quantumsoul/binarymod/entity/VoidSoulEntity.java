package com.quantumsoul.binarymod.entity;

import com.quantumsoul.binarymod.init.BlockInit;
import com.quantumsoul.binarymod.init.EntityInit;
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
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.4F);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2F);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2F);
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

    //=================================================== SOUND =================================================== todo
}
