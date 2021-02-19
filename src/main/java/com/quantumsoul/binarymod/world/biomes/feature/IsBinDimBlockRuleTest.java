package com.quantumsoul.binarymod.world.biomes.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.template.IRuleTestType;
import net.minecraft.world.gen.feature.template.RuleTest;

import java.util.Random;

import static com.quantumsoul.binarymod.util.WorldUtils.isBinDimBlock;

public class IsBinDimBlockRuleTest extends RuleTest
{
    public static final IsBinDimBlockRuleTest INSTANCE = new IsBinDimBlockRuleTest();

    @Override
    public boolean test(BlockState state, Random random)
    {
        return isBinDimBlock(state.getBlock());
    }

    @Override
    protected IRuleTestType<?> getType()
    {
        return (IRuleTestType<RuleTest>) () -> Codec.unit(INSTANCE);
    }
}
