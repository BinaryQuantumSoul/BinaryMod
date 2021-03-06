package com.quantumsoul.binarymod.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import com.quantumsoul.binarymod.entity.ZeroEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZeroModel extends AgeableModel<ZeroEntity>
{
    private final ModelRenderer body;
    private final ModelRenderer rightLeg;
    private final ModelRenderer leftLeg;

    public ZeroModel()
    {
        super(false, 10.0F, 4.0F, 2.0F, 2.0F, 24);
        this.textureWidth = 32;
        this.textureHeight = 32;

        body = new ModelRenderer(this, 0, 0);
        body.setRotationPoint(0.0F, 16.0F, 0.0F);
        body.addBox(-4.0F, -14.0F, -2.0F, 8.0F, 14.0F, 4.0F, 0.0F, false);

        leftLeg = new ModelRenderer(this, 0, 18);
        leftLeg.setRotationPoint(-2.0F, 16.0F, 0.0F);
        leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);

        rightLeg = new ModelRenderer(this, 0, 18);
        rightLeg.setRotationPoint(2.0F, 16.0F, 0.0F);
        rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(ZeroEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
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