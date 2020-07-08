package com.quantumsoul.binarymod.compat.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ChatConfig
{
    public static ForgeConfigSpec.BooleanValue sendMachineUpgradeMessage;
    public static ForgeConfigSpec.BooleanValue sendShooterPlayerMessage;

    public static void init(ForgeConfigSpec.Builder common, ForgeConfigSpec.Builder client, ForgeConfigSpec.Builder server)
    {
        sendMachineUpgradeMessage = client
                .comment("Send chat message when upgrading machine")
                .define("chat_messages.send_machine_upgrade_message", true);

        sendShooterPlayerMessage = client
                .comment("Send chat message when adding/removing player to shooter whitelist")
                .define("chat_messages.send_shooter_player_message", true);
    }
}
