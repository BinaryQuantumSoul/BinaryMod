package com.quantumsoul.binarymod.network.packet;

import com.quantumsoul.binarymod.network.event.PlayerEvents;
import com.quantumsoul.binarymod.recipe.DarkWebRecipe;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class CBtcBuyPacket
{
    private final ResourceLocation id;

    public CBtcBuyPacket(DarkWebRecipe recipe)
    {
        this(recipe.getId());
    }

    public CBtcBuyPacket(ResourceLocation recipeId)
    {
        id = recipeId;
    }

    public static void serialize(CBtcBuyPacket packet, PacketBuffer buf)
    {
        buf.writeResourceLocation(packet.id);
    }

    public static CBtcBuyPacket deserialize(PacketBuffer buf)
    {
        return new CBtcBuyPacket(buf.readResourceLocation());
    }

    public static void handle(CBtcBuyPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            PlayerInventory inv = Objects.requireNonNull(context.get().getSender()).inventory;
            Optional<?> opt = inv.player.world.getRecipeManager().getRecipe(packet.id);
            if(opt.isPresent() && opt.get() instanceof DarkWebRecipe)
            {
                DarkWebRecipe recipe = (DarkWebRecipe) opt.get();
                PlayerEvents.darkWebRecipes.add(recipe);
                PlayerEvents.playerInventories.add(inv);
            }
        });

        context.get().setPacketHandled(true);
    }
}