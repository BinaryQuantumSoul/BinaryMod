package com.quantumsoul.binarymod.entity;

import com.quantumsoul.binarymod.init.EntityInit;
import com.quantumsoul.binarymod.init.ItemInit;
import com.quantumsoul.binarymod.init.SoundInit;
import com.quantumsoul.binarymod.util.WorldUtils;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.List;

import static com.quantumsoul.binarymod.util.WorldUtils.onBinDimLivingFall;

public class OneZeroEntity extends AnimalEntity
{
    private static final Ingredient BREED = Ingredient.fromItems(ItemInit.BIT.get());

    public boolean randomized = false;

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
        this.goalSelector.addGoal(4, new OneZeroParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
    }

    @Override
    public boolean isBreedingItem(ItemStack stack)
    {
        return BREED.test(stack);
    }

    @Override
    public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity ageable)
    {
        return this.rand.nextBoolean() ? EntityInit.ONE.get().create(world) : EntityInit.ZERO.get().create(world);
    }

    @Override
    public boolean canMateWith(AnimalEntity otherAnimal)
    {
        if (otherAnimal != this && otherAnimal instanceof OneZeroEntity)
            return this.isInLove() && otherAnimal.isInLove();

        return false;
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier)
    {
        return onBinDimLivingFall(world, this.getPosition(), () -> super.onLivingFall(distance, damageMultiplier));
    }

    @Override
    public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn)
    {
        return WorldUtils.canBinDimAnimalSpawn(null, (IWorld) worldIn, null, pos, null) ? 10.0F : worldIn.getBrightness(pos) - 0.5F;
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld world, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag)
    {
        if(!randomized && !isChild())
        {
            OneZeroEntity entity = rand.nextBoolean() ? EntityInit.ONE.get().create(world.getWorld()) : EntityInit.ZERO.get().create(world.getWorld());
            entity.setPositionAndRotation(getPosX() + rand.nextDouble() * 1.75D - 0.875D, getPosY(), getPosZ() + rand.nextDouble() * 1.75D - 0.875D, rotationYaw, rotationPitch);
            entity.randomized = true;

            world.addEntity(entity);
        }

        return super.onInitialSpawn(world, difficultyIn, reason, spawnDataIn, dataTag);
    }

    //=================================================== SOUND ===================================================
    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundInit.ONE_ZERO_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundInit.ONE_ZERO_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundInit.ONE_ZERO_DEATH.get();
    }

    @Override
    protected float getSoundVolume()
    {
        return this instanceof ZeroEntity ? 0.8F : 1F;
    }

    //=================================================== GOALS ===================================================
    protected static class OneZeroParentGoal extends FollowParentGoal
    {
        private static final Field childAnimal = ObfuscationReflectionHelper.findField(FollowParentGoal.class, "field_75348_a");
        private static final Field parentAnimal = ObfuscationReflectionHelper.findField(FollowParentGoal.class, "field_75346_b");

        public OneZeroParentGoal(AnimalEntity animal, double speed)
        {
            super(animal, speed);
        }

        @Override
        public boolean shouldExecute()
        {
            if (!getChild().isChild())
                return false;

            List<AnimalEntity> list = getChild().world.getEntitiesWithinAABB(OneZeroEntity.class, getChild().getBoundingBox().grow(8.0D, 4.0D, 8.0D));
            AnimalEntity animalentity = null;
            double d0 = Double.MAX_VALUE;

            for (AnimalEntity tmp : list)
            {
                if (!tmp.isChild())
                {
                    double d1 = getChild().getDistanceSq(tmp);
                    if (d1 <= d0)
                    {
                        d0 = d1;
                        animalentity = tmp;
                    }
                }
            }

            if (animalentity != null && d0 >= 9.0D)
            {
                setParent(animalentity);
                return true;
            }

            return false;
        }

        private AnimalEntity getChild()
        {
            try
            {
                return (AnimalEntity) childAnimal.get(this);
            } catch (IllegalAccessException e)
            {
                e.printStackTrace();
                return null;
            }
        }

        private void setParent(AnimalEntity parent)
        {
            try
            {
                parentAnimal.set(this, parent);
            } catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
    }
}
