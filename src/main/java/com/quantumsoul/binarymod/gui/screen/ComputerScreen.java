package com.quantumsoul.binarymod.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.gui.screen.widgets.ScrollBar;
import com.quantumsoul.binarymod.gui.screen.widgets.TextButton;
import com.quantumsoul.binarymod.gui.screen.widgets.UnloadButton;
import com.quantumsoul.binarymod.init.NetworkInit;
import com.quantumsoul.binarymod.network.ComputerPacket;
import com.quantumsoul.binarymod.tileentity.ComputerTileEntity;
import com.quantumsoul.binarymod.tileentity.container.ComputerContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
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
                this.xSize = 176;
                this.ySize = 129;
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
            case DARK_NET:
                addButton(new ScrollBar(guiLeft + 146, guiTop + 54, 57, 10, i -> drawCenteredString(font, i.toString(), width, 58, 0xFF0000)));
            case SD:
                addButton(new UnloadButton(guiLeft + 68, guiTop + 17, this::renderTooltip, p ->
                {
                    container.load(false);
                    NetworkInit.CHANNEL.sendToServer(new ComputerPacket(container.getPos(), false));
                }));
                break;

            case BATTERY:
                addButton(new UnloadButton(guiLeft + 45, guiTop + 17, this::renderTooltip, p ->
                {
                    container.load(false);
                    NetworkInit.CHANNEL.sendToServer(new ComputerPacket(container.getPos(), false));
                }));
                break;

            default:
                addButton(new TextButton(guiLeft + 120, guiTop + 39, 36, I18n.format("gui.binarymod.computer_load"), p ->
                {
                    container.load(true);
                    NetworkInit.CHANNEL.sendToServer(new ComputerPacket(container.getPos(), true));
                }));
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
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(texture);

        if (this.container.getState() == ComputerTileEntity.ComputerState.SD)
        {
            this.blit(guiLeft, guiTop, 0, 0, this.xSize, 58);
            int k = 17 * container.getSDOrder();
            this.blit(guiLeft, guiTop + 58, 0, 109 - k, this.xSize, 94 + k);
        } else
            this.blit(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        font.drawString(newTitle, (xSize - font.getStringWidth(newTitle)) / 2.0F, 5, 0x164C00);
        if (this.container.getState() != ComputerTileEntity.ComputerState.DARK_NET)
            font.drawString(playerInventory.getName().getFormattedText(), 45, ySize - 90, 0xFCC900);

        for (Widget b : buttons)
            if (b.isHovered())
                b.renderToolTip(47, 20);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int id, double newMouseX, double newMouseY)
    {
        if (this.container.getState() != ComputerTileEntity.ComputerState.DARK_NET)
            return super.mouseDragged(mouseX, mouseY, id, newMouseX, newMouseY);
        else
            return (this.getFocused() != null && this.isDragging() && id == 0) && this.getFocused().mouseDragged(mouseX, mouseY, id, newMouseX, newMouseY);
    }
}
