package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.tileentity.container.BlockProgContainer;
import com.quantumsoul.binarymod.tileentity.container.ComputerContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerInit
{
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, BinaryMod.MOD_ID);

    public static final RegistryObject<ContainerType<ComputerContainer>> COMPUTER = CONTAINERS.register("computer", () -> IForgeContainerType.create(ComputerContainer::new));
    public static final RegistryObject<ContainerType<BlockProgContainer>> BLOCK_PROGRAMMER = CONTAINERS.register("block_programmer", () -> IForgeContainerType.create(BlockProgContainer::new));
}