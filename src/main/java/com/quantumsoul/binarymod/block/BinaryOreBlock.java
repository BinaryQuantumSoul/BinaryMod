package com.quantumsoul.binarymod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;

public class BinaryOreBlock extends Block
{
    public BinaryOreBlock(Block.Properties properties)
    {
        super(properties);
    }

    @Override
    public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state)
    {
        if (worldIn.isRemote())
            return;

        if (worldIn.getRandom().nextInt(10) == 0)
        {
            RabbitEntity rabbit = new RabbitEntity(EntityType.RABBIT, worldIn.getWorld());
            rabbit.setRabbitType(1);
            rabbit.setCustomName(new TranslationTextComponent("nameTag.binarymod.white_rabbit"));
            rabbit.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            worldIn.addEntity(rabbit);
        }
    }
}
