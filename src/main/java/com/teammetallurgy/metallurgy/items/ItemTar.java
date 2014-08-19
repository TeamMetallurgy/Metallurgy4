package com.teammetallurgy.metallurgy.items;

import com.teammetallurgy.metallurgy.Metallurgy;

import net.minecraft.item.Item;

public class ItemTar extends Item
{
    public ItemTar()
    {
        this.setTextureName("metallurgy:misc/tar");
        this.setUnlocalizedName("metallurgy.tar");
        this.setMaxStackSize(64);
        this.setCreativeTab(Metallurgy.instance.creativeTabItems);
    }
}
