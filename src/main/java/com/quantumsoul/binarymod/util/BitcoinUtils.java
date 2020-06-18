package com.quantumsoul.binarymod.util;

import com.quantumsoul.binarymod.init.ItemInit;
import com.quantumsoul.binarymod.item.BitcoinItem;
import com.quantumsoul.binarymod.recipe.DarkWebRecipe;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BitcoinUtils
{
    private static final char[] prefixes = new char[]{' ', 'k', 'M', 'G', 'T', 'P'};
    public static String getBitcoinString(double value)
    {
        for (int i = prefixes.length - 1; i >= 0; i--)
        {
            double remainder = value / Math.pow(8, i);
            if ((int) remainder > 0)
                return String.format("%.2f %cB", remainder, prefixes[i]);
        }

        return String.format("%.2f  B", value);
    }

    private static final Item[] btc_items = {ItemInit.BITCOIN.get(), ItemInit.K_BTC.get(), ItemInit.M_BTC.get(), ItemInit.G_BTC.get(), ItemInit.T_BTC.get(), ItemInit.P_BTC.get()};
    public static final List<ItemStack> BTC_STACKS = Arrays.stream(BitcoinUtils.btc_items).map(ItemStack::new).collect(Collectors.toList());

    public static ItemStack getBitcoinStack(double value)
    {
        for (int i = btc_items.length - 1; i >= 0; i--)
        {
            double remainder = value / Math.pow(8, i);
            if ((int) remainder > 0)
                return BTC_STACKS.get(i);
        }

        return new ItemStack(ItemInit.BITCOIN.get());
    }

    public static List<ItemStack> getBitcoinStacks(double value)
    {
        List<ItemStack> stacks = NonNullList.create();

        double[] octal = new double[btc_items.length];
        for (int i = octal.length - 1; i >= 0; i--)
        {
            double pre = i < octal.length - 1 ? octal[i + 1] : value;
            double d = Math.pow(8, i);
            octal[i] = (int) (pre % d);

            int count = (int) (pre / d);
            if (count != 0)
                stacks.add(new ItemStack(btc_items[i], count));
        }

        return stacks;
    }

    public static double evaluateInventory(PlayerInventory inv)
    {
        int count = 0;
        for (int i = 0; i < inv.getSizeInventory(); i++)
        {
            ItemStack stack = inv.getStackInSlot(i);
            Item item = stack.getItem();
            if (item instanceof BitcoinItem)
                count += ((BitcoinItem) item).getValue() * stack.getCount();
        }

        return count;
    }

    public static boolean buyRecipe(DarkWebRecipe recipe, PlayerInventory inv)
    {
        double allMoney = evaluateInventory(inv);
        int toPay = recipe.getPrice();
        if (allMoney >= toPay)
        {
            for(int i = 0; i < inv.getSizeInventory(); i++)
            {
                if(toPay <= 0)
                    break;

                ItemStack stack = inv.getStackInSlot(i);
                if(stack.getItem() instanceof BitcoinItem)
                {
                    int money = stack.getCount() * ((BitcoinItem) stack.getItem()).getValue();
                    toPay -= money;
                    inv.setInventorySlotContents(i, ItemStack.EMPTY);

                    if(toPay < 0)
                        for(ItemStack rem : getBitcoinStacks(-toPay))
                            inv.addItemStackToInventory(rem);
                }
            }

            inv.addItemStackToInventory(recipe.getCraftingResult(inv));
            return true;
        }

        return false;
    }
}
