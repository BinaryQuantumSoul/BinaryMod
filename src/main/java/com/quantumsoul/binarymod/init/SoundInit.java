package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.BinaryMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundInit
{
    public static final DeferredRegister<SoundEvent> SOUNDS = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, BinaryMod.MOD_ID);

    public static final RegistryObject<SoundEvent> ONE_ZERO_AMBIENT = addSound("entity.one_zero.ambient");
    public static final RegistryObject<SoundEvent> ONE_ZERO_HURT = addSound("entity.one_zero.hurt");
    public static final RegistryObject<SoundEvent> ONE_ZERO_DEATH = addSound("entity.one_zero.death");

    public static final RegistryObject<SoundEvent> VIRUS_JUMP = addSound("entity.virus.squish");
    
    public static final RegistryObject<SoundEvent> VOID_SOUL_AMBIENT = addSound("entity.void_soul.ambient");
    public static final RegistryObject<SoundEvent> VOID_SOUL_HURT = addSound("entity.void_soul.hurt");
    public static final RegistryObject<SoundEvent> VOID_SOUL_DEATH = addSound("entity.void_soul.death");

    public static final RegistryObject<SoundEvent> WORM_AMBIENT = addSound("entity.worm.ambient");
    public static final RegistryObject<SoundEvent> WORM_HURT = addSound("entity.worm.hurt");
    public static final RegistryObject<SoundEvent> WORM_DEATH = addSound("entity.worm.death");

    private static RegistryObject<SoundEvent> addSound(String name)
    {
        return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(BinaryMod.MOD_ID, name)));
    }
}
