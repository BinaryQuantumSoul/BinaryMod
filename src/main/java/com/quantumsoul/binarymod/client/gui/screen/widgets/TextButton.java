package com.quantumsoul.binarymod.client.gui.screen.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.quantumsoul.binarymod.BinaryMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TextButton extends Button
{
    private final static ResourceLocation TEXTURE = new ResourceLocation(BinaryMod.MOD_ID, "textures/gui/widgets.png");
    private final int dif;

    public TextButton(int xIn, int yIn, int widthIn, String text, IPressable onPress)
    {
        super(xIn, yIn, widthIn, 20, text, onPress);

        FontRenderer fontrenderer = Minecraft.getInstance().fontRenderer;
        String s = getMessage();
        dif = Math.max(fontrenderer.getStringWidth(s) + 5, this.width) - width;
        width += dif;
        x -= dif / 2;
    }

    @Override
    public void renderButton(int p1, int p2, float p3)
    {
        Minecraft minecraft = Minecraft.getInstance();

        minecraft.getTextureManager().bindTexture(TEXTURE);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);

        int i = this.getYImage(this.isHovered());
        this.blit(this.x, this.y, 0, 44 + i * 20, this.width / 2, this.height);
        this.blit(this.x + this.width / 2, this.y, 200 - this.width / 2, 44 + i * 20, this.width / 2, this.height);
        this.renderBg(minecraft, p1, p2);

        FontRenderer fontrenderer = minecraft.fontRenderer;
        int color = isHovered() ? 0x000000 : 0x00FF00;
        String s = getMessage();
        fontrenderer.drawString(s, this.x + (this.width - fontrenderer.getStringWidth(s)) / 2.0F, this.y + (this.height - 8) / 2.0F, color);
    }
}