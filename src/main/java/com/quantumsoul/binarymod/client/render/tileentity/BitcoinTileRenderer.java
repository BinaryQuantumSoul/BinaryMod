package com.quantumsoul.binarymod.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.quantumsoul.binarymod.block.MachineBlock;
import com.quantumsoul.binarymod.tileentity.BitcoinTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.quantumsoul.binarymod.util.BitcoinUtils.getBitcoinStack;
import static com.quantumsoul.binarymod.util.BitcoinUtils.getBitcoinString;

@OnlyIn(Dist.CLIENT)
public class BitcoinTileRenderer extends TileEntityRenderer<BitcoinTileEntity>
{
    public BitcoinTileRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(BitcoinTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        double value = tileEntityIn.getValue();
        float rotation = -tileEntityIn.getBlockState().get(MachineBlock.DIRECTION).getHorizontalAngle();

        //TEXT
        matrixStackIn.push();

        matrixStackIn.translate(0.5D, 0.375D, 0.5D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(rotation));
        matrixStackIn.translate(0.0D, 0.0D, 0.44D);

        float f2 = 0.021F;
        matrixStackIn.scale(f2, -f2, f2);

        FontRenderer fontrenderer = this.renderDispatcher.getFontRenderer();
        String s = getBitcoinString(value);
        float x = (float) (-fontrenderer.getStringWidth(s) / 2);

        fontrenderer.renderString(s, x, 0.0F, 0x4CFF00, false, matrixStackIn.getLast().getMatrix(), bufferIn, false, 0, combinedLightIn);
        matrixStackIn.pop();

        //IMAGE
        matrixStackIn.push();

        matrixStackIn.translate(0.5D, 0.625D, 0.5D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(rotation));
        matrixStackIn.translate(0.0D, 0.0D, 0.4225D);

        matrixStackIn.scale(0.5F, 0.5F, 0.5F);

        Minecraft.getInstance().getItemRenderer().renderItem(getBitcoinStack(value), ItemCameraTransforms.TransformType.GUI, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
        matrixStackIn.pop();
    }
}
