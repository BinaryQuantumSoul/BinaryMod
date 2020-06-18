package com.quantumsoul.binarymod.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.client.gui.screen.widgets.TextButton;
import com.quantumsoul.binarymod.init.NetworkInit;
import com.quantumsoul.binarymod.network.packet.CBlockProgPacket;
import com.quantumsoul.binarymod.tileentity.container.BlockProgContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.Arrays;

public class BlockProgScreen extends ContainerScreen<BlockProgContainer>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(BinaryMod.MOD_ID, "textures/gui/container/block_programmer.png");

    public BlockProgScreen(BlockProgContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
    {
        super(screenContainer, inv, titleIn);
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void init()
    {
        super.init();
        addButton(new TextButton(guiLeft + 67, guiTop + 33, 36, I18n.format("gui.binarymod.blockprog_program"), p ->
        {
            if(container.canDo())
            {
                container.tileEntity.launch();
                NetworkInit.CHANNEL.sendToServer(new CBlockProgPacket(container.tileEntity.getPos()));
                p.visible = false;
            }
        }));
        buttons.get(0).visible = !container.tileEntity.isDoing();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        this.blit(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = title.getFormattedText();
        font.drawString(s, (xSize - font.getStringWidth(s)) / 2.0F, 5, 0x164C00);
        font.drawString(playerInventory.getName().getFormattedText(), 45, ySize - 90, 0xFCC900);

        buttons.get(0).visible = !container.tileEntity.isDoing();
        if (container.tileEntity.isDoing())
            font.drawString(String.format("> Loading %d%%", (int) (container.tileEntity.getProgress() * 100F)), 51, 25, 0x4CFF00);
    }
}
