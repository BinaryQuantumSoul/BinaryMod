package com.quantumsoul.binarymod.item;

import com.quantumsoul.binarymod.entity.BugEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;

public class DebugItem extends Item
{
    public DebugItem(Item.Properties properties)
    {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand handIn)
    {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++)
            player.inventory.getStackInSlot(i).setDamage(200);

        return ActionResult.resultSuccess(player.getHeldItem(handIn));
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
