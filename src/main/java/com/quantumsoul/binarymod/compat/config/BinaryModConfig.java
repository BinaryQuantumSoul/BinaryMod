package com.quantumsoul.binarymod.compat.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class BinaryModConfig
{
    private static final ForgeConfigSpec.Builder COMMON = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder SERVER = new ForgeConfigSpec.Builder();

    public static void initConfigs(ModLoadingContext modLoadingContext)
    {
        ChatConfig.init(COMMON, CLIENT, SERVER);
        GUIConfig.init(COMMON, CLIENT, SERVER);
        OreConfig.init(COMMON, CLIENT, SERVER);

        modLoadingContext.registerConfig(ModConfig.Type.COMMON, COMMON.build());
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, CLIENT.build());
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, SERVER.build());
    }
}
