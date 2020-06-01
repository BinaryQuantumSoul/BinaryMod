package com.quantumsoul.binarymod.client.render.entity;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.entity.ZeroEntity;
import com.quantumsoul.binarymod.client.render.entity.model.ZeroModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZeroRenderer extends MobRenderer<ZeroEntity, ZeroModel>
{
    public static final ResourceLocation ZERO_TEXTURE = new ResourceLocation(BinaryMod.MOD_ID, "textures/entity/zero.png");

    public ZeroRenderer(EntityRendererManager renderManagerIn)
    {
        super(renderManagerIn, new ZeroModel(), 0.4F);
    }

    @Override
    public ResourceLocation getEntityTexture(ZeroEntity entity)
    {
        return ZERO_TEXTURE;
    }
}
