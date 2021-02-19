package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.entity.*;
import com.quantumsoul.binarymod.util.WorldUtils;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static net.minecraft.entity.ai.attributes.Attributes.*;

@Mod.EventBusSubscriber(modid = BinaryMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityInit
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, BinaryMod.MOD_ID);

    public static final RegistryObject<EntityType<OneEntity>> ONE = ENTITIES.register("one", () -> EntityType.Builder.<OneEntity>create(OneEntity::new, EntityClassification.CREATURE).size(0.6F, 2F).build(BinaryMod.MOD_ID + ":one"));
    public static final RegistryObject<EntityType<ZeroEntity>> ZERO = ENTITIES.register("zero", () -> EntityType.Builder.<ZeroEntity>create(ZeroEntity::new, EntityClassification.CREATURE).size(0.6F, 1.375F).build(BinaryMod.MOD_ID + ":zero"));

    public static final RegistryObject<EntityType<BugEntity>> BUG = ENTITIES.register("bug", () -> EntityType.Builder.<BugEntity>create(BugEntity::new, EntityClassification.MONSTER).size(0.9F, 0.9F).build(BinaryMod.MOD_ID + ":bug"));
    public static final RegistryObject<EntityType<VirusEntity>> VIRUS = ENTITIES.register("virus", () -> EntityType.Builder.<VirusEntity>create(VirusEntity::new, EntityClassification.MONSTER).size(3F, 3F).build(BinaryMod.MOD_ID + ":virus"));
    public static final RegistryObject<EntityType<WormEntity>> WORM = ENTITIES.register("worm", () -> EntityType.Builder.<WormEntity>create(WormEntity::new, EntityClassification.MONSTER).size(0.7F, 0.125F).build(BinaryMod.MOD_ID + ":worm"));
    public static final RegistryObject<EntityType<VoidSoulEntity>> VOID_SOUL = ENTITIES.register("void_soul", () -> EntityType.Builder.<VoidSoulEntity>create(VoidSoulEntity::new, EntityClassification.MONSTER).size(0.5F, 0.5F).build(BinaryMod.MOD_ID + ":void_soul"));

    public static final RegistryObject<EntityType<BulletEntity>> BULLET = ENTITIES.register("bullet", () -> EntityType.Builder.<BulletEntity>create(BulletEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).build(BinaryMod.MOD_ID + ":bullet"));
    public static final RegistryObject<EntityType<FlyerEntity>> FLYER = ENTITIES.register("flyer", () -> EntityType.Builder.<FlyerEntity>create(FlyerEntity::new, EntityClassification.MISC).size(1F, 0.5F).build(BinaryMod.MOD_ID + ":flyer"));

    @SubscribeEvent
    public static void entityAttributeCreation(final EntityAttributeCreationEvent event)
    {
        event.put(ONE.get(), MobEntity.func_233666_p_().createMutableAttribute(MAX_HEALTH, 10.0D).createMutableAttribute(MOVEMENT_SPEED, 0.25D).create());
        event.put(ZERO.get(), MobEntity.func_233666_p_().createMutableAttribute(MAX_HEALTH, 10.0D).createMutableAttribute(MOVEMENT_SPEED, 0.25D).create());

        event.put(BUG.get(), MonsterEntity.func_234295_eP_().createMutableAttribute(MAX_HEALTH, 20.0D).createMutableAttribute(MOVEMENT_SPEED, 0.2D).createMutableAttribute(ATTACK_DAMAGE, 4D).create());
        event.put(VIRUS.get(), MonsterEntity.func_234295_eP_().create());
        event.put(WORM.get(), MonsterEntity.func_234295_eP_().createMutableAttribute(MAX_HEALTH, 9.0D).createMutableAttribute(MOVEMENT_SPEED, 0.5D).createMutableAttribute(ATTACK_DAMAGE, 2D).create());
        event.put(VOID_SOUL.get(), MonsterEntity.func_234295_eP_().createMutableAttribute(MAX_HEALTH, 8.0D).createMutableAttribute(FLYING_SPEED, 0.8D).createMutableAttribute(MOVEMENT_SPEED, 0.4D).createMutableAttribute(ATTACK_DAMAGE, 3D).create());
    }

    //SPAWN PLACEMENT
    public static void initSpawns()
    {
        EntitySpawnPlacementRegistry.register(EntityInit.ONE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WorldUtils::canBinDimAnimalSpawn);
        EntitySpawnPlacementRegistry.register(EntityInit.ZERO.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WorldUtils::canBinDimAnimalSpawn);

        EntitySpawnPlacementRegistry.register(EntityInit.BUG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WorldUtils::canBinDimMonsterSpawn);
        EntitySpawnPlacementRegistry.register(EntityInit.VIRUS.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WorldUtils::canBinDimMonsterSpawn);
        EntitySpawnPlacementRegistry.register(EntityInit.WORM.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WorldUtils::canBinDimMonsterSpawn);

        EntitySpawnPlacementRegistry.register(EntityInit.VOID_SOUL.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, VoidSoulEntity::canVoidSoulSpawn);
    }
}