package com.quantumsoul.binarymod.item;

import com.quantumsoul.binarymod.entity.BugEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import org.apache.logging.log4j.LogManager;

public class DebugItem extends Item
{
    public DebugItem(Item.Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand)
    {
        if (target instanceof BugEntity)
        {
            BugEntity bug = (BugEntity) target;
            LogManager.getLogger().debug("ClientSide: {}, Pitch: {}, Yaw: {}, YawHead: {}, Mimic: {}", playerIn.world.isRemote, bug.rotationPitch, bug.rotationYaw, bug.rotationYawHead, bug.getMimic().getBlock().getRegistryName());
            return true;
        }

        return false;
    }
}
