package com.quantumsoul.binarymod.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.client.render.entity.model.BulletModel;
import com.quantumsoul.binarymod.entity.BulletEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BulletRenderer extends EntityRenderer<BulletEntity>
{
    public static final ResourceLocation BULLET_TEXTURE = new ResourceLocation(BinaryMod.MOD_ID, "textures/entity/bullet.png");
    public static final RenderType RENDER_TYPE = RenderType.getEntityCutout(BULLET_TEXTURE);

    private final BulletModel model = new BulletModel();

    public BulletRenderer(EntityRendererManager renderManager)
    {
        super(renderManager);
    }

    @Override
    public void render(BulletEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        model.render(matrixStackIn, bufferIn.getBuffer(RENDER_TYPE), packedLightIn, 10 << 16, 1F, 1F, 1F, 1F);
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(BulletEntity entity)
    {
        return BULLET_TEXTURE;
    }
}
