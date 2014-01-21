package com.teammetallurgy.metallurgy;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;

import com.teammetallurgy.metallurgy.recipes.CrusherRecipes;

public class Utils
{

    public static void injectOreDictionaryRecipes()
    {
        for (String name : OreDictionary.getOreNames())
        {
            for (final ItemStack oreItem : OreDictionary.getOres(name))
            {

                String replacement = "";
                replacement = name.contains("ore") ? "ore" : replacement;
                replacement = name.contains("ingot") ? "ingot" : replacement;
                replacement = name.contains("dust") ? "dust" : replacement;

                if (replacement.equals(""))
                {
                    continue;
                }

                injectCrusherDustRecipe(oreItem, replacement, name.replace(replacement, "dust"));
                injectFurnaceDustRecipe(oreItem, replacement, name.replace(replacement, "ingot"));
            }
        }
    }

    private static void injectFurnaceDustRecipe(ItemStack oreItem, String replacement, String name)
    {
        List<ItemStack> retList = OreDictionary.getOres(name);
        if (retList.size() > 0)
        {
            ItemStack ret = retList.get(0).copy();
            FurnaceRecipes.smelting().addSmelting(oreItem.itemID, oreItem.getItemDamage(), ret, 0.7F);
        }
    }

    private static void injectCrusherDustRecipe(ItemStack oreItem, String replacement, String name)
    {
        if (replacement.equals("dust")) { return; }

        List<ItemStack> retList = OreDictionary.getOres(name);
        if (retList.size() > 0)
        {
            ItemStack ret = retList.get(0).copy();
            if (replacement.equals("ore"))
            {
                ret.stackSize = 2;
            }
            CrusherRecipes.getInstance().addRecipe(oreItem.itemID, oreItem.getItemDamage(), ret);
        }
    }

}
