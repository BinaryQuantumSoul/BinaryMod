package com.quantumsoul.binarymod.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.quantumsoul.binarymod.entity.BugEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.EmptyModelData;

import static com.quantumsoul.binarymod.render.RenderUtils.applyRotations;

@OnlyIn(Dist.CLIENT)
public class BugRenderer extends EntityRenderer<BugEntity>
{
   public BugRenderer(EntityRendererManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    @Override
    public void render(BugEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        matrixStackIn.push();
        applyRotations(entityIn, matrixStackIn, MathHelper.interpolateAngle(partialTicks, entityIn.prevRenderYawOffset, entityIn.renderYawOffset), partialTicks);
        matrixStackIn.translate(-0.5D, 0.0D, -0.5D);
        renderBlockState(entityIn.getMimic(), matrixStackIn, bufferIn, packedLightIn, LivingRenderer.getPackedOverlay(entityIn, 0.0F));
        matrixStackIn.pop();
    }

    private void renderBlockState(BlockState stateIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, int packedOverlayIn)
    {
        Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(stateIn, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, EmptyModelData.INSTANCE);
    }

    @Override
    public ResourceLocation getEntityTexture(BugEntity entity)
    {
        return null;
    }
}
