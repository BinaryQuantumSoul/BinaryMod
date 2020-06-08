package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.client.gui.screen.BlockProgScreen;
import com.quantumsoul.binarymod.client.gui.screen.ComputerScreen;
import net.minecraft.client.gui.ScreenManager;

public class ScreenInit
{
    public static void initScreens()
    {
        ScreenManager.registerFactory(ContainerInit.COMPUTER.get(), ComputerScreen::new);
        ScreenManager.registerFactory(ContainerInit.BLOCK_PROGRAMMER.get(), BlockProgScreen::new);
    }
}
