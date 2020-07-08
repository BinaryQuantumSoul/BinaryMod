package com.quantumsoul.binarymod.compat.hwyla;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.block.BackdoorBlock;
import com.quantumsoul.binarymod.block.MachineBlock;
import com.quantumsoul.binarymod.entity.FlyerEntity;
import com.quantumsoul.binarymod.tileentity.MachineTileEntity;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaPlugin;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

@WailaPlugin(BinaryMod.MOD_ID)
public class BinaryModHWYLAPlugin implements IWailaPlugin
{
    @Override
    public void register(IRegistrar registrar)
    {
        registrar.addConfig(new ResourceLocation(BinaryMod.MOD_ID, "machine_level"), true);
        registrar.addConfig(new ResourceLocation(BinaryMod.MOD_ID, "machine_level_info"), true);
        registrar.addConfig(new ResourceLocation(BinaryMod.MOD_ID, "machine_on_off_state"), true);
        registrar.registerComponentProvider(new MachineProvider(), TooltipPosition.BODY, MachineTileEntity.class);

        registrar.registerComponentProvider(new FlyerProvider(), TooltipPosition.HEAD, FlyerEntity.class);
        registrar.registerEntityStackProvider(new FlyerProvider(), FlyerEntity.class);

        registrar.registerStackProvider(new BackdoorProvider(), BackdoorBlock.class);
    }
}
