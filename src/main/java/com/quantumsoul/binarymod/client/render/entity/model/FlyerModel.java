package com.quantumsoul.binarymod.client.render.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.quantumsoul.binarymod.entity.FlyerEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class FlyerModel extends EntityModel<FlyerEntity>
{
    private final ModelRenderer bone;

    public FlyerModel()
    {
        textureWidth = 64;
        textureHeight = 64;

        bone = new ModelRenderer(this);
        bone.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone.setTextureOffset(0, 0).addBox(-7.0F, -1.0F, -7.0F, 14.0F, 1.0F, 14.0F, 0.0F, false);
        bone.setTextureOffset(0, 15).addBox(-8.0F, -8.0F, -7.0F, 1.0F, 8.0F, 14.0F, 0.0F, false);
        bone.setTextureOffset(0, 15).addBox(7.0F, -8.0F, -7.0F, 1.0F, 8.0F, 14.0F, 0.0F, false);
        bone.setTextureOffset(0, 37).addBox(-7.0F, -8.0F, -8.0F, 14.0F, 8.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(0, 37).addBox(-7.0F, -8.0F, 7.0F, 14.0F, 8.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(FlyerEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {}

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
    {
        bone.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
