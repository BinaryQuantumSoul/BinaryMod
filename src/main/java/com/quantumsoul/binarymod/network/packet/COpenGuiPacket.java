package com.quantumsoul.binarymod.network.packet;

import com.quantumsoul.binarymod.tileentity.IExecutableMachine;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class COpenGuiPacket
{
    private final BlockPos pos;

    public COpenGuiPacket(BlockPos pos)
    {
        this.pos = pos;
    }

    public static void serialize(COpenGuiPacket packet, PacketBuffer buf)
    {
        buf.writeBlockPos(packet.pos);
    }

    public static COpenGuiPacket deserialize(PacketBuffer buf)
    {
        return new COpenGuiPacket(buf.readBlockPos());
    }

    public static void handle(COpenGuiPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayerEntity player = Objects.requireNonNull(context.get().getSender());
            if (player.world.isBlockLoaded(packet.pos))
            {
                TileEntity te = player.world.getTileEntity(packet.pos);
                if (te instanceof IExecutableMachine)
                    ((IExecutableMachine) te).execute(player);
            }
        });

        context.get().setPacketHandled(true);
    }
}
