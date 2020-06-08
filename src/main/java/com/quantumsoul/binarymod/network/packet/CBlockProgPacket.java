package com.quantumsoul.binarymod.network.packet;

import com.quantumsoul.binarymod.tileentity.BlockProgTileEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CBlockProgPacket
{
    private final BlockPos pos;

    public CBlockProgPacket(BlockPos position)
    {
        pos = position;
    }

    public static void serialize(CBlockProgPacket packet, PacketBuffer buf)
    {
        buf.writeBlockPos(packet.pos);
    }

    public static CBlockProgPacket deserialize(PacketBuffer buf)
    {
        return new CBlockProgPacket(buf.readBlockPos());
    }

    public static void handle(CBlockProgPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayerEntity player = context.get().getSender();

            if (player.world.isBlockLoaded(packet.pos))
            {
                TileEntity tileEntity = player.world.getTileEntity(packet.pos);
                if(tileEntity instanceof BlockProgTileEntity)
                    ((BlockProgTileEntity) tileEntity).launch();
            }
        });

        context.get().setPacketHandled(true);
    }
}
