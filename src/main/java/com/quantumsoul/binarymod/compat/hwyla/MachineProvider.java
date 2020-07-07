package com.quantumsoul.binarymod.compat.hwyla;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.tileentity.IOnOffMachine;
import com.quantumsoul.binarymod.tileentity.IUpgradableMachine;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public class MachineProvider implements IComponentProvider
{
    @Override
    public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config)
    {
        TileEntity te = accessor.getTileEntity();

        if (te instanceof IUpgradableMachine)
        {
            IUpgradableMachine upgradableMachine = (IUpgradableMachine) te;

            if (config.get(new ResourceLocation(BinaryMod.MOD_ID, "machine_level")))
                tooltip.add(upgradableMachine.getLevelMessage());


            ITextComponent info = upgradableMachine.getInfoMessage();
            if (info != null && config.get(new ResourceLocation(BinaryMod.MOD_ID, "machine_level_info")))
                tooltip.add(info);
        }

        if (te instanceof IOnOffMachine && config.get(new ResourceLocation(BinaryMod.MOD_ID, "machine_on_off_state")))
            tooltip.add(((IOnOffMachine) te).getStateMessage());
    }
}
