package com.quantumsoul.binarymod.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.client.render.entity.model.FlyerModel;
import com.quantumsoul.binarymod.entity.FlyerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class FlyerRenderer extends EntityRenderer<FlyerEntity>
{
    private static final ResourceLocation FLYER_TEXTURE = new ResourceLocation(BinaryMod.MOD_ID, "textures/entity/flyer.png");
    protected final FlyerModel model = new FlyerModel();

    public FlyerRenderer(EntityRendererManager renderManagerIn)
    {
        super(renderManagerIn);
        this.shadowSize = 0.4F;
    }

    public void render(FlyerEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, 0D, 0.0D);

        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(entityIn.rotationPitch));
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90.0F));

        IVertexBuilder vb1 = bufferIn.getBuffer(model.getRenderType(getEntityTexture(entityIn)));
        model.render(matrixStackIn, vb1, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(FlyerEntity entity)
    {
        return FLYER_TEXTURE;
    }
}
