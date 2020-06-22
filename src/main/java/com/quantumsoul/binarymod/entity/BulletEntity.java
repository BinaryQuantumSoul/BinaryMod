package com.quantumsoul.binarymod.entity;

import com.quantumsoul.binarymod.init.EntityInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BulletEntity extends AbstractArrowEntity
{
    public BulletEntity(World world)
    {
        this(EntityInit.BULLET.get(), world);
    }
    public BulletEntity(EntityType<? extends AbstractArrowEntity> entityTypeIn, World worldIn)
    {
        super(entityTypeIn, worldIn);
    }

    public BulletEntity(World world, float x, float y, float z)
    {
        this(world);
        setPosition(x, y, z);
    }

    @Override
    protected ItemStack getArrowStack()
    {
        return ItemStack.EMPTY;
    }

    @Override
    public IPacket<?> createSpawnPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
