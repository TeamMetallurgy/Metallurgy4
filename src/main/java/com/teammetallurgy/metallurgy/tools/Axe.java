package com.teammetallurgy.metallurgy.tools;

import java.util.Locale;

import net.minecraft.item.ItemAxe;

import com.teammetallurgy.metallurgy.Metallurgy;

public class Axe extends ItemAxe
{

    public Axe(ToolMaterial toolMaterial, String unlocalizedName, String texture)
    {
        super(toolMaterial);
        this.setTextureName(texture);
        this.setUnlocalizedName(Metallurgy.MODID.toLowerCase(Locale.US) + "." + unlocalizedName);
        this.setCreativeTab(Metallurgy.instance.creativeTabTools);
    }

}
