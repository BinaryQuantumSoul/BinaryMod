package com.quantumsoul.binarymod.network.event;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.init.DimensionInit;
import com.quantumsoul.binarymod.init.ItemInit;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BinaryMod.MOD_ID)
public class PlayerEvents
{
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        if (event.getPlayer().dimension == DimensionInit.DIM_BINARY_TYPE)
            event.getPlayer().addItemStackToInventory(new ItemStack(ItemInit.BLUE_PILL.get()));
    }
}
