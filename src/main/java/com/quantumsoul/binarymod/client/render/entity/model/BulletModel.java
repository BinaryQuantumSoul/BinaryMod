package com.quantumsoul.binarymod.client.render.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.quantumsoul.binarymod.entity.BulletEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BulletModel extends EntityModel<BulletEntity>
{
    public final ModelRenderer bone;

    public BulletModel()
    {
        textureWidth = 32;
        textureHeight = 32;

        bone = new ModelRenderer(this, 0, 0);
        bone.setRotationPoint(0.0F, 17.9F, 0.0F);
        setRotationAngle(bone, -0.7854F, -0.9599F, 1.5708F);
        bone.addBox(-1.2706F, -2.2683F, -3.7317F, 5.0F, 5.0F, 5.0F, -0.2F, false);
    }

    @Override
    public void setRotationAngles(BulletEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        matrixStack.push();
        matrixStack.translate(-0.025D, -1.05D, -0.025D);
        bone.render(matrixStack, buffer, packedLight, packedOverlay);
        matrixStack.pop();
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}