package com.teammetallurgy.metallurgy.tools;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;

import com.teammetallurgy.metallurgy.Metallurgy;

public class Axe extends ItemAxe
{
    @Deprecated
    public Axe(int id, Item.ToolMaterial toolMaterial, String unlocalizedName, String texture)
    {
        this(toolMaterial, unlocalizedName, texture);
    }

    public Axe(ToolMaterial toolMaterial, String unlocalizedName, String texture)
    {
        super(toolMaterial);
        this.setTextureName(texture);
        this.setUnlocalizedName(Metallurgy.MODID.toLowerCase() + "." + unlocalizedName);
        this.setCreativeTab(Metallurgy.instance.creativeTabTools);
    }

}
