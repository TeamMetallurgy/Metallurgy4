package com.teammetallurgy.metallurgy;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.teammetallurgy.metallurgy.handlers.ConfigHandler;
import com.teammetallurgy.metallurgy.items.ItemDrawer;
import com.teammetallurgy.metallurgy.items.ItemFeritilizer;
import com.teammetallurgy.metallurgy.metals.VanillaMetals;

import cpw.mods.fml.common.registry.GameRegistry;

public class ItemList
{
    public static Item drawer;
    public static Item fertilizer;
    public static boolean drawerEnabled = true;
    public static boolean fertilizerEnabled = true;

    public static void init()
    {
        VanillaMetals.initItems();

        String itemDrawerName = "drawer";

        drawerEnabled = ConfigHandler.itemEnabled(itemDrawerName);
        fertilizerEnabled = ConfigHandler.itemEnabled("fertilizer");

        if (drawerEnabled)
        {

            ItemList.drawer = new ItemDrawer().setUnlocalizedName("metallurgy." + itemDrawerName);

            ItemList.registerItem(ItemList.drawer, itemDrawerName);
        }

        if (fertilizerEnabled)
        {
            ItemList.fertilizer = new ItemFeritilizer();
            ItemList.registerItem(ItemList.fertilizer, "fertilizer");
        }

    }

    public static void addRecipes()
    {
        if (fertilizerEnabled)
        {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemList.fertilizer, 8), "dustPhosphorus", "dustSaltpeter", "dustPotash", "dustMagnesium"));
        }
    }

    public static void registerItem(Item item, String itemName)
    {
        GameRegistry.registerItem(item, itemName);
    }
}
