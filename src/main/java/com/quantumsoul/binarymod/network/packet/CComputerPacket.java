package com.quantumsoul.binarymod.network.packet;

import com.quantumsoul.binarymod.tileentity.ComputerTileEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CComputerPacket
{
    private final BlockPos pos;
    private final boolean load;

    public CComputerPacket(BlockPos pos, boolean load)
    {
        this.pos = pos;
        this.load = load;
    }

    public static void serialize(CComputerPacket packet, PacketBuffer buf)
    {
        buf.writeBlockPos(packet.pos);
        buf.writeBoolean(packet.load);
    }

    public static CComputerPacket deserialize(PacketBuffer buf)
    {
        BlockPos pos = buf.readBlockPos();
        boolean load = buf.readBoolean();

        return new CComputerPacket(pos, load);
    }

    public static void handle(CComputerPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayerEntity player = context.get().getSender();

            if (player.world.isBlockLoaded(packet.pos))
            {
                ComputerTileEntity tileEntity = (ComputerTileEntity) player.world.getTileEntity(packet.pos);
                if (tileEntity != null)
                {
                    if (packet.load)
                        tileEntity.load();
                    else
                        tileEntity.unload();

                    tileEntity.openGui(player);
                }
            }
        });

        context.get().setPacketHandled(true);
    }
}
