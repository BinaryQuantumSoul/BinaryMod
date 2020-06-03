package com.quantumsoul.binarymod.network.event;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.init.DimensionInit;
import com.quantumsoul.binarymod.init.ItemInit;
import com.quantumsoul.binarymod.recipe.DarkWebRecipe;
import com.quantumsoul.binarymod.util.BitcoinUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = BinaryMod.MOD_ID)
public class PlayerEvents
{
    public static List<DarkWebRecipe> darkWebRecipes = new ArrayList<>();
    public static List<PlayerInventory> playerInventories = new ArrayList<>();
    
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        if (event.getPlayer().dimension == DimensionInit.DIM_BINARY_TYPE)
            event.getPlayer().addItemStackToInventory(new ItemStack(ItemInit.BLUE_PILL.get()));
    }
    
    @SubscribeEvent
    public static void serverTick(TickEvent.ServerTickEvent event)
    {
        List<DarkWebRecipe> darkWebRecipesIterated = new ArrayList<>();
        List<PlayerInventory> playerInventoriesIterated = new ArrayList<>();

        darkWebRecipes.forEach(darkWebRecipe ->
        {
            PlayerInventory inventory = playerInventories.get(darkWebRecipes.indexOf(darkWebRecipe));
            BitcoinUtils.buyRecipe(darkWebRecipe, inventory);
            darkWebRecipesIterated.add(darkWebRecipe);
            playerInventoriesIterated.add(inventory);
        });

        darkWebRecipes.removeAll(darkWebRecipesIterated);
        playerInventories.removeAll(playerInventoriesIterated);
        darkWebRecipesIterated.clear();
        playerInventoriesIterated.clear();
    }
}
