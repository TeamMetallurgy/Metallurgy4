package com.teammetallurgy.metallurgy.tools;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemHoe;

import com.teammetallurgy.metallurgy.Metallurgy;

public class Hoe extends ItemHoe
{

    public Hoe(int id, EnumToolMaterial toolMaterial, String unlocalizedName, String texture)
    {
        super(id, toolMaterial);
        this.setTextureName(texture);
        this.setUnlocalizedName(Metallurgy.MODID.toLowerCase() + "." + unlocalizedName);
        this.setCreativeTab(Metallurgy.instance.creativeTabTools);
    }

}
