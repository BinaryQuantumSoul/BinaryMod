package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.entity.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, BinaryMod.MOD_ID);

    public static final RegistryObject<EntityType<OneEntity>> ONE = ENTITIES.register("one", () -> EntityType.Builder.<OneEntity>create(OneEntity::new, EntityClassification.CREATURE).size(0.6F, 2F).build(BinaryMod.MOD_ID + ":one"));
    public static final RegistryObject<EntityType<ZeroEntity>> ZERO = ENTITIES.register("zero", () -> EntityType.Builder.<ZeroEntity>create(ZeroEntity::new, EntityClassification.CREATURE).size(0.6F, 1.375F).build(BinaryMod.MOD_ID + ":zero"));

    public static final RegistryObject<EntityType<BugEntity>> BUG = ENTITIES.register("bug", () -> EntityType.Builder.<BugEntity>create(BugEntity::new, EntityClassification.MONSTER).size(0.9F, 0.9F).build(BinaryMod.MOD_ID + ":bug"));
    public static final RegistryObject<EntityType<VirusEntity>> VIRUS = ENTITIES.register("virus", () -> EntityType.Builder.<VirusEntity>create(VirusEntity::new, EntityClassification.MONSTER).size(3F, 3F).build(BinaryMod.MOD_ID + ":virus"));

    public static final RegistryObject<EntityType<VoidSoulEntity>> VOID_SOUL = ENTITIES.register("void_soul", () -> EntityType.Builder.<VoidSoulEntity>create(VoidSoulEntity::new, EntityClassification.MONSTER).size(0.5F, 0.5F).build(BinaryMod.MOD_ID + ":void_soul"));
    public static final RegistryObject<EntityType<WormEntity>> WORM = ENTITIES.register("worm", () -> EntityType.Builder.<WormEntity>create(WormEntity::new, EntityClassification.MONSTER).size(1F, 0.125F).build(BinaryMod.MOD_ID + ":worm"));
}