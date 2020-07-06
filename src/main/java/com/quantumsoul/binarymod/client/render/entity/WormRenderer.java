package com.quantumsoul.binarymod.client.render.entity;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.client.render.entity.model.WormModel;
import com.quantumsoul.binarymod.entity.WormEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class WormRenderer extends MobRenderer<WormEntity, WormModel>
{
    private static final ResourceLocation WORM_TEXTURE = new ResourceLocation(BinaryMod.MOD_ID, "textures/entity/worm.png");

    public WormRenderer(EntityRendererManager renderManagerIn)
    {
        super(renderManagerIn, new WormModel(), 0.25F);
    }

    @Override
    public ResourceLocation getEntityTexture(WormEntity entity)
    {
        return WORM_TEXTURE;
    }
}
