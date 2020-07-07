package com.quantumsoul.binarymod.compat.hwyla;

import com.quantumsoul.binarymod.init.ItemInit;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class FlyerProvider implements IEntityComponentProvider
{
    private final ItemStack displayStack = new ItemStack(ItemInit.FLYER.get());

    @Override
    public void appendHead(List<ITextComponent> tooltip, IEntityAccessor accessor, IPluginConfig config)
    {
        tooltip.add(new TranslationTextComponent("entity.binarymod.flyer"));
    }

    @Override
    public ItemStack getDisplayItem(IEntityAccessor accessor, IPluginConfig config)
    {
        return displayStack;
    }
}
