package com.teammetallurgy.metallurgy.tools;

import java.util.Locale;

import net.minecraft.item.ItemSpade;

import com.teammetallurgy.metallurgy.Metallurgy;

public class Shovel extends ItemSpade
{
    public Shovel(ToolMaterial toolMaterial, String unlocalizedName, String texture)
    {
        super(toolMaterial);
        this.setTextureName(texture);
        this.setUnlocalizedName(Metallurgy.MODID.toLowerCase(Locale.US) + "." + unlocalizedName);
        this.setCreativeTab(Metallurgy.instance.creativeTabTools);
    }

}
