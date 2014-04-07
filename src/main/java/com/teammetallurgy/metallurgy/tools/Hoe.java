package com.teammetallurgy.metallurgy.tools;

import net.minecraft.item.ItemHoe;

import com.teammetallurgy.metallurgy.Metallurgy;

public class Hoe extends ItemHoe
{

    public Hoe(ToolMaterial toolMaterial, String unlocalizedName, String texture)
    {
        super(toolMaterial);
        this.setTextureName(texture);
        this.setUnlocalizedName(Metallurgy.MODID.toLowerCase() + "." + unlocalizedName);
        this.setCreativeTab(Metallurgy.instance.creativeTabTools);
    }

}
