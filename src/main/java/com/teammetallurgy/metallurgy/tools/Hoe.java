package com.teammetallurgy.metallurgy.tools;

import java.util.Locale;

import net.minecraft.item.ItemHoe;

import com.teammetallurgy.metallurgy.Metallurgy;

public class Hoe extends ItemHoe
{

    public Hoe(ToolMaterial toolMaterial, String unlocalizedName, String texture)
    {
        super(toolMaterial);
        this.setTextureName(texture);
        this.setUnlocalizedName(Metallurgy.MODID.toLowerCase(Locale.US) + "." + unlocalizedName);
        this.setCreativeTab(Metallurgy.instance.creativeTabTools);
    }

}
