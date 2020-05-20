package com.quantumsoul.binarymod.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.quantumsoul.binarymod.entity.VirusEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VirusModel extends EntityModel<VirusEntity>
{
    private final ModelRenderer bone;

    public VirusModel()
    {
        this.textureWidth = 32;
        this.textureHeight = 32;

        bone = new ModelRenderer(this);
        bone.setRotationPoint(0.0F, 18.75F, -1.0F);
        setRotationAngle(bone, -0.7854F, 0.0F, 0.6109F);
        bone.setTextureOffset(0, 0).addBox(-5.0F, -5.0F, -3.0F, 7.0F, 7.0F, 7.0F, 0.0F, false);
        bone.setTextureOffset(0, 24).addBox(-3.0F, -9.0F, -1.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);
        bone.setTextureOffset(13, 14).addBox(-2.0F, -8.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(11, 18).addBox(-2.0F, -2.0F, 4.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
        bone.setTextureOffset(2, 20).addBox(-3.0F, -3.0F, 7.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(13, 14).addBox(-2.0F, 2.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(0, 24).addBox(-3.0F, 5.0F, -1.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);
        bone.setTextureOffset(2, 14).addBox(5.0F, -3.0F, -1.0F, 1.0F, 3.0F, 3.0F, 0.0F, false);
        bone.setTextureOffset(11, 22).addBox(2.0F, -2.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(11, 18).addBox(-2.0F, -2.0F, -6.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
        bone.setTextureOffset(2, 20).addBox(-3.0F, -3.0F, -7.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(2, 14).addBox(-9.0F, -3.0F, -1.0F, 1.0F, 3.0F, 3.0F, 0.0F, false);
        bone.setTextureOffset(11, 22).addBox(-8.0F, -2.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(VirusEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {}

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        bone.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
