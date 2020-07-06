package com.quantumsoul.binarymod.recipe;

import com.google.gson.JsonObject;
import com.quantumsoul.binarymod.item.SDCardItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.common.crafting.NBTIngredient;

import javax.annotation.Nullable;

public class SdIngredient extends NBTIngredient
{
    protected SdIngredient(ItemStack stack)
    {
        super(stack);
    }

    @Override
    public boolean test(@Nullable ItemStack stack)
    {
        return stack.getItem() == getMatchingStacks()[0].getItem() && (!(getMatchingStacks()[0].getItem() instanceof SDCardItem) || stack.getDamage() == 100);
    }

    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer()
    {
        return Serializer.INSTANCE_;
    }

    public static class Serializer extends NBTIngredient.Serializer
    {
        public static final Serializer INSTANCE_ = new Serializer();

        @Override
        public SdIngredient parse(JsonObject json)
        {
            return new SdIngredient(CraftingHelper.getItemStack(json, false));
        }

        @Override
        public SdIngredient parse(PacketBuffer buffer)
        {
            return new SdIngredient(buffer.readItemStack());
        }
    }
}
