package com.quantumsoul.binarymod.network.event;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.entity.FlyerEntity;
import com.quantumsoul.binarymod.init.GenerationInit;
import com.quantumsoul.binarymod.init.ItemInit;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

import static com.quantumsoul.binarymod.util.WorldUtils.isOnGround;

@Mod.EventBusSubscriber(modid = BinaryMod.MOD_ID)
public class PlayerEvents
{
    private static final Field vehicleFloatingTickCount = ObfuscationReflectionHelper.findField(ServerPlayNetHandler.class, "field_184346_E");

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        if (event.getPlayer().getEntityWorld().getDimensionKey() == GenerationInit.BINARY_DIMENSION)
            event.getPlayer().addItemStackToInventory(new ItemStack(ItemInit.BLUE_PILL.get()));
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event)
    {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player != null)
        {
            GameSettings set = Minecraft.getInstance().gameSettings;
            Entity riding = player.getRidingEntity();
            if (riding instanceof FlyerEntity && set.keyBindSneak.isKeyDown() && !isOnGround(riding))
                set.keyBindSneak.setPressed(false);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.END && event.side.isServer() && event.player.getRidingEntity() instanceof FlyerEntity)
        {
            try
            {
                vehicleFloatingTickCount.set(((ServerPlayerEntity) event.player).connection, 0);
            } catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
    }
}
