package com.quantumsoul.binarymod.client.gui.screen.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.quantumsoul.binarymod.BinaryMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class ScrollBar extends Widget
{
    private final static ResourceLocation TEXTURE = new ResourceLocation(BinaryMod.MOD_ID, "textures/gui/widgets.png");

    private final int maxValue;
    private final Consumer<Integer> apply;

    private double pos;

    public ScrollBar(int xIn, int yIn, int heightIn, int maxValue, Consumer<Integer> apply)
    {
        super(xIn, yIn, 11, heightIn, new TranslationTextComponent("gui.binarymod.scrollbar"));

        this.maxValue = maxValue;
        this.apply = apply;

        changePosition(yIn + width / 2.0D);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);

        this.blit(matrixStack, x, (int) (pos - width / 2), 0, 105, width, width);
    }

    @Override
    public void onClick(double mouseX, double mouseY)
    {
        changePosition(mouseY);
    }

    @Override
    public void onDrag(double mouseX, double mouseY, double newMouseX, double newMouseY)
    {
        changePosition(mouseY);
    }

    private void changePosition(double mouseY)
    {
        double lim = width / 2.0D;
        double newPos = MathHelper.clamp(mouseY, y + lim, y + height - lim);

        if(newPos != pos)
            apply.accept((int) (maxValue * (newPos + lim - y) / (height - width)));

        pos = newPos;
    }

    @Override
    public void onRelease(double mouseX, double mouseY)
    {
        super.playDownSound(Minecraft.getInstance().getSoundHandler());
    }

    @Override
    public boolean keyPressed(int key, int p_keyPressed_2_, int p_keyPressed_3_)
    {
        boolean flag = key == 265;
        if (flag || key == 264)
        {
            float f = flag ? -1.0F : 1.0F;
            changePosition(pos + f / maxValue * (height - width));
            return true;
        }

        return false;
    }

    @Override
    public boolean mouseScrolled(double d0, double d1, double sign)
    {
        changePosition(pos - sign / maxValue * (height - width));
        return true;
    }
}
