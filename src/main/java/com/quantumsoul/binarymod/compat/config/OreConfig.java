package com.quantumsoul.binarymod.compat.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class OreConfig
{
    public static ForgeConfigSpec.IntValue binaryOreCountRange;
    public static ForgeConfigSpec.IntValue binaryOreWhiteRabbit;

    public static void init(ForgeConfigSpec.Builder common, ForgeConfigSpec.Builder client, ForgeConfigSpec.Builder server)
    {
        binaryOreCountRange = server
                .comment("Binary ore count range")
                .defineInRange("ores.binary_ore_count_range", 2, 0, 20);

        binaryOreWhiteRabbit = server
                .comment("Chance of summoning white rabbit when harvesting binary ore")
                .defineInRange("ores.binary_ore_white_rabbit", 10, 0, 100);
    }
}
