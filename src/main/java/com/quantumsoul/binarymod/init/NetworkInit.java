package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.network.packet.CBtcBuyPacket;
import com.quantumsoul.binarymod.network.packet.SBtcResetValuePacket;
import com.quantumsoul.binarymod.network.packet.CComputerPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkInit
{
    private static final String PROTOCOL_VERSION = BinaryMod.MOD_ID + ":protocol";

    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(BinaryMod.MOD_ID, "channel"))
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .simpleChannel();

    public static void initPackets()
    {
        CHANNEL.messageBuilder(CComputerPacket.class, 0).encoder(CComputerPacket::serialize).decoder(CComputerPacket::deserialize).consumer(CComputerPacket::handle).add();
        CHANNEL.messageBuilder(SBtcResetValuePacket.class, 1).encoder(SBtcResetValuePacket::serialize).decoder(SBtcResetValuePacket::deserialize).consumer(SBtcResetValuePacket::handle).add();
        CHANNEL.messageBuilder(CBtcBuyPacket.class, 2).encoder(CBtcBuyPacket::serialize).decoder(CBtcBuyPacket::deserialize).consumer(CBtcBuyPacket::handle).add();
    }
}
