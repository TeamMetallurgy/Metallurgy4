package com.teammetallurgy.metallurgy.recipes;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeUtils
{

    public static boolean matchesOreDict(ItemStack input, ItemStack... stacks)
    {
        if (input == null) { return false; }

        final int oreID = OreDictionary.getOreID(input);

        if (oreID == -1) { return false; }

        final ArrayList<ItemStack> ores = OreDictionary.getOres(oreID);

        for (final ItemStack ore : ores)
        {
            for (final ItemStack stack : stacks)
            {
                if (stack != null && OreDictionary.itemMatches(stack, ore, true)) { return true; }
            }

        }

        return false;
    }

}
