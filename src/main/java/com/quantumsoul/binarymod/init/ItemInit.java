package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.item.CorruptItem;
import com.quantumsoul.binarymod.item.PillItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit
{
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, BinaryMod.MOD_ID);

    public static final Item.Properties BASE = new Item.Properties().group(ItemGroupInit.instance);
    private static final Item.Properties FAKE_FOOD = new Item.Properties().food(new Food.Builder().setAlwaysEdible().hunger(0).saturation(0F).build()).maxStackSize(1).group(ItemGroupInit.instance);
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
}
