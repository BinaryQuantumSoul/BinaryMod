package com.quantumsoul.binarymod.entity;

import com.quantumsoul.binarymod.init.EntityInit;
import com.quantumsoul.binarymod.init.SoundInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.quantumsoul.binarymod.util.WorldUtils.onBinDimLivingFall;

public class VirusEntity extends SlimeEntity
{
    public VirusEntity(World worldIn)
    {
        this(EntityInit.VIRUS.get(), worldIn);
    }
    public VirusEntity(EntityType<? extends SlimeEntity> type, World worldIn)
    {
        super(type, worldIn);
    }

    //=================================================== AI ===================================================
    @Override
    public boolean onLivingFall(float distance, float damageMultiplier)
    {
        return onBinDimLivingFall(world, this.getPosition(), () -> super.onLivingFall(distance, damageMultiplier));
    }

    @Override
    protected float func_225512_er_() //damages
    {
        return super.func_225512_er_() + 0.5F;
    }

    @Override
    protected boolean canDamagePlayer()
    {
        return this.isServerWorld();
    }

    @Override
    protected int getJumpDelay()
    {
        return this.rand.nextInt(20) + 20;
    }

    @Override
    protected boolean spawnCustomParticles()
    {
        return true;
    }

    //=================================================== SOUND ===================================================
    @Override
    protected SoundEvent getJumpSound()
    {
        return SoundInit.VIRUS_JUMP.get();
    }

    @Override
    protected SoundEvent getSquishSound()
    {
        return null;
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_GENERIC_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_GENERIC_DEATH;
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.1F * (float)this.getSlimeSize();
    }

    @Override
    protected void dealDamage(LivingEntity entityIn)
    {
        if (this.isAlive())
        {
            int i = this.getSlimeSize();
            if (this.getDistanceSq(entityIn) < 0.6D * (double)i * 0.6D * (double)i && this.canEntityBeSeen(entityIn) && entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), this.func_225512_er_()))
                this.applyEnchantments(this, entityIn);
        }
    }
}
