package com.quantumsoul.binarymod.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.quantumsoul.binarymod.item.SDCardItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.lang.reflect.Method;
import java.util.Map;

//THIS CLASS IS A MESS
public class SDRecipe extends ShapedRecipe
{
    protected final NonNullList<Ingredient> recipeItems;
    protected static Method rDeserializeKey;
    protected static Method rShrink;
    protected static Method rPatternFromJson;
    protected static Method rDeserializeIngredients;

    static
    {
        try
        {
            rDeserializeKey = ShapedRecipe.class.getDeclaredMethod("deserializeKey", JsonObject.class);
            rDeserializeKey.setAccessible(true);

            rShrink = ShapedRecipe.class.getDeclaredMethod("shrink", String[].class);
            rShrink.setAccessible(true);

            rPatternFromJson = ShapedRecipe.class.getDeclaredMethod("patternFromJson", JsonArray.class);
            rPatternFromJson.setAccessible(true);

            rDeserializeIngredients = ShapedRecipe.class.getDeclaredMethod("deserializeIngredients", String[].class, Map.class, int.class, int.class);
            rDeserializeIngredients.setAccessible(true);
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }

    public SDRecipe(ResourceLocation idIn, String groupIn, int recipeWidthIn, int recipeHeightIn, NonNullList<Ingredient> recipeItemsIn, ItemStack recipeOutputIn)
    {
        super(idIn, groupIn, recipeWidthIn, recipeHeightIn, recipeItemsIn, recipeOutputIn);

        recipeItems = recipeItemsIn;
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn)
    {
        //copy
        for (int i = 0; i <= inv.getWidth() - getWidth(); ++i)
        {
            for (int j = 0; j <= inv.getHeight() - getHeight(); ++j)
            {
                if (checkSDMatch(inv, i, j, true)) //change
                    return true;

                if (checkSDMatch(inv, i, j, false)) //change
                    return true;
            }
        }

        return false;
    }

    protected boolean checkSDMatch(CraftingInventory inv, int x, int y, boolean placement)
    {
        //copy
        for (int i = 0; i < inv.getWidth(); ++i)
        {
            for (int j = 0; j < inv.getHeight(); ++j)
            {
                int k = i - x;
                int l = j - y;

                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < getWidth() && l < getHeight())
                {
                    if (placement)
                        ingredient = this.recipeItems.get(getWidth() - k - 1 + l * getWidth());
                    else
                        ingredient = this.recipeItems.get(k + l * getWidth());
                }

                ItemStack stack = inv.getStackInSlot(i + j * inv.getWidth());

                if (!ingredient.test(stack))
                    return false;

                if (ingredient.getMatchingStacks()[0].getItem() instanceof SDCardItem && stack.getDamage() != 100) //add
                    return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv)
    {
        ItemStack stack = super.getCraftingResult(inv);
        if (stack.getItem() instanceof SDCardItem)
            stack.setDamage(100);

        return stack;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<SDRecipe>
    {
        @Override
        public SDRecipe read(ResourceLocation recipeId, JsonObject json)
        {
            //copy
            try
            {
                String s = JSONUtils.getString(json, "group", "");
                Map<String, Ingredient> map = (Map<String, Ingredient>) rDeserializeKey.invoke(this, JSONUtils.getJsonObject(json, "key"));
                String[] astring = (String[]) rShrink.invoke(this, rPatternFromJson.invoke(this, JSONUtils.getJsonArray(json, "pattern")));
                int i = astring[0].length();
                int j = astring.length;
                NonNullList<Ingredient> nonnulllist = (NonNullList<Ingredient>) rDeserializeIngredients.invoke(this, astring, map, i, j);
                ItemStack itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
                return new SDRecipe(recipeId, s, i, j, nonnulllist, itemstack); //change
            } catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public SDRecipe read(ResourceLocation recipeId, PacketBuffer buffer)
        {
            //copy
            int i = buffer.readVarInt();
            int j = buffer.readVarInt();
            String s = buffer.readString(32767);
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);

            for (int k = 0; k < nonnulllist.size(); ++k)
            {
                nonnulllist.set(k, Ingredient.read(buffer));
            }

            ItemStack itemstack = buffer.readItemStack();
            return new SDRecipe(recipeId, s, i, j, nonnulllist, itemstack); //change
        }

        @Override
        public void write(PacketBuffer buffer, SDRecipe recipe)
        {
            //copy
            buffer.writeVarInt(recipe.getWidth());
            buffer.writeVarInt(recipe.getHeight());
            buffer.writeString(recipe.getGroup());

            for (Ingredient ingredient : recipe.recipeItems)
                ingredient.write(buffer);

            buffer.writeItemStack(recipe.getRecipeOutput());
        }
    }
}