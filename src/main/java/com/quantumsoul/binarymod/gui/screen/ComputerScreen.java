package com.quantumsoul.binarymod.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.init.NetworkInit;
import com.quantumsoul.binarymod.network.ComputerPacket;
import com.quantumsoul.binarymod.tileentity.container.ComputerContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ComputerScreen extends ContainerScreen<ComputerContainer>
{
    private final ResourceLocation buttonTexture = new ResourceLocation(BinaryMod.MOD_ID, "textures/gui/widgets.png");
    private final ResourceLocation texture;
    private final String newTitle;

    public ComputerScreen(ComputerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
    {
        super(screenContainer, inv, titleIn);
        this.newTitle = I18n.format("gui.binarymod.computer_" + this.getContainer().getState().name().toLowerCase());
        this.texture = new ResourceLocation(BinaryMod.MOD_ID, "textures/gui/container/computer_" + this.getContainer().getState().name().toLowerCase() + ".png");

        switch (screenContainer.getState())
        {
            case SD:
                this.xSize = 176;
                this.ySize = 152 + 17 * getContainer().getSDOrder();
                break;

            case BATTERY:
                this.xSize = 131;
                this.ySize = 117;
                break;

            case DARK_NET:

                break;

            default:
                this.xSize = 176;
                this.ySize = 177;
                break;
        }
    }

    @Override
    protected void init()
    {
        super.init();
        switch (container.getState())
        {
            case SD:
                addButton(new UnloadButton(guiLeft + 68, guiTop + 17));
                break;

            case BATTERY:
                addButton(new UnloadButton(guiLeft + 45, guiTop + 17));
                break;

            default:
                addButton(new LoadButton(guiLeft + 120, guiTop + 39, 36));
                break;
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.notFrozenToolTip(mouseX, mouseY);
    }

    protected void notFrozenToolTip(int x, int y)
    {
        if (minecraft.player.inventory.getItemStack().isEmpty() && hoveredSlot != null && hoveredSlot.getHasStack() && hoveredSlot.canTakeStack(minecraft.player))
            this.renderTooltip(hoveredSlot.getStack(), x, y);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        font.drawString(newTitle, (xSize - font.getStringWidth(newTitle)) / 2.0F, 5, 0x164C00);

        for(Widget b: buttons)
            if(b.isHovered())
                b.renderToolTip(47, 20);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(texture);

        switch(this.container.getState())
        {
            case SD:
                this.blit(guiLeft, guiTop, 0, 0, this.xSize, 58);
                int k = 17 * container.getSDOrder();
                this.blit(guiLeft, guiTop + 58, 0, 109 - k, this.xSize, 94 + k);
                break;

            default:
                this.blit(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private class LoadButton extends AbstractButton
    {
        public LoadButton(int xIn, int yIn, int widthIn)
        {
            super(xIn, yIn, widthIn, 20, I18n.format("gui.binarymod.computer_load"));
        }

        @Override
        public void renderButton(int p1, int p2, float p3)
        {
            Minecraft minecraft = Minecraft.getInstance();

            FontRenderer fontrenderer = minecraft.fontRenderer;
            String s = getMessage();
            int strSize = fontrenderer.getStringWidth(s);
            this.width = Math.max(strSize + 5, this.width);

            minecraft.getTextureManager().bindTexture(buttonTexture);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);

            int i = this.getYImage(this.isHovered());
            this.blit(this.x, this.y, 0, 44 + i * 20, this.width / 2, this.height);
            this.blit(this.x + this.width / 2, this.y, 200 - this.width / 2, 44 + i * 20, this.width / 2, this.height);
            this.renderBg(minecraft, p1, p2);

            int color = isHovered() ? 0x000000 : 0x00FF00;
            fontrenderer.drawString(s, this.x + (this.width - strSize) / 2.0F, this.y + (this.height - 8) / 2.0F, color);
        }

        @Override
        public void onPress()
        {
            container.load(true);
            NetworkInit.CHANNEL.sendToServer(new ComputerPacket(container.getPos(), true));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private class UnloadButton extends AbstractButton
    {
        public UnloadButton(int xIn, int yIn)
        {
            super(xIn, yIn, 18, 18, "");
        }

        @Override
        public void render(int xMouse, int yMouse, float p3)
        {
            isHovered = x <= xMouse && xMouse < x + width && y <= yMouse && yMouse < y + height;

            Minecraft.getInstance().getTextureManager().bindTexture(buttonTexture);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
            this.blit(this.x, this.y, isHovered() ? this.width : 0, 0, this.width, this.height);
        }

        @Override
        public void renderToolTip(int x, int y)
        {
            ComputerScreen.this.renderTooltip(I18n.format("gui.binarymod.computer_unload"), x, y);
        }

        @Override
        public void onPress()
        {
            container.load(false);
            NetworkInit.CHANNEL.sendToServer(new ComputerPacket(container.getPos(), false));
        }
    }
}
