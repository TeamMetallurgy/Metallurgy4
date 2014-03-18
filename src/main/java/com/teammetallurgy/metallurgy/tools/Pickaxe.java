package com.teammetallurgy.metallurgy.tools;

import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;

import com.teammetallurgy.metallurgy.Metallurgy;

public class Pickaxe extends ItemPickaxe
{
    @Deprecated
    public Pickaxe(int id, Item.ToolMaterial toolMaterial, String unlocalizedName, String texture)
    {
        this(toolMaterial, unlocalizedName, texture);
    }

    public Pickaxe(ToolMaterial toolMaterial, String unlocalizedName, String texture)
    {
        super(toolMaterial);
        this.setTextureName(texture);
        this.setUnlocalizedName(Metallurgy.MODID.toLowerCase() + "." + unlocalizedName);
        this.setCreativeTab(Metallurgy.instance.creativeTabTools);
    }

}
