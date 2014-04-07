package com.teammetallurgy.metallurgy.metals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.teammetallurgy.metallurgy.Metallurgy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetalItem extends Item
{
    private HashMap<Integer, String> names = new HashMap<Integer, String>();
    private HashMap<Integer, Integer> itemTypes = new HashMap<Integer, Integer>();
    private HashMap<Integer, String> textures = new HashMap<Integer, String>();
    private HashMap<Integer, IIcon> icons = new HashMap<Integer, IIcon>();

    public MetalItem(String postfix)
    {
        this.init(postfix);
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
    public IIcon getIconFromDamage(int meta)
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
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        for (Map.Entry<Integer, String> name : this.names.entrySet())
        {
            list.add(new ItemStack(item, 1, name.getKey()));
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

    private void init(String postfix)
    {
        this.setTextureName(Metallurgy.MODID + ":metal_item_default");
        this.setUnlocalizedName("metal.item." + postfix);
        this.setCreativeTab(Metallurgy.instance.creativeTabItems);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register)
    {
        // Default texture
        this.itemIcon = register.registerIcon(this.getIconString());

        // Sub-Items textures
        for (Map.Entry<Integer, String> texture : this.textures.entrySet())
        {
            int meta = texture.getKey();
            String textureName = texture.getValue();

            IIcon icon = register.registerIcon(textureName);

            this.icons.put(meta, icon);
        }
    }

}
