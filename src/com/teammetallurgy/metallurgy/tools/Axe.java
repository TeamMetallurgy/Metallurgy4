package com.teammetallurgy.metallurgy.tools;

import com.teammetallurgy.metallurgy.Metallurgy;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;

public class Axe extends ItemAxe
{

    public Axe(int id, EnumToolMaterial toolMaterial, String unlocalizedName, String texture)
    {
        super(id, toolMaterial);
        this.setTextureName(texture);
        this.setUnlocalizedName(Metallurgy.MODID.toLowerCase() + "." + unlocalizedName);
        this.setCreativeTab(Metallurgy.instance.creativeTabItems);
    }

}
