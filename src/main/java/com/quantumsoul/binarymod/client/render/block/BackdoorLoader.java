package com.quantumsoul.binarymod.client.render.block;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.quantumsoul.binarymod.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class BackdoorLoader implements IModelLoader<BackdoorLoader.BackdoorGeometry>
{
    @Override
    public void onResourceManagerReload(@Nonnull IResourceManager resourceManager)
    {}

    @Nonnull
    @Override
    public BackdoorGeometry read(@Nonnull JsonDeserializationContext deserializationContext, @Nonnull JsonObject modelContents)
    {
        return new BackdoorGeometry();
    }

    public static class BackdoorGeometry implements IModelGeometry<BackdoorGeometry>
    {
        @Override
        public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation)
        {
            return new BackdoorModel(spriteGetter.apply(owner.resolveTexture("particle")));
        }

        @Override
        public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors)
        {
            return Collections.singleton(owner.resolveTexture("particle"));
        }

        public static class BackdoorModel implements IDynamicBakedModel
        {
            private final ModelProperty<BlockState> MIMIC = new ModelProperty<>();
            private final ModelDataMap.Builder builder = new ModelDataMap.Builder().withProperty(MIMIC);

            private final TextureAtlasSprite particle;

            public BackdoorModel(TextureAtlasSprite particleTexture)
            {
                particle = particleTexture;
            }

            @Nonnull
            @Override
            public IModelData getModelData(@Nonnull IBlockDisplayReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData)
            {
                BlockPos.Mutable mutable = new BlockPos.Mutable();
                mutable.setPos(pos);
                BlockState nextState;
                do
                {
                    mutable.setPos(mutable.getX(), mutable.getY() - 1D, mutable.getZ());
                    nextState = world.getBlockState(mutable);
                } while (nextState == BlockInit.BACKDOOR.get().getDefaultState());

                IModelData data = builder.build();
                data.setData(MIMIC, nextState);
                return data;
            }

            @Nonnull
            @Override
            public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData)
            {
                BlockState mimic = Objects.requireNonNull(extraData.getData(MIMIC));
                ModelResourceLocation location = BlockModelShapes.getModelLocation(mimic != Blocks.AIR.getDefaultState() ? mimic : BlockInit.VOID_BLOCK.get().getDefaultState());
                IBakedModel model = Minecraft.getInstance().getModelManager().getModel(location);
                return model.getBakedModel().getQuads(mimic, side, rand, extraData);
            }

            @Override
            public boolean isAmbientOcclusion()
            {
                return true;
            }

            @Override
            public boolean isGui3d()
            {
                return false;
            }

            @Override
            public boolean isSideLit()
            {
                return false;
            }

            @Override
            public boolean isBuiltInRenderer()
            {
                return false;
            }

            @Nonnull
            @Override
            public TextureAtlasSprite getParticleTexture()
            {
                return particle;
            }

            @Nonnull
            @Override
            public ItemOverrideList getOverrides()
            {
                return ItemOverrideList.EMPTY;
            }
        }
    }
}
