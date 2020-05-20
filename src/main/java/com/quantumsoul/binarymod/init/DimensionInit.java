package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.world.dimension.BinaryDimension;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;

import java.util.function.BiFunction;

@Mod.EventBusSubscriber(modid = BinaryMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DimensionInit
{
    public static final DeferredRegister<ModDimension> MOD_DIMENSIONS = new DeferredRegister<>(ForgeRegistries.MOD_DIMENSIONS, BinaryMod.MOD_ID);

    public static final ResourceLocation DIM_BINARY_RL = new ResourceLocation(BinaryMod.MOD_ID, "binary_dimension");
    public static DimensionType DIM_BINARY_TYPE;
    public static final RegistryObject<ModDimension> DIM_BINARY_MOD = MOD_DIMENSIONS.register(DIM_BINARY_RL.getPath(), () -> new ModDimension()
    {
        @Override
        public BiFunction<World, DimensionType, ? extends Dimension> getFactory()
        {
            return BinaryDimension::new;
        }
    });

    @SubscribeEvent
    public static void registerDimensions(RegisterDimensionsEvent event)
    {
        DIM_BINARY_TYPE = DimensionManager.registerOrGetDimension(DIM_BINARY_RL, DIM_BINARY_MOD.get(), null, true);
    }
}
