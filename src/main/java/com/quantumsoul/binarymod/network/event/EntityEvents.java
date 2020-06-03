package com.quantumsoul.binarymod.network.event;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.entity.OneZeroEntity;
import com.quantumsoul.binarymod.init.EntityInit;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BinaryMod.MOD_ID)
public class EntityEvents
{
    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinWorldEvent event)
    {
        Entity e = event.getEntity();
        if (e instanceof OneZeroEntity && !((OneZeroEntity) e).randomized && !((OneZeroEntity) e).isChild())
        {
            World world = event.getWorld();

            OneZeroEntity entity = world.rand.nextBoolean() ? EntityInit.ONE.get().create(world) : EntityInit.ZERO.get().create(world);
            entity.setPositionAndRotation(e.getPosX(), e.getPosY(), e.getPosZ(), e.rotationYaw, e.rotationPitch);
            entity.randomized = true;

            world.addEntity(entity);
            event.setCanceled(true);
        }
    }
}
