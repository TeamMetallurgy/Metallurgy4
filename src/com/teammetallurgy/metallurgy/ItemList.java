package com.teammetallurgy.metallurgy;

import net.minecraft.item.Item;

import com.teammetallurgy.metallurgycore.handlers.ConfigHandler;
import com.teammetallurgy.metallurgycore.utils.ItemOreFinder;

import cpw.mods.fml.common.registry.GameRegistry;

public class ItemList
{
    private static Item oreFinder;

    public static void init()
    {
        String itemName = "oreFinder";
        int defaultId = 2560;

        if (ConfigHandler.itemEnabled(itemName))
        {
            int id = ConfigHandler.getItem(itemName, defaultId++);

            oreFinder = new ItemOreFinder(id).setUnlocalizedName(itemName);

            registerItem(oreFinder, itemName);
        }

    }

    private static void registerItem(Item item, String itemName)
    {
        GameRegistry.registerItem(item, itemName);
    }

}
