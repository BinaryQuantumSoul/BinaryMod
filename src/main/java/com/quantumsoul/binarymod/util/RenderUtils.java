package com.quantumsoul.binarymod.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;

public class RenderUtils
{
    public static void applyRotations(LivingEntity entityLiving, MatrixStack matrixStackIn, float rotationYaw, float partialTicks)
    {
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F - rotationYaw));

        if (entityLiving.deathTime > 0)
        {
            float f = ((float) entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
            f = MathHelper.sqrt(f);
            if (f > 1.0F)
                f = 1.0F;

            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(f * 90.0F));
        }
        else if (entityLiving.hasCustomName())
        {
            String s = TextFormatting.getTextWithoutFormattingCodes(entityLiving.getName().getString());
            if ("Dinnerbone".equals(s) || "Grumm".equals(s))
            {
                matrixStackIn.translate(0.0D, entityLiving.getHeight() + 0.1F, 0.0D);
                matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(180.0F));
            }
        }
    }
}
