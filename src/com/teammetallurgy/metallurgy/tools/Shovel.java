package com.teammetallurgy.metallurgy.tools;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSpade;

import com.teammetallurgy.metallurgy.Metallurgy;

public class Shovel extends ItemSpade
{

    public Shovel(int id, EnumToolMaterial toolMaterial, String unlocalizedName, String texture)
    {
        super(id, toolMaterial);
        this.setTextureName(texture);
        this.setUnlocalizedName(Metallurgy.MODID.toLowerCase() + "." + unlocalizedName);
        this.setCreativeTab(Metallurgy.instance.creativeTabTools);
    }

}
