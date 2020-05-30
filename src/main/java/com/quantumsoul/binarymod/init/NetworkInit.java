package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.network.BtcResetValuePacket;
import com.quantumsoul.binarymod.network.ComputerPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkInit
{
    private static final String PROTOCOL_VERSION = BinaryMod.MOD_ID + "protocol";

    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(BinaryMod.MOD_ID, "channel"))
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .simpleChannel();

    public static void initPackets()
    {
        CHANNEL.messageBuilder(ComputerPacket.class, 0).encoder(ComputerPacket::serialize).decoder(ComputerPacket::deserialize).consumer(ComputerPacket::handle).add();
        CHANNEL.messageBuilder(BtcResetValuePacket.class, 1).encoder(BtcResetValuePacket::serialize).decoder(BtcResetValuePacket::deserialize).consumer(BtcResetValuePacket::handle).add();
    }
}
