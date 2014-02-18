package com.teammetallurgy.metallurgy;

import net.minecraft.item.Item;

import com.teammetallurgy.metallurgy.items.ItemDrawer;
import com.teammetallurgy.metallurgycore.handlers.ConfigHandler;

public class ItemList
{
    private static Item drawer;

    public static void init()
    {
        String itemName = "drawer";
        int defaultId = 2560;

        if (ConfigHandler.itemEnabled(itemName))
        {
            int id = ConfigHandler.getItem(itemName, defaultId++);

            ItemList.drawer = new ItemDrawer(id).setUnlocalizedName(itemName);

            com.teammetallurgy.metallurgycore.ItemList.registerItem(ItemList.drawer, itemName);
        }

    }
}
