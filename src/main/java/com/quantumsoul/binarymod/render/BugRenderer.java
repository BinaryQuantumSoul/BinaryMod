package com.quantumsoul.binarymod.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.entity.BugEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BugRenderer extends EntityRenderer<BugEntity>
{
    public static final ResourceLocation BUG_TEXTURE = new ResourceLocation(BinaryMod.MOD_ID, "textures/entity/bug.png");

    public BugRenderer(EntityRendererManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    @Override
    public void render(BugEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.translate(-0.8D, 0.0D, -0.8D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180F - entityYaw));
 //       renderBlockState(entityIn.mimic, matrixStackIn, bufferIn, packedLightIn);
    }

    private void renderBlockState(BlockState stateIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        if (stateIn.getRenderType() != BlockRenderType.INVISIBLE)
            Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(stateIn, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY);
        else
            Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(BugEntity.defaultMimic, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY);
    }

    @Override
    public ResourceLocation getEntityTexture(BugEntity entity)
    {
        return BUG_TEXTURE;
    }
}
