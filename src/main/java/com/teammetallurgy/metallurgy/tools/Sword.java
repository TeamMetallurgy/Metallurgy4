package com.teammetallurgy.metallurgy.tools;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;

import com.teammetallurgy.metallurgy.Metallurgy;

public class Sword extends ItemSword
{
    @Deprecated
    public Sword(int id, Item.ToolMaterial toolMaterial, String unlocalizedName, String texture)
    {
        this(toolMaterial, unlocalizedName, texture);
    }

    public Sword(ToolMaterial toolMaterial, String unlocalizedName, String texture)
    {
        super(toolMaterial);
        this.setTextureName(texture);
        this.setUnlocalizedName(Metallurgy.MODID.toLowerCase() + "." + unlocalizedName);
        this.setCreativeTab(Metallurgy.instance.creativeTabTools);
    }

}
