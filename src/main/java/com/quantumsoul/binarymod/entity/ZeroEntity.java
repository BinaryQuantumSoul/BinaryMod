package com.quantumsoul.binarymod.entity;

import com.quantumsoul.binarymod.init.EntityInit;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.world.World;

public class ZeroEntity extends OneZeroEntity
{
    public ZeroEntity(World world)
    {
        this(EntityInit.ZERO.get(), world);
    }
    public ZeroEntity(EntityType<? extends OneZeroEntity> type, World world)
    {
        super(type, world);
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn)
    {
        return this.isChild() ? sizeIn.height * 0.95F : 0.6F;
    }
}
