package com.quantumsoul.binarymod.network.packet;

import com.quantumsoul.binarymod.item.BitcoinItem;
import com.quantumsoul.binarymod.recipe.DarkWebRecipe;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import static com.quantumsoul.binarymod.util.BitcoinUtils.*;

public class BtcBuyPacket
{
    private final ResourceLocation id;

    public BtcBuyPacket(DarkWebRecipe recipe)
    {
        this(recipe.getId());
    }

    public BtcBuyPacket(ResourceLocation recipeId)
    {
        id = recipeId;
    }

    public static void serialize(BtcBuyPacket packet, PacketBuffer buf)
    {
        buf.writeResourceLocation(packet.id);
    }

    public static BtcBuyPacket deserialize(PacketBuffer buf)
    {
        return new BtcBuyPacket(buf.readResourceLocation());
    }

    public static void handle(BtcBuyPacket packet, Supplier<NetworkEvent.Context> context)
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
