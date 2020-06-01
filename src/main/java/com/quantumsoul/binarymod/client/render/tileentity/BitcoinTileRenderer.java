package com.quantumsoul.binarymod.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.quantumsoul.binarymod.block.AbstractMachineBlock;
import com.quantumsoul.binarymod.init.ItemInit;
import com.quantumsoul.binarymod.tileentity.BitcoinTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BitcoinTileRenderer extends TileEntityRenderer<BitcoinTileEntity>
{
    private static final Item[] btc_items = {ItemInit.BITCOIN.get(), ItemInit.K_BTC.get(), ItemInit.M_BTC.get(), ItemInit.G_BTC.get(), ItemInit.T_BTC.get(), ItemInit.P_BTC.get()};
    private static final char[] prefixes = new char[]{' ', 'k', 'M', 'G', 'T', 'P'};

    public BitcoinTileRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(BitcoinTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        double value = tileEntityIn.getValue();
        float rotation = -tileEntityIn.getBlockState().get(AbstractMachineBlock.DIRECTION).getHorizontalAngle();

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

        fontrenderer.renderString(s, x, 0.0F, 0x00FF00, false, matrixStackIn.getLast().getMatrix(), bufferIn, false, 0, combinedLightIn);
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

    public static String getBitcoinString(double value)
    {
        for (int i = prefixes.length - 1; i >= 0; i--)
        {
            double remainder = value / Math.pow(8, i);
            if ((int) remainder > 0)
                return String.format("%.2f %cB", remainder, prefixes[i]);
        }

        return String.format("%.2f  B", value);
    }

    public static ItemStack getBitcoinStack(double value)
    {
        for (int i = btc_items.length - 1; i >= 0; i--)
        {
            double remainder = value / Math.pow(8, i);
            if ((int) remainder > 0)
                return new ItemStack(btc_items[i]);
        }

        return new ItemStack(ItemInit.BITCOIN.get());
    }
}
