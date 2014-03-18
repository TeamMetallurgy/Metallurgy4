package com.teammetallurgy.metallurgy.armor;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.metallurgy.Metallurgy;

public class ItemMetallurgyArmor extends ItemArmor
{

    private String modelTexture;

    public ItemMetallurgyArmor(int id, ItemArmor.ArmorMaterial armorMaterial, int renderIndex, int armorPart, String modelTexture)
    {
        this(armorMaterial, renderIndex, armorPart, modelTexture);
        
    }

    public ItemMetallurgyArmor(ArmorMaterial armorMaterial, int renderIndex, int armorPart, String modelTexture)
    {
        super(armorMaterial, renderIndex, armorPart);
        this.modelTexture = Metallurgy.MODID.toLowerCase() + ":" + "textures/models/armor/";
        this.modelTexture += modelTexture + "_layer_";
        this.setCreativeTab(Metallurgy.instance.creativeTabArmor);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String RenderingType)
    {
        if (this.armorType == 2)
        {
            return this.modelTexture + "2.png";
        }
        else
        {
            return this.modelTexture + "1.png";
        }
    }
}
