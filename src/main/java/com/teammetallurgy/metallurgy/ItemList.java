package com.teammetallurgy.metallurgy;

import net.minecraft.item.Item;

import com.teammetallurgy.metallurgy.items.ItemDrawer;
import com.teammetallurgy.metallurgy.items.ItemFeritilizer;
import com.teammetallurgy.metallurgy.metals.VanillaMetals;
import com.teammetallurgy.metallurgycore.handlers.ConfigHandler;

import cpw.mods.fml.common.registry.GameRegistry;

public class ItemList
{
    public static Item drawer;
    public static Item fertilizer; 

    public static void init()
    {
        String itemDrawerName = "metallurgy.drawer";

        if (ConfigHandler.itemEnabled(itemDrawerName))
        {

            ItemList.drawer = new ItemDrawer().setUnlocalizedName(itemDrawerName);

            ItemList.registerItem(ItemList.drawer, itemDrawerName);
        }

        VanillaMetals.initItems();
        
        if (ConfigHandler.itemEnabled("fertilizer"))
        {
            ItemList.fertilizer = new ItemFeritilizer();
            ItemList.registerItem(ItemList.fertilizer, "fertilizer");
        }

    }

    public static void registerItem(Item item, String itemName)
    {
        GameRegistry.registerItem(item, itemName);
    }
}
