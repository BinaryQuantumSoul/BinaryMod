package com.quantumsoul.binarymod.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.quantumsoul.binarymod.block.MachineBlock;
import com.quantumsoul.binarymod.block.UpgradableBlock;
import com.quantumsoul.binarymod.tileentity.FactoryTileEntity;
import com.quantumsoul.binarymod.tileentity.UpgradableTileEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.I18n;

import static com.quantumsoul.binarymod.util.BitcoinUtils.getBitcoinString;

public class FactoryTileRenderer extends TileEntityRenderer<FactoryTileEntity>
{
    private final String local;

    public FactoryTileRenderer(TileEntityRendererDispatcher rendererDispatcherIn, String local)
    {
        super(rendererDispatcherIn);
        this.local = local;
    }

    @Override
    public void render(FactoryTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        if (tileEntityIn.isDone())
        {
            //ROTATION
            float rotation = -tileEntityIn.getBlockState().get(MachineBlock.DIRECTION).getHorizontalAngle();

            matrixStackIn.push();

            matrixStackIn.translate(0.5D, 0.375D, 0.5D);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(rotation));
            matrixStackIn.translate(0.0D, 0.0D, 0.44D);

            //TEXT
            float f = 0.0125F;
            matrixStackIn.scale(f, -f, f);

            FontRenderer fontrenderer = this.renderDispatcher.getFontRenderer();

            String s = I18n.format("machine.binarymod.click");
            float x = (float) (-fontrenderer.getStringWidth(s) / 2);
            fontrenderer.renderString(s, x, 0.0F, 0x4CFF00, false, matrixStackIn.getLast().getMatrix(), bufferIn, false, 0, combinedLightIn);

            matrixStackIn.translate(0.0D, 10D, 0.0D);
            s = I18n.format("machine.binarymod." + local);
            x = (float) (-fontrenderer.getStringWidth(s) / 2);
            fontrenderer.renderString(s, x, 0.0F, 0x4CFF00, false, matrixStackIn.getLast().getMatrix(), bufferIn, false, 0, combinedLightIn);

            matrixStackIn.pop();
        }
    }
}