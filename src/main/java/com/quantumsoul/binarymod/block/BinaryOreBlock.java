package com.quantumsoul.binarymod.block;

import com.quantumsoul.binarymod.compat.config.OreConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

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

        if (worldIn.getRandom().nextInt(100) < OreConfig.binaryOreWhiteRabbit.get() && worldIn instanceof World)
        {
            RabbitEntity rabbit = EntityType.RABBIT.create((World)worldIn);
            rabbit.setRabbitType(1);
            rabbit.setCustomName(new TranslationTextComponent("nameTag.binarymod.white_rabbit"));
            rabbit.setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
            worldIn.addEntity(rabbit);
        }
    }
}
