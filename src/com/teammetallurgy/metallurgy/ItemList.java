package com.teammetallurgy.metallurgy;

import net.minecraft.item.Item;

import com.teammetallurgy.metallurgy.handlers.ConfigHandler;
import com.teammetallurgy.metallurgy.machines.crusher.BlockCrusher;
import com.teammetallurgy.metallurgy.machines.crusher.TileEntityCrusher;
import com.teammetallurgy.metallurgy.utils.ItemOreFinder;

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
