package com.teammetallurgy.metallurgy.recipes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CrusherRecipes
{
    private static CrusherRecipes instance = new CrusherRecipes();

    public static CrusherRecipes getInstance()
    {
        return instance;
    }

    private HashMap<List<Integer>, ItemStack> metaList = new HashMap<List<Integer>, ItemStack>();

    private CrusherRecipes()
    {
        for (String name : OreDictionary.getOreNames())
        {
            for (final ItemStack oreItem : OreDictionary.getOres(name))
            {

                String replacement = "";
                replacement = name.contains("ore") ? "ore" : replacement;
                replacement = name.contains("ingot") ? "ingot" : replacement;
                replacement = name.contains("item") ? "item" : replacement;
                if (name.contains("dust"))
                {
                    continue;
                }
                name = name.replace(replacement, "dust");

                final List<ItemStack> retList = OreDictionary.getOres(name);
                if (retList.size() > 0)
                {
                    ItemStack ret = retList.get(0).copy();
                    if (replacement.equals("ore"))
                    {
                        ret.stackSize = 2;
                    }
                    addRecipe(oreItem.itemID, oreItem.getItemDamage(), ret);
                }
            }
        }

    }

    private void addRecipe(int itemID, int itemDamage, ItemStack itemStack)
    {
        metaList.put(Arrays.asList(itemID, itemDamage), itemStack);
    }

    public ItemStack getCrushingResult(ItemStack itemStack)
    {
        if (itemStack == null) { return null; }
        return metaList.get(Arrays.asList(itemStack.itemID, itemStack.getItemDamage()));

    }

}
