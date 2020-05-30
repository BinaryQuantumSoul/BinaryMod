package com.quantumsoul.binarymod.item;

import net.minecraft.item.Item;

public class BitcoinItem extends Item
{
    private final Bitcoin btc;

    public BitcoinItem(Item.Properties properties, Bitcoin bitcoin)
    {
        super(properties);
        btc = bitcoin;
    }

    public int getValue()
    {
        return btc.n;
    }

    public enum Bitcoin
    {
        UNIT(1),
        KILO(8),
        MEGA(64),
        GIGA(512),
        TERA(4096),
        PETA(32768);

        private final int n;
        Bitcoin(int value)
        {
            n = value;
        }
    }
}
