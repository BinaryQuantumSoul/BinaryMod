package com.quantumsoul.binarymod.recipe;

import com.google.gson.JsonObject;
import com.quantumsoul.binarymod.init.ItemInit;
import com.quantumsoul.binarymod.init.RecipeInit;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

public class DarkWebRecipe implements IRecipe<IInventory>
{
    private final ResourceLocation id;
    private final int price;
    private final ItemStack result;
    private final boolean isSource;

    public DarkWebRecipe(ResourceLocation idIn, int priceIn, ItemStack resultIn, boolean isSourceIn)
    {
        id = idIn;
        price = priceIn;
        result = resultIn;
        isSource = isSourceIn;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn)
    {
        return true;
    }

    @Override
    public boolean canFit(int width, int height)
    {
        return true;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv)
    {
        return result.copy();
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        return result.copy();
    }

    public int getPrice()
    {
        return price;
    }

    public boolean isSource()
    {
        return isSource;
    }

    @Override
    public ResourceLocation getId()
    {
        return id;
    }

    @Override
    public IRecipeType<?> getType()
    {
        return RecipeInit.DARK_WEB;
    }

    @Override
    public IRecipeSerializer<?> getSerializer()
    {
        return RecipeInit.DARK_WEB_SERIALIZER.get();
    }

    @Override
    public ItemStack getIcon()
    {
        return new ItemStack(ItemInit.DARK_NET.get());
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<DarkWebRecipe>
    {
        @Override
        public DarkWebRecipe read(ResourceLocation recipeId, JsonObject json)
        {
            int price = JSONUtils.getInt(json, "price");
            Item item = JSONUtils.getItem(json, "item");
            boolean isSource = JSONUtils.getBoolean(json, "isSource", false);

            ItemStack stack;
            if (isSource)
            {
                stack = new ItemStack(ItemInit.SOURCE.get());
                CompoundNBT nbt = new CompoundNBT();
                nbt.putString("item", item.getRegistryName().toString());
                stack.setTag(nbt);
            }
            else
                stack = new ItemStack(item);

            return new DarkWebRecipe(recipeId, price, stack, isSource);
        }

        @Nullable
        @Override
        public DarkWebRecipe read(ResourceLocation recipeId, PacketBuffer buffer)
        {
            int price = buffer.readVarInt();
            ItemStack result = buffer.readItemStack();
            boolean isSource = buffer.readBoolean();

            return new DarkWebRecipe(recipeId, price, result, isSource);
        }

        @Override
        public void write(PacketBuffer buffer, DarkWebRecipe recipe)
        {
            buffer.writeVarInt(recipe.price);
            buffer.writeItemStack(recipe.result);
            buffer.writeBoolean(recipe.isSource);
        }
    }
}
