package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.block.*;
import com.quantumsoul.binarymod.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = BinaryMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockInit
{
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, BinaryMod.MOD_ID);
    public static final DeferredRegister<Block> MACHINES = new DeferredRegister<>(ForgeRegistries.BLOCKS, BinaryMod.MOD_ID);

    private static final Block.Properties BASE = Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5F).harvestLevel(2).harvestTool(ToolType.PICKAXE);
    private static final Block.Properties NOT_RESISTANT = Block.Properties.create(Material.EARTH).hardnessAndResistance(0F).sound(SoundType.SNOW);
    private static final Block.Properties FULL_RESISTANT = Block.Properties.create(Material.ROCK).hardnessAndResistance(-1F, 360000F).noDrops();
    private static final Block.Properties ALIVE = Block.Properties.create(Material.WOOD).hardnessAndResistance(4F).sound(SoundType.NETHER_WART);
    private static final Block.Properties DEAD = Block.Properties.create(Material.WOOD).hardnessAndResistance(1.5F).sound(SoundType.NETHER_WART);
    private static final Block.Properties MACHINE = Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5F).sound(SoundType.METAL).lightValue(7);
    private static final Block.Properties NOT_SOLID = Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5F).notSolid();

    public static final RegistryObject<Block> BINARY_ORE = BLOCKS.register("binary_ore", () -> new BinaryOreBlock(BASE));
    public static final RegistryObject<Block> BINARY_BLOCK = BLOCKS.register("binary_block", () -> new Block(BASE));
    public static final RegistryObject<Block> ON_BINARY_BLOCK = BLOCKS.register("on_binary_block", () -> new Block(BASE));
    public static final RegistryObject<Block> WIRE_BLOCK = BLOCKS.register("wire_block", () -> new WireBlock(BASE));
    public static final RegistryObject<Block> CODE_BLOCK = BLOCKS.register("code_block", () -> new Block(BASE));
    public static final RegistryObject<Block> VOID_BLOCK = BLOCKS.register("void_block", () -> new Block(NOT_RESISTANT));
    public static final RegistryObject<Block> FIREWALL_BLOCK = BLOCKS.register("firewall_block", () -> new FirewallBlock(FULL_RESISTANT));

    public static final RegistryObject<Block> HEX_RED = BLOCKS.register("hex_red", () -> new HexBlock(BASE));
    public static final RegistryObject<Block> HEX_GREEN = BLOCKS.register("hex_green", () -> new HexBlock(BASE));
    public static final RegistryObject<Block> HEX_BLUE = BLOCKS.register("hex_blue", () -> new HexBlock(BASE));
    public static final RegistryObject<Block> HEX_CYAN = BLOCKS.register("hex_cyan", () -> new HexBlock(BASE));
    public static final RegistryObject<Block> HEX_MAGENTA = BLOCKS.register("hex_magenta", () -> new HexBlock(BASE));
    public static final RegistryObject<Block> HEX_YELLOW = BLOCKS.register("hex_yellow", () -> new HexBlock(BASE));
    public static final RegistryObject<Block> HEX_ORANGE = BLOCKS.register("hex_orange", () -> new HexBlock(BASE));

    public static final RegistryObject<Block> BUG_BLOCK = BLOCKS.register("bug_block", () -> new BugBlock(DEAD));
    public static final RegistryObject<Block> VIRUS_BLOCK = BLOCKS.register("virus_block", () -> new VirusBlock(ALIVE));
    public static final RegistryObject<Block> VIRUS_BUG_BLOCK = BLOCKS.register("virus_bug_block", () -> new VirusBugBlock(ALIVE));
    public static final RegistryObject<Block> VIRUS_DEAD_BLOCK = BLOCKS.register("virus_dead_block", () -> new Block(DEAD));

    public static final RegistryObject<Block> COMPUTER = MACHINES.register("computer", () -> new MachineBlock(MACHINE, ComputerTileEntity::new));
    public static final RegistryObject<Block> FEEDER = MACHINES.register("feeder", () -> FactoryBlock.create(MACHINE, FeederTileEntity::new, 2));
    public static final RegistryObject<Block> HEALER = MACHINES.register("healer", () -> FactoryBlock.create(MACHINE, HealerTileEntity::new, 4));
    public static final RegistryObject<Block> REPAIRER = MACHINES.register("repairer", () -> FactoryBlock.create(MACHINE, RepairerTileEntity::new, 4));
    public static final RegistryObject<Block> BITCOIN_MINER = MACHINES.register("bitcoin_miner", () -> UpgradableBlock.create(MACHINE, BitcoinTileEntity::new, 4));
    public static final RegistryObject<Block> BLOCK_PROGRAMMER = MACHINES.register("block_programmer", () -> new HalfMachineBlock(MACHINE, BlockProgTileEntity::new));

    public static final RegistryObject<Block> MYSTERY_BOX = BLOCKS.register("mystery_box", () -> new MysteryBlock(BASE));
    public static final RegistryObject<Block> TROJAN = BLOCKS.register("trojan", () -> new TrojanBlock(BASE));
    public static final RegistryObject<Block> KEY_LOGGER = BLOCKS.register("key_logger", () -> new KeyLoggerBlock(BASE));
    public static final RegistryObject<Block> BACKDOOR = BLOCKS.register("backdoor", () -> new BackdoorBlock(NOT_SOLID));

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event)
    {
        final IForgeRegistry<Item> registry = event.getRegistry();

        BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block ->
                registry.register(new BlockItem(block, ItemInit.BASE).setRegistryName(block.getRegistryName())));

        BlockInit.MACHINES.getEntries().stream().map(RegistryObject::get).forEach(block ->
                registry.register(new BlockItem(block, ItemInit.MACHINE).setRegistryName(block.getRegistryName())));
    }
}
