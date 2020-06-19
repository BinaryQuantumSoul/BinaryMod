package com.quantumsoul.binarymod.tileentity;

import com.quantumsoul.binarymod.init.TileEntityInit;
import com.quantumsoul.binarymod.tileentity.container.BlockProgContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

import static com.quantumsoul.binarymod.item.SourceItem.getSourceItem;

public class BlockProgTileEntity extends ProgrammerTileEntity implements ITickableTileEntity
{
    private static final int DOING_TIME = 400;
    public static final int SLOT_NUMBER = 3;

    private int timer = 0;
    private boolean doing = false;
    private ItemStack result = ItemStack.EMPTY;

    public BlockProgTileEntity()
    {
        super(TileEntityInit.BLOCK_PROGRAMMER.get(), SLOT_NUMBER);
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName()
    {
        return new TranslationTextComponent("gui.binarymod.block_programmer");
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory playerInventory, @Nonnull PlayerEntity player)
    {
        return new BlockProgContainer(id, playerInventory, this);
    }

    //=================================================== PROCESS ===================================================
    @Override
    public void tick()
    {
        if (doing)
        {
            timer++;

            if (timer >= DOING_TIME)
            {
                doing = false;
                timer = 0;

                ItemStack stack = contents.getStackInSlot(2);
                if (stack.getItem() == result.getItem())
                {
                    ItemStack nextStack = stack.copy();
                    nextStack.grow(1);
                    contents.setStackInSlot(2, nextStack);
                }
                else
                    contents.setStackInSlot(2, result);

                result = ItemStack.EMPTY;
            }
        }
    }

    public void launch()
    {
        doing = true;
        ItemStack stack = contents.getStackInSlot(1).copy();
        stack.shrink(1);
        contents.setStackInSlot(1, stack);
        result = getSourceItem(contents.getStackInSlot(0));
    }

    public float getProgress()
    {
        return (float) timer / (float) DOING_TIME;
    }

    public boolean isDoing()
    {
        return doing;
    }

    public ItemStack getResult()
    {
        return result;
    }

    //=================================================== DATA ===================================================
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);

        compound.putInt("timer", timer);
        compound.putBoolean("doing", doing);
        compound.put("result", result.write(new CompoundNBT()));

        return compound;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);

        timer = compound.getInt("timer");
        doing = compound.getBoolean("doing");
        result = ItemStack.read((CompoundNBT) Objects.requireNonNull(compound.get("result")));
    }
}
