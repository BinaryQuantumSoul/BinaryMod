package com.quantumsoul.binarymod.util;

import net.minecraft.client.resources.I18n;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.function.Function;

public class MachineUtils
{
    public static final LevelInfo L_FEEDER = new LevelInfo(1, level -> 0F, "", f -> f);
    public static final LevelInfo L_HEALER = new LevelInfo(3, level -> (float) (2.5F * Math.pow(2, level)), "healer", Float::intValue);
    public static final LevelInfo L_REPAIRER = new LevelInfo(3, level -> (float) (64F * Math.pow(4, level)), "repairer", Float::intValue);
    public static final LevelInfo L_SHOOTER = new LevelInfo(3, level -> (float) (40F * Math.pow(2, -level)), "shooter", f -> f / 20F);
    public static final LevelInfo L_BITCOIN = new LevelInfo(3, level -> (float) (0.5F * Math.pow(7, level)), "bitcoin", f -> f);

    public static class LevelInfo
    {
        private final int maxLevel;
        private final float[] values;
        private final String local;
        private final Object[] textValues;

        public final IntegerProperty level;

        public LevelInfo(int maxLevel, Function<Integer, Float> builder, String local, Function<Float, Object> toText)
        {
            this.maxLevel = maxLevel;

            this.values = new float[maxLevel + 1];
            for (int i = 0; i <= maxLevel; i++)
                this.values[i] = builder.apply(i);

            this.local = local;

            this.textValues = new Object[maxLevel + 1];
            for (int i = 0; i <= maxLevel; i++)
                this.textValues[i] = toText.apply(this.values[i]);

            this.level = IntegerProperty.create("level", 0, maxLevel);
        }

        public float get(int level)
        {
            if (level >= values.length)
                throw new IllegalArgumentException("Required level out of bounds");

            return values[level];
        }

        public int getMax()
        {
            return maxLevel;
        }

        public ITextComponent formatLevel(int level)
        {
            return new StringTextComponent(TextFormatting.GRAY + getFormattedLevel(level, maxLevel));
        }

        public ITextComponent formatInfo(int level)
        {
            return !local.isEmpty() ? new StringTextComponent(TextFormatting.GRAY + capitalize(getInfo(level))) : null;
        }

        public String getInfo(int level)
        {
            return getFormattedInfo(local, textValues[level]);
        }

        public String capitalize(String s)
        {
            return Character.toUpperCase(s.charAt(0)) + s.substring(1);
        }
    }

    public static String getFormattedLevel(int level, int maxLevel)
    {
        return I18n.format("machine.binarymod.level", level + 1, maxLevel + 1);
    }

    public static String getFormattedInfo(String local, Object value)
    {
        return I18n.format("jei.binarymod." + local, value);
    }
}
