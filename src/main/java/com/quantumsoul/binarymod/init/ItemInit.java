package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.item.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit
{
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, BinaryMod.MOD_ID);

    public static final Item.Properties BASE = new Item.Properties().group(ItemGroupInit.instance);
    public static final Item.Properties MACHINE = new Item.Properties().group(ItemGroupInit.instance).maxStackSize(1);
    private static final Item.Properties FAKE_FOOD = new Item.Properties().food(new Food.Builder().setAlwaysEdible().hunger(0).saturation(0F).build()).maxStackSize(1).group(ItemGroupInit.instance);
    private static final Item.Properties ONION_FOOD = new Item.Properties().group(ItemGroupInit.instance).food(new Food.Builder().hunger(4).saturation(0.2F).build());
    private static Item.Properties special()
    {
        return new Item.Properties().group(ItemGroupInit.instance);
    }

    public static final RegistryObject<Item> RED_PILL = ITEMS.register("red_pill", () -> new PillItem(PillItem.Pill.RED, FAKE_FOOD));
    public static final RegistryObject<Item> BLUE_PILL = ITEMS.register("blue_pill", () -> new PillItem(PillItem.Pill.BLUE, FAKE_FOOD));

    public static final RegistryObject<Item> BIT = ITEMS.register("bit", () -> new Item(BASE));
    public static final RegistryObject<Item> BYTE = ITEMS.register("byte", () -> new Item(BASE));
    public static final RegistryObject<Item> CODE = ITEMS.register("code", () -> new Item(BASE));
    public static final RegistryObject<Item> VOID = ITEMS.register("void", () -> new Item(BASE));
    public static final RegistryObject<Item> FIREBRICK = ITEMS.register("firebrick", () -> new Item(BASE));

    public static final RegistryObject<Item> FILE_CORRUPT = ITEMS.register("file_corrupt", () -> new CorruptItem(BASE));
    public static final RegistryObject<Item> FILE_SAFE = ITEMS.register("file_safe", () -> new Item(BASE));
    public static final RegistryObject<Item> FILE_INFECT = ITEMS.register("file_infect", () -> new Item(BASE));

    public static final RegistryObject<Item> ANTIVIRUS_TOOL = ITEMS.register("antivirus_tool", () -> new AxeItem(ItemTier.DIAMOND, 5F, -3F, special()));
    //public static final RegistryObject<Item> DEBUG_TOOL = ITEMS.register("debug_tool", () -> new DebugItem(BASE));

    public static final RegistryObject<Item> SD_CARD_SMALL = ITEMS.register("sd_card_small", () -> new SDCardItem(MACHINE, SDCardItem.SDSize.SMALL));
    public static final RegistryObject<Item> SD_CARD_MEDIUM = ITEMS.register("sd_card_medium", () -> new SDCardItem(MACHINE, SDCardItem.SDSize.MEDIUM));
    public static final RegistryObject<Item> SD_CARD_BIG = ITEMS.register("sd_card_big", () -> new SDCardItem(MACHINE, SDCardItem.SDSize.BIG));
    //public static final RegistryObject<Item> BATTERY = ITEMS.register("battery", () -> new BatteryItem(MACHINE));

    public static final RegistryObject<Item> UPGRADE = ITEMS.register("upgrade", () -> new BatteryItem(BASE));

    public static final RegistryObject<Item> ONION = ITEMS.register("onion", () -> new Item(ONION_FOOD));
    public static final RegistryObject<Item> BITCOIN = ITEMS.register("bitcoin", () -> new BitcoinItem(BASE, BitcoinItem.Bitcoin.UNIT));
    public static final RegistryObject<Item> K_BTC = ITEMS.register("btc_k", () -> new BitcoinItem(BASE, BitcoinItem.Bitcoin.KILO));
    public static final RegistryObject<Item> M_BTC = ITEMS.register("btc_m", () -> new BitcoinItem(BASE, BitcoinItem.Bitcoin.MEGA));
    public static final RegistryObject<Item> G_BTC = ITEMS.register("btc_g", () -> new BitcoinItem(BASE, BitcoinItem.Bitcoin.GIGA));
    public static final RegistryObject<Item> T_BTC = ITEMS.register("btc_t", () -> new BitcoinItem(BASE, BitcoinItem.Bitcoin.TERA));
    public static final RegistryObject<Item> P_BTC = ITEMS.register("btc_p", () -> new BitcoinItem(BASE, BitcoinItem.Bitcoin.PETA));
}
