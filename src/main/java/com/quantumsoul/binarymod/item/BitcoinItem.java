package com.quantumsoul.binarymod.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BitcoinItem extends Item
{
    private final Bitcoin btc;

    public BitcoinItem(Item.Properties properties, Bitcoin bitcoin)
    {
        super(properties);
        btc = bitcoin;
    }

    public int getValue()
    {
        return btc.n;
    }

    public enum Bitcoin
    {
        UNIT(1),
        KILO(8),
        MEGA(64),
        GIGA(512),
        TERA(4096),
        PETA(32768);

        private final int n;
        Bitcoin(int value)
        {
            n = value;
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        int val = getValue();
        if (val != 1)
            tooltip.add(new StringTextComponent(TextFormatting.GOLD + I18n.format("tooltip.binarymod.bitcoin", val)));
    }
}
