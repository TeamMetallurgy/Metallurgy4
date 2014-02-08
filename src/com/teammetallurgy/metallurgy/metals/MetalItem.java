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
        this.setTextureName(Metallurgy.MODID + ":metal_item_" + id);
        this.setUnlocalizedName("metal.item." + id);
        this.setCreativeTab(Metallurgy.instance.creativeTabItems);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    public void addSubItem(int meta, String name, int itemType, String texture)
    {
        this.names.put(meta, name);

        if (itemType < 0 || itemType > 2)
        {
            itemType = 0;
        }

        this.itemTypes.put(meta, itemType);

        this.textures.put(meta, texture);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIconFromDamage(int meta)
    {
        if (this.icons.containsKey(meta))
        {
            return this.icons.get(meta);
        }
        else
        {
            return this.itemIcon;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubItems(int id, CreativeTabs tab, List list)
    {
        for (Map.Entry<Integer, String> name : this.names.entrySet())
        {
            list.add(new ItemStack(id, 1, name.getKey()));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        int meta = itemStack.getItemDamage();

        if (this.names.get(meta) != null)
        {
            String unlocalizedName = this.names.get(meta);
            unlocalizedName = unlocalizedName.replace(" ", ".").toLowerCase();

            String itemType = "";
            switch (this.itemTypes.get(meta))
            {
                case 0:
                    itemType = ".dust";
                    break;
                case 1:
                    itemType = ".ingot";
                    break;
                case 2:
                    // for item/drop
                    itemType = "";
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

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister register)
    {
        for (Map.Entry<Integer, String> texture : this.textures.entrySet())
        {
            int meta = texture.getKey();
            String textureName = texture.getValue();

            Icon icon = register.registerIcon(textureName);

            this.icons.put(meta, icon);
        }
    }

}
