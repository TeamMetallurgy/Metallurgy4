package com.teammetallurgy.metallurgy.tools;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSword;

import com.teammetallurgy.metallurgy.Metallurgy;

public class Sword extends ItemSword
{

    public Sword(int id, EnumToolMaterial toolMaterial, String unlocalizedName, String texture)
    {
        super(id, toolMaterial);
        this.setTextureName(texture);
        this.setUnlocalizedName(Metallurgy.MODID.toLowerCase() + "." + unlocalizedName);
        this.setCreativeTab(Metallurgy.instance.creativeTabTools);
    }

}
