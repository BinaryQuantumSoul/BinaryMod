package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.block.loot.UpgradableBlockLoot;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class LootInit
{
    public static final LootFunctionType UPGRADABLE_BLOCK_LOOT_TYPE = Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(BinaryMod.MOD_ID,"upgradable_block_loot"), new LootFunctionType(new UpgradableBlockLoot.Serializer()));
}
