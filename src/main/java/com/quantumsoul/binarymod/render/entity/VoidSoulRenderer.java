package com.quantumsoul.binarymod.render.entity;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.entity.VoidSoulEntity;
import com.quantumsoul.binarymod.render.entity.model.VoidSoulModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VoidSoulRenderer extends MobRenderer<VoidSoulEntity, VoidSoulModel>
{
    public static final ResourceLocation VOID_SOUL_TEXTURE = new ResourceLocation(BinaryMod.MOD_ID, "textures/entity/void_soul.png");

    public VoidSoulRenderer(EntityRendererManager renderManagerIn)
    {
        super(renderManagerIn, new VoidSoulModel(), 0.4F);
    }

    @Override
    public ResourceLocation getEntityTexture(VoidSoulEntity entity)
    {
        return VOID_SOUL_TEXTURE;
    }
}
