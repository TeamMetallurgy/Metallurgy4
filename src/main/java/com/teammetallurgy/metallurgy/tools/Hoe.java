package com.teammetallurgy.metallurgy.tools;

import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;

import com.teammetallurgy.metallurgy.Metallurgy;

public class Hoe extends ItemHoe
{
    @Deprecated
    public Hoe(int id, Item.ToolMaterial toolMaterial, String unlocalizedName, String texture)
    {
        this(toolMaterial, unlocalizedName, texture);
    }

    public Hoe(ToolMaterial toolMaterial, String unlocalizedName, String texture)
    {
        super(toolMaterial);
        this.setTextureName(texture);
        this.setUnlocalizedName(Metallurgy.MODID.toLowerCase() + "." + unlocalizedName);
        this.setCreativeTab(Metallurgy.instance.creativeTabTools);
    }

}
