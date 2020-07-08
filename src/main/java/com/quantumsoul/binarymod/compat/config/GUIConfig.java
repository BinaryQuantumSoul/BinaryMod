package com.quantumsoul.binarymod.compat.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class GUIConfig
{
    public enum HUDPosition
    {
        TOP_LEFT(true, true),
        TOP_RIGHT(true, false),
        BOTTOM_LEFT(false, true),
        BOTTOM_RIGHT(false, false);

        public final boolean top, left;
        HUDPosition(boolean top, boolean left)
        {
            this.top = top;
            this.left = left;
        }
    }

    public static ForgeConfigSpec.BooleanValue showBitcoinHUD;
    public static ForgeConfigSpec.EnumValue<HUDPosition> btcPosition;
    public static ForgeConfigSpec.IntValue btcX;
    public static ForgeConfigSpec.IntValue btcY;


    public static void init(ForgeConfigSpec.Builder common, ForgeConfigSpec.Builder client, ForgeConfigSpec.Builder server)
    {
        showBitcoinHUD = client
                .comment("Show bitcoin HUD when having bitcoins in inventory")
                .define("gui.btc_show_hud", true);

        btcPosition = client
                .comment("Bitcoin HUD position")
                .defineEnum("gui.btc_hud_pos", HUDPosition.BOTTOM_RIGHT);

        btcX = client
                .comment("Bitcoin HUD X shift")
                .defineInRange("gui.btc_hud_x", 40, 0, 500);

        btcY = client
                .comment("Bitcoin HUD Y shift")
                .defineInRange("gui.btc_hud_y", 15, 0, 500);
    }
}
