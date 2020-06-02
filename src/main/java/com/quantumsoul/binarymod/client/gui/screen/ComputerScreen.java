package com.quantumsoul.binarymod.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.client.gui.screen.widgets.ScrollBar;
import com.quantumsoul.binarymod.client.gui.screen.widgets.TextButton;
import com.quantumsoul.binarymod.client.gui.screen.widgets.UnloadButton;
import com.quantumsoul.binarymod.init.NetworkInit;
import com.quantumsoul.binarymod.init.RecipeInit;
import com.quantumsoul.binarymod.item.SDCardItem;
import com.quantumsoul.binarymod.network.packet.BtcBuyPacket;
import com.quantumsoul.binarymod.network.packet.ComputerPacket;
import com.quantumsoul.binarymod.recipe.DarkWebRecipe;
import com.quantumsoul.binarymod.tileentity.ComputerTileEntity;
import com.quantumsoul.binarymod.tileentity.container.ComputerContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

import static com.quantumsoul.binarymod.util.BitcoinUtils.*;

@OnlyIn(Dist.CLIENT)
public class ComputerScreen extends ContainerScreen<ComputerContainer>
{
    private final ResourceLocation texture;
    private final String newTitle;
    private final List<DarkWebRecipe> recipes;
    private final DarkWebRecipe[] currents = new DarkWebRecipe[3];
    private final double money;
    private final String moneyString;
    private int index = 0;

    public ComputerScreen(ComputerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
    {
        super(screenContainer, inv, titleIn);
        this.newTitle = I18n.format("gui.binarymod.computer_" + this.getContainer().getState().name().toLowerCase());
        this.texture = new ResourceLocation(BinaryMod.MOD_ID, "textures/gui/container/computer_" + this.getContainer().getState().name().toLowerCase() + ".png");

        recipes = Minecraft.getInstance().world.getRecipeManager().getRecipes(RecipeInit.DARK_WEB, inv, inv.player.world);
        money = evaluateInventory(inv);
        moneyString = getBitcoinString(money);

        switch (getState())
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
        switch (getState())
        {
            case DARK_NET:
                addButton(new ScrollBar(guiLeft + 146, guiTop + 54, 57, recipes.size() - 3, (i) -> index = i));
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
        if (minecraft.player.inventory.getItemStack().isEmpty() && hoveredSlot != null && !(hoveredSlot.getHasStack() && hoveredSlot.getStack().getItem() instanceof SDCardItem && !hoveredSlot.canTakeStack(playerInventory.player)))
            this.renderTooltip(hoveredSlot.getStack(), x, y);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(texture);

        if (getState() == ComputerTileEntity.ComputerState.SD)
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
        if (getState() != ComputerTileEntity.ComputerState.DARK_NET)
            font.drawString(playerInventory.getName().getFormattedText(), 45, ySize - 90, 0xFCC900);
        else
            drawRecipes(index);

        for (Widget b : buttons)
            if (b.isHovered())
                b.renderToolTip(47, 20); //todo 47 20

/*        if(getState() == ComputerTileEntity.ComputerState.DARK_NET)
            for (int i = 0; i < 3; i++)
                if (isHovering(guiLeft + 30, guiTop + 53 + 22 * i, 16, 16, mouseX, mouseY))
                    renderTooltip(currents[i].getRecipeOutput(), mouseX - 150, mouseY - 40);*/
    }

    private void drawRecipes(int index)
    {
        font.drawString(moneyString, 122, 44, 0x4CFF00);

        for (int i = 0; i < 3; i++)
        {
            int y = 53 + 22 * i;
            DarkWebRecipe recipe = recipes.get(i + index);
            currents[i] = recipe;

            container.putStackInSlot(i + 1, recipe.getRecipeOutput());
            /*ItemStack result = recipe.getRecipeOutput();
            itemRenderer.renderItemAndEffectIntoGUI(result, 30, y);*/

            double price = recipe.getPrice();
            font.drawString(getBitcoinString(price), 75, y + 5, money >= price ? 0x4CFF00 : 0xFCC900);
            itemRenderer.renderItemAndEffectIntoGUI(getBitcoinStack(price), 110, y);
        }
    }

    private boolean isHovering(int x, int y, int width, int height, int mouseX, int mouseY)
    {
        return x <= mouseX && mouseX < x + width && y <= mouseY && mouseY < y + height;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int id, double newMouseX, double newMouseY)
    {
        if (getState() == ComputerTileEntity.ComputerState.DARK_NET)
            return (this.getFocused() != null && this.isDragging() && id == 0) && this.getFocused().mouseDragged(mouseX, mouseY, id, newMouseX, newMouseY);

        return super.mouseDragged(mouseX, mouseY, id, newMouseX, newMouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int partialTicks)
    {
        if (getState() == ComputerTileEntity.ComputerState.DARK_NET)
            for (int i = 0; i < 3; i++)
                if (isHovering(guiLeft + 30, guiTop + 53 + 22 * i, 16, 16, (int) mouseX, (int) mouseY) && money >= currents[i].getPrice())
                    if (buyRecipe(currents[i], container.playerInv))
                    {
                        NetworkInit.CHANNEL.sendToServer(new BtcBuyPacket(currents[i]));
                        getMinecraft().displayGuiScreen(null);
                        return true;
                    }

        return super.mouseClicked(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseScrolled(double d0, double d1, double d2)
    {
        if (getState() == ComputerTileEntity.ComputerState.DARK_NET)
            return buttons.get(0).mouseScrolled(d0, d1, d2);

        return super.mouseScrolled(d0, d1, d2);
    }

    private ComputerTileEntity.ComputerState getState()
    {
        return this.container.getState();
    }
}
