package com.quantumsoul.binarymod.entity;

import com.quantumsoul.binarymod.init.EntityInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.world.World;

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
    public void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2F);
    }

    @Override
    protected void setSlimeSize(int size, boolean resetHealth)
    {
        super.setSlimeSize(size, resetHealth);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(size * size * 3F);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4F + 0.1F * (float) size);
    }

    @Override
    protected float func_225512_er_() //damages
    {
        return super.func_225512_er_() + 3.0F;
    }

    @Override
    protected boolean canDamagePlayer()
    {
        return this.isServerWorld();
    }

    @Override
    protected int getJumpDelay()
    {
        return this.rand.nextInt(20) + 5;
    }

    @Override
    protected boolean spawnCustomParticles()
    {
        return true;
    }

    //=================================================== SOUND =================================================== todo
}
