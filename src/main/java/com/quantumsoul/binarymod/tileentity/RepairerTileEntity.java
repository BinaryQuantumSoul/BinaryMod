package com.quantumsoul.binarymod.tileentity;

import com.quantumsoul.binarymod.init.TileEntityInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;

import javax.annotation.Nullable;
import java.util.List;

public class RepairerTileEntity extends FactoryTileEntity
{
    public RepairerTileEntity()
    {
        super(TileEntityInit.REPAIRER.get(), 4);
    }

    @Override
    void doAction(PlayerEntity player, int level)
    {
        int amount = (int) (64 * Math.pow(4, level));

        for(int i = 0; i < player.inventory.getSizeInventory(); i++)
        {
            if(amount <= 0)
                break;

            ItemStack stack = player.inventory.getStackInSlot(i);
            if((stack.getItem() instanceof ToolItem || stack.getItem() instanceof ArmorItem) && stack.isDamaged())
            {
                int damage = stack.getDamage();
                int repair = Math.min(damage, amount);
                stack.setDamage(damage - repair);
                amount -= repair;
            }
        }
    }

    @Nullable
    @Override
    List<ItemStack> getDrops(int level)
    {
        return null;
    }
}
