package com.quantumsoul.binarymod.entity;

import com.quantumsoul.binarymod.init.EntityInit;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.world.World;

public class OneEntity extends OneZeroEntity
{
    public OneEntity(World world)
    {
        this(EntityInit.ONE.get(), world);
    }
    public OneEntity(EntityType<? extends OneZeroEntity> type, World world)
    {
        super(type, world);
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn)
    {
        return this.isChild() ? sizeIn.height * 0.95F : 0.8F;
    }
}
