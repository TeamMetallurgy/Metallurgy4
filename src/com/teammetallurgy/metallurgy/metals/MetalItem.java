package com.teammetallurgy.metallurgy.metals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import com.teammetallurgy.metallurgy.Metallurgy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetalItem extends Item
{
    private HashMap<Integer, String> names = new HashMap<Integer, String>();
    private HashMap<Integer, Integer> itemTypes = new HashMap<Integer, Integer>();
    private HashMap<Integer, String> textures = new HashMap<Integer, String>();
    private HashMap<Integer, Icon> icons = new HashMap<Integer, Icon>();

    public MetalItem(int id)
    {
        super(id);
        setTextureName(Metallurgy.MODID + ":metal_item_" + id);
        setUnlocalizedName("metal.item." + id);
        setCreativeTab(Metallurgy.instance.creativeTabItems);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    public void addSubItem(int meta, String name, int itemType, String texture)
    {
        names.put(meta, name);

        if (itemType < 0 || itemType > 1)
        {
            itemType = 0;
        }

        itemTypes.put(meta, itemType);

        textures.put(meta, texture);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIconFromDamage(int meta)
    {
        if (icons.containsKey(meta))
        {
            return icons.get(meta);
        }
        else
        {
            return itemIcon;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister register)
    {
        for (Map.Entry<Integer, String> texture : textures.entrySet())
        {
            int meta = texture.getKey();
            String textureName = texture.getValue();

            Icon icon = register.registerIcon(textureName);

            icons.put(meta, icon);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubItems(int id, CreativeTabs tab, List list)
    {
        for (Map.Entry<Integer, String> name : names.entrySet())
        {
            list.add(new ItemStack(id, 1, name.getKey()));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        int meta = itemStack.getItemDamage();

        if (names.get(meta) != null)
        {
            String unlocalizedName = names.get(meta);
            unlocalizedName = unlocalizedName.replace(" ", ".").toLowerCase();

            String itemType = "";
            switch (itemTypes.get(meta))
            {
                case 0:
                    itemType = ".dust";
                    break;
                case 1:
                    itemType = ".ingot";
                    break;
            }

            String prefix = "item." + Metallurgy.MODID.toLowerCase() + ".";
            return prefix + unlocalizedName + itemType;
        }
        else
        {
            return this.getUnlocalizedName();
        }
    }

}
