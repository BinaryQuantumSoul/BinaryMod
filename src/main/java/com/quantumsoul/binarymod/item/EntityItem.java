package com.quantumsoul.binarymod.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class EntityItem extends Item
{
    private static final Predicate<Entity> ENTITY_PREDICATE = EntityPredicates.NOT_SPECTATING.and(Entity::canBeCollidedWith);
    private final Supplier<EntityType<?>> type;

    public EntityItem(Properties properties, Supplier<EntityType<?>> entity)
    {
        super(properties);
        this.type = entity;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY);
        if (raytraceresult.getType() == RayTraceResult.Type.MISS)
        {
            return ActionResult.resultPass(itemstack);
        } else
        {
            Vector3d vec3d = playerIn.getLook(1.0F);
            List<Entity> list = worldIn.getEntitiesInAABBexcluding(playerIn, playerIn.getBoundingBox().expand(vec3d.scale(5.0D)).grow(1.0D), ENTITY_PREDICATE);
            if (!list.isEmpty())
            {
                Vector3d vec3d1 = playerIn.getEyePosition(1.0F);

                for (Entity entity : list)
                {
                    AxisAlignedBB axisalignedbb = entity.getBoundingBox().grow(entity.getCollisionBorderSize());
                    if (axisalignedbb.contains(vec3d1))
                        return ActionResult.resultPass(itemstack);
                }
            }

            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK)
            {
                Entity entity = type.get().create(worldIn);
                entity.setPosition(raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
                entity.rotationYaw = playerIn.rotationYaw;
                if (!worldIn.hasNoCollisions(entity, entity.getBoundingBox().grow(-0.1D)))
                    return ActionResult.resultFail(itemstack);
                else
                {
                    if (!worldIn.isRemote)
                    {
                        worldIn.addEntity(entity);
                        if (!playerIn.abilities.isCreativeMode)
                            itemstack.shrink(1);
                    }

                    playerIn.addStat(Stats.ITEM_USED.get(this));
                    return ActionResult.resultSuccess(itemstack);
                }
            } else
                return ActionResult.resultPass(itemstack);
        }
    }
}
