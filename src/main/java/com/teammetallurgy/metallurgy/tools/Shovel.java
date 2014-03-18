package com.teammetallurgy.metallurgy.tools;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;

import com.teammetallurgy.metallurgy.Metallurgy;

public class Shovel extends ItemSpade
{

    @Deprecated
    public Shovel(int id, Item.ToolMaterial toolMaterial, String unlocalizedName, String texture)
    {
        this(toolMaterial, unlocalizedName, texture);
    }

    public Shovel(ToolMaterial toolMaterial, String unlocalizedName, String texture)
    {
        super(toolMaterial);
        this.setTextureName(texture);
        this.setUnlocalizedName(Metallurgy.MODID.toLowerCase() + "." + unlocalizedName);
        this.setCreativeTab(Metallurgy.instance.creativeTabTools);
    }

}
