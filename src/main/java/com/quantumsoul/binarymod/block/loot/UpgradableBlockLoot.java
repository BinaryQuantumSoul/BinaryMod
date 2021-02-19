package com.quantumsoul.binarymod.block.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.quantumsoul.binarymod.block.UpgradableBlock;
import com.quantumsoul.binarymod.init.LootInit;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.nbt.CompoundNBT;

public class UpgradableBlockLoot extends LootFunction
{
    protected UpgradableBlockLoot(ILootCondition[] conditionsIn)
    {
        super(conditionsIn);
    }

    @Override
    protected ItemStack doApply(ItemStack stack, LootContext context)
    {
        BlockState state = context.get(LootParameters.BLOCK_STATE);

        CompoundNBT compound = new CompoundNBT();
        compound.putInt("level", state.get(((UpgradableBlock)state.getBlock()).levelInfo.level));
        stack.setTagInfo("levelInfo", compound);

        return stack;

        //todo datagen
    }

    @Override
    public LootFunctionType getFunctionType()
    {
        return LootInit.UPGRADABLE_BLOCK_LOOT_TYPE;
    }

    public static class Serializer extends LootFunction.Serializer<UpgradableBlockLoot>
    {
        @Override
        public UpgradableBlockLoot deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditionsIn)
        {
            return new UpgradableBlockLoot(conditionsIn);
        }
    }
}
