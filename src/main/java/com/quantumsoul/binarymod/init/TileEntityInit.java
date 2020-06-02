package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.tileentity.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit
{
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, BinaryMod.MOD_ID);

    public static final RegistryObject<TileEntityType<ComputerTileEntity>> COMPUTER = TILE_ENTITIES.register("computer", () -> TileEntityType.Builder.create(ComputerTileEntity::new, BlockInit.COMPUTER.get()).build(null));
    public static final RegistryObject<TileEntityType<FeederTileEntity>> FEEDER = TILE_ENTITIES.register("feeder", () -> TileEntityType.Builder.create(FeederTileEntity::new, BlockInit.FEEDER.get()).build(null));
    public static final RegistryObject<TileEntityType<HealerTileEntity>> HEALER = TILE_ENTITIES.register("healer", () -> TileEntityType.Builder.create(HealerTileEntity::new, BlockInit.HEALER.get()).build(null));
    public static final RegistryObject<TileEntityType<RepairerTileEntity>> REPAIRER = TILE_ENTITIES.register("repairer", () -> TileEntityType.Builder.create(RepairerTileEntity::new, BlockInit.REPAIRER.get()).build(null));
    public static final RegistryObject<TileEntityType<BitcoinTileEntity>> BITCOIN_MINER = TILE_ENTITIES.register("bitcoin_miner", () -> TileEntityType.Builder.create(BitcoinTileEntity::new, BlockInit.BITCOIN_MINER.get()).build(null));
}
