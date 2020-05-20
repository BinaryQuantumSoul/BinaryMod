package com.quantumsoul.binarymod.entity;

import com.quantumsoul.binarymod.init.EntityInit;
import com.quantumsoul.binarymod.init.ItemInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OneZeroEntity extends AnimalEntity
{
    private static final Ingredient BREED = Ingredient.fromItems(ItemInit.BIT.get());

    public OneZeroEntity(World world)
    {
        this(null, world);
    }
    public OneZeroEntity(EntityType<? extends OneZeroEntity> type, World world)
    {
        super(type, world);
    }

    //=================================================== AI ===================================================
    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D, OneZeroEntity.class));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, BREED, false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack)
    {
        return BREED.test(stack);
    }

    @Override
    public AgeableEntity createChild(AgeableEntity ageable)
    {
        return this.rand.nextBoolean() ? EntityInit.ONE.get().create(this.world) : EntityInit.ZERO.get().create(this.world);
    }

    @Override
    public boolean canMateWith(AnimalEntity otherAnimal)
    {
        if (otherAnimal != this && otherAnimal instanceof OneZeroEntity)
            return this.isInLove() && otherAnimal.isInLove();

        return false;
    }

    //=================================================== SOUND =================================================== todo
    @Override
    protected SoundEvent getAmbientSound()
    {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_GENERIC_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_GENERIC_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn)
    {}

    @Override
    protected float getSoundVolume()
    {
        return 0.4F;
    }
}
