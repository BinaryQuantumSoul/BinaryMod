package com.quantumsoul.binarymod.render.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.quantumsoul.binarymod.entity.OneEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OneModel extends AgeableModel<OneEntity>
{
    private final ModelRenderer rightLeg;
    private final ModelRenderer leftLeg;
    private final ModelRenderer body;

    public OneModel()
    {
        super(false, 10.0F, 4.0F, 2.0F, 2.0F, 24);
        this.textureWidth = 64;
        this.textureHeight = 64;

        body = new ModelRenderer(this, 0, 0);
        body.setRotationPoint(0.0F, 12.0F, 0.0F);
        body.addBox(-4.0F, -20.0F, -2.0F, 8.0F, 20.0F, 4.0F, 0.0F, false);

        rightLeg = new ModelRenderer(this, 0, 24);
        rightLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        leftLeg = new ModelRenderer(this, 0, 24);
        leftLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(OneEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1F * limbSwingAmount;
        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1F * limbSwingAmount;
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts()
    {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts()
    {
        return ImmutableList.of(this.body, this.leftLeg, this.rightLeg);
    }
}