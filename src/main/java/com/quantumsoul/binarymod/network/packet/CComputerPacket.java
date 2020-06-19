package com.quantumsoul.binarymod.network.packet;

import com.quantumsoul.binarymod.init.NetworkInit;
import com.quantumsoul.binarymod.tileentity.ComputerTileEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

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
        return new CComputerPacket(buf.readBlockPos(), buf.readBoolean());
    }

    public static void handle(CComputerPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayerEntity player = context.get().getSender();

            if (player.world.isBlockLoaded(packet.pos))
            {
                TileEntity te = player.world.getTileEntity(packet.pos);
                if (te instanceof ComputerTileEntity)
                {
                    ComputerTileEntity tileEntity = (ComputerTileEntity) te;

                    player.closeContainer();
                    tileEntity.load(packet.load);
                    //tileEntity.openGui(player);

                    NetworkInit.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SComputerPacket(packet.pos, packet.load));
                }
            }
        });

        context.get().setPacketHandled(true);
    }
}
