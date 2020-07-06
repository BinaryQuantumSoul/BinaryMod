package com.quantumsoul.binarymod.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.client.render.entity.model.VirusModel;
import com.quantumsoul.binarymod.entity.VirusEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VirusRenderer extends MobRenderer<VirusEntity, VirusModel>
{
    public static final ResourceLocation VIRUS_TEXTURE = new ResourceLocation(BinaryMod.MOD_ID, "textures/entity/virus.png");

    public VirusRenderer(EntityRendererManager renderManagerIn)
    {
        super(renderManagerIn, new VirusModel(), 0.5F);
    }

    @Override
    public void render(VirusEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        this.shadowSize = 0.5F * (float) entityIn.getSlimeSize();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected void preRenderCallback(VirusEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime)
    {
        float f = 0.999F;
        matrixStackIn.scale(f, f, f);
        matrixStackIn.translate(0.0D, 0.001F, 0.0D);

        float f1 = (float) entitylivingbaseIn.getSlimeSize();
        matrixStackIn.scale(f1, f1, f1);
    }

    @Override
    public ResourceLocation getEntityTexture(VirusEntity entity)
    {
        return VIRUS_TEXTURE;
    }
}
