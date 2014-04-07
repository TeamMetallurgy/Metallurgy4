package com.teammetallurgy.metallurgy.tools;

import net.minecraft.item.ItemSword;

import com.teammetallurgy.metallurgy.Metallurgy;

public class Sword extends ItemSword
{

    public Sword(ToolMaterial toolMaterial, String unlocalizedName, String texture)
    {
        super(toolMaterial);
        this.setTextureName(texture);
        this.setUnlocalizedName(Metallurgy.MODID.toLowerCase() + "." + unlocalizedName);
        this.setCreativeTab(Metallurgy.instance.creativeTabTools);
    }

}
