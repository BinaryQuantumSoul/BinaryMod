package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.network.packet.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkInit
{
    private static final String PROTOCOL_VERSION = BinaryMod.MOD_ID;

    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(BinaryMod.MOD_ID, "channel"))
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .simpleChannel();

    public static void initPackets()
    {
        int i  = 0;

        CHANNEL.messageBuilder(CComputerPacket.class, i++).encoder(CComputerPacket::serialize).decoder(CComputerPacket::deserialize).consumer(CComputerPacket::handle).add();
        CHANNEL.messageBuilder(SComputerPacket.class, i++).encoder(SComputerPacket::serialize).decoder(SComputerPacket::deserialize).consumer(SComputerPacket::handle).add();
        CHANNEL.messageBuilder(COpenGuiPacket.class, i++).encoder(COpenGuiPacket::serialize).decoder(COpenGuiPacket::deserialize).consumer(COpenGuiPacket::handle).add();

        CHANNEL.messageBuilder(SBtcResetValuePacket.class, i++).encoder(SBtcResetValuePacket::serialize).decoder(SBtcResetValuePacket::deserialize).consumer(SBtcResetValuePacket::handle).add();
        CHANNEL.messageBuilder(CBtcBuyPacket.class, i++).encoder(CBtcBuyPacket::serialize).decoder(CBtcBuyPacket::deserialize).consumer(CBtcBuyPacket::handle).add();

        CHANNEL.messageBuilder(CBlockProgPacket.class, i++).encoder(CBlockProgPacket::serialize).decoder(CBlockProgPacket::deserialize).consumer(CBlockProgPacket::handle).add();
    }
}
