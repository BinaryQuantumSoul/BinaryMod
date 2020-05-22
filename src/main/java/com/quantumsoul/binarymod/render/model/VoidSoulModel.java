package com.quantumsoul.binarymod.render.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.quantumsoul.binarymod.entity.VoidSoulEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VoidSoulModel extends EntityModel<VoidSoulEntity>
{
    private final ModelRenderer bone;

    public VoidSoulModel()
    {
        this.textureWidth = 32;
        this.textureHeight = 32;

        bone = new ModelRenderer(this, 0, 0);
        bone.setRotationPoint(0.0F, 4.0F, 0.0F);
        bone.addBox(-4.0F, 0, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(VoidSoulEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {}

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        matrixStack.translate(0.0D, 0.75F, 0.0D);

        bone.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}