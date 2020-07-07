package com.quantumsoul.binarymod.tileentity;

import com.quantumsoul.binarymod.init.TileEntityInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;

import javax.annotation.Nullable;
import java.util.List;

import static com.quantumsoul.binarymod.util.MachineUtils.L_REPAIRER;

public class RepairerTileEntity extends FactoryTileEntity
{
    public RepairerTileEntity()
    {
        super(TileEntityInit.REPAIRER.get(), L_REPAIRER);
    }

    @Override
    void doAction(PlayerEntity player, int level)
    {
        int amount = (int) L_REPAIRER.get(level);

        for(int i = 0; i < player.inventory.getSizeInventory(); i++)
        {
            if(amount <= 0)
                break;

            ItemStack stack = player.inventory.getStackInSlot(i);
            Item item = stack.getItem();
            if(stack.isDamaged()
                && (item instanceof TieredItem
                || item instanceof ShootableItem
                || item instanceof ArmorItem
                || item instanceof ElytraItem
                || item instanceof ShieldItem
                || item instanceof ShearsItem
                || item instanceof FlintAndSteelItem
                || item instanceof FishingRodItem
                || item instanceof TridentItem))
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
