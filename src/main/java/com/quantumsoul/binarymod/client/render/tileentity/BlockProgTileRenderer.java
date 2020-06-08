package com.quantumsoul.binarymod.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.quantumsoul.binarymod.block.MachineBlock;
import com.quantumsoul.binarymod.tileentity.BlockProgTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class BlockProgTileRenderer extends TileEntityRenderer<BlockProgTileEntity>
{
    public BlockProgTileRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }

    private float time = 0;

    @Override
    public void render(BlockProgTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        //SCREEN ROTATION
        float rotation = -tileEntityIn.getBlockState().get(MachineBlock.DIRECTION).getHorizontalAngle();
        matrixStackIn.push();

        matrixStackIn.translate(0.5D, 0.0D, 0.5D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(rotation));
        matrixStackIn.translate(0.0D, 0.0D, 0.44D);

        //TEXT
        String s;
        if (!tileEntityIn.isDoing())
        {
            matrixStackIn.translate(0.0D, 0.25D, 0.0D);
            s = I18n.format("machine.binarymod.block_programmer_0");
        } else
        {
            //PROGRESS BAR
            IVertexBuilder builder = bufferIn.getBuffer(RenderType.getEntitySolid(new ResourceLocation("binarymod:textures/block/void_block.png")));
            drawRectangle(16, 13.5F, 84, 20, true, 0, builder, matrixStackIn, combinedLightIn);
            drawRectangle(17, 14.5F, 83, 19, false, 1, builder, matrixStackIn, combinedLightIn);
            drawRectangle(17, 14.5F, (int) (17 + (66 * tileEntityIn.getProgress())), 19, true, 2, builder, matrixStackIn, combinedLightIn);

            matrixStackIn.translate(0.0D, 0.3D, 0.0D);
            s = I18n.format("machine.binarymod.block_programmer_1");
        }

        //TEXT
        float f2 = 0.010F;
        matrixStackIn.scale(f2, -f2, f2);

        FontRenderer fontrenderer = this.renderDispatcher.getFontRenderer();
        float x = (float) (-fontrenderer.getStringWidth(s) / 2);
        fontrenderer.renderString(s, x, 0.0F, 0x4CFF00, false, matrixStackIn.getLast().getMatrix(), bufferIn, false, 0, combinedLightIn);

        matrixStackIn.pop();

        //ITEM
        if (tileEntityIn.isDoing())
        {
            matrixStackIn.push();
            matrixStackIn.translate(0.5D, 0.625D, 0.5D);
            time = time > 360F ? 0F : time + 2.5F;
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(time));
            Minecraft.getInstance().getItemRenderer().renderItem(tileEntityIn.getResult(), ItemCameraTransforms.TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
            matrixStackIn.pop();
        }
    }

    private void drawRectangle(float x1, float y1, float x2, float y2, boolean green, int n, IVertexBuilder builder, MatrixStack matrix, int combinedLightIn)
    {
        x1 = x1 / 100F - 0.5F;
        x2 = x2 / 100F - 0.5F;
        y1 /= 100;
        y2 /= 100;

        Matrix4f matrix4f = matrix.getLast().getMatrix();
        Matrix3f matrix3f = matrix.getLast().getNormal();

        builder.pos(matrix4f, x1, y1, n / 10000F).color(green ? 76 : 0, green ? 255 : 0, 0, 255).tex(0, 0).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
        builder.pos(matrix4f, x2, y1, n / 10000F).color(green ? 76 : 0, green ? 255 : 0, 0, 255).tex(0, 0).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
        builder.pos(matrix4f, x2, y2, n / 10000F).color(green ? 76 : 0, green ? 255 : 0, 0, 255).tex(0, 0).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
        builder.pos(matrix4f, x1, y2, n / 10000F).color(green ? 76 : 0, green ? 255 : 0, 0, 255).tex(0, 0).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }
}
