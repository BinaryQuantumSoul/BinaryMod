package com.quantumsoul.binarymod.render;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.entity.OneEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OneRenderer extends MobRenderer<OneEntity, OneModel>
{
    public static final ResourceLocation ONE_TEXTURE = new ResourceLocation(BinaryMod.MOD_ID, "textures/entity/one.png");

    public OneRenderer(EntityRendererManager renderManagerIn)
    {
        super(renderManagerIn, new OneModel(), 0.4F);
    }

    @Override
    public ResourceLocation getEntityTexture(OneEntity entity)
    {
        return ONE_TEXTURE;
    }
}
