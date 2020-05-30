package com.quantumsoul.binarymod.render.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.quantumsoul.binarymod.entity.WormEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class WormModel extends EntityModel<WormEntity>
{
    private final ModelRenderer bone0;
    private final ModelRenderer bone1;
    private final ModelRenderer bone2;
    private final ModelRenderer bone3;
    private float[] delta0;
    private float[] delta3;

    public WormModel()
    {
        this.textureWidth = 16;
        this.textureHeight = 16;

        bone0 = new ModelRenderer(this, 0, 6);
        bone0.setRotationPoint(0.0F, 0.0F, -4.0F);
        bone0.addBox(-1.0F, -1.0F, -4.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);

        bone1 = new ModelRenderer(this, 0, 0);
        bone1.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone1.addBox(-1.0F, -1.0F, -4.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);

        bone2 = new ModelRenderer(this, 0, 0);
        bone2.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone2.addBox(-1.0F, -1.0F, -4.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);

        bone3 = new ModelRenderer(this, 0, 0);
        bone3.setRotationPoint(0.0F, 0.0F, 4.0F);
        bone3.addBox(-1.0F, -1.0F, -4.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        matrixStack.translate(0, 23 / 16.0F, 0);

        matrixStack.translate(-delta0[0] / 16.0F, 0, -delta0[1] / 16.0F);
        bone0.render(matrixStack, buffer, packedLight, packedOverlay);
        matrixStack.translate(delta0[0] / 16.0F, 0, delta0[1] / 16.0F);

        bone1.render(matrixStack, buffer, packedLight, packedOverlay);
        bone2.render(matrixStack, buffer, packedLight, packedOverlay);

        matrixStack.translate(-delta3[0] / 16.0F, 0, -delta3[1] / 16.0F);
        bone3.render(matrixStack, buffer, packedLight, packedOverlay);
        matrixStack.translate(delta3[0] / 16.0F, 0, delta3[1] / 16.0F);
    }

    @Override
    public void setRotationAngles(WormEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        //===== BODY =====
        float right = MathHelper.cos(limbSwing * 0.666F) * limbSwingAmount;
        float left = MathHelper.cos(limbSwing * 0.666F + (float)Math.PI) * limbSwingAmount;

        bone1.rotateAngleY = right;
        bone2.rotateAngleY = left + (float)Math.PI;
        bone3.rotateAngleY = right + (float)Math.PI;

        delta0 = new float[]{4.0F * MathHelper.sin(right), 4.0F * MathHelper.cos(right) - 4.0F};
        delta3 = new float[]{4.0F * MathHelper.sin(right), 4.0F - 4.0F * MathHelper.cos(right)};

        //===== HEAD =====
        bone0.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        if (entityIn.getTicksElytraFlying() > 4)
            bone0.rotateAngleX = (-(float)Math.PI / 4F);
        else
            bone0.rotateAngleX = headPitch * ((float)Math.PI / 180F);
    }
}
