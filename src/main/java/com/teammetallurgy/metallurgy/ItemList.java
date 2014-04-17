package com.teammetallurgy.metallurgy;

import net.minecraft.item.Item;

import com.teammetallurgy.metallurgy.items.ItemDrawer;
import com.teammetallurgy.metallurgycore.handlers.ConfigHandler;

import cpw.mods.fml.common.registry.GameRegistry;

public class ItemList
{
    public static Item drawer;

    public static void init()
    {
        String itemName = "metallurgy.drawer";

        if (ConfigHandler.itemEnabled(itemName))
        {

            ItemList.drawer = new ItemDrawer().setUnlocalizedName(itemName);

            ItemList.registerItem(ItemList.drawer, itemName);
        }

    }

    public static void registerItem(Item item, String itemName)
    {
        GameRegistry.registerItem(item, itemName);
    }
}
