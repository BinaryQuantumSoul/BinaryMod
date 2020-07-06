package com.quantumsoul.binarymod.entity;

import com.quantumsoul.binarymod.init.EntityInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.network.NetworkHooks;

import java.lang.reflect.Field;

public class BulletEntity extends AbstractArrowEntity
{
    private static final Field ticksInGround = ObfuscationReflectionHelper.findField(AbstractArrowEntity.class, "field_70252_j");

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
    protected void func_225516_i_()
    {
        super.func_225516_i_();

        try
        {
            if ((int) ticksInGround.get(this) >= 200 && !removed)
                remove();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
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
