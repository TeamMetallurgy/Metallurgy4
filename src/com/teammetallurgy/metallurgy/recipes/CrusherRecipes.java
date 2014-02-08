package com.teammetallurgy.metallurgy.recipes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.item.ItemStack;

public class CrusherRecipes
{
    private static CrusherRecipes instance = new CrusherRecipes();

    public static CrusherRecipes getInstance()
    {
        return CrusherRecipes.instance;
    }

    private HashMap<List<Integer>, ItemStack> metaList = new HashMap<List<Integer>, ItemStack>();

    public void addRecipe(int itemID, int itemDamage, ItemStack itemStack)
    {
        this.metaList.put(Arrays.asList(itemID, itemDamage), itemStack);
    }

    public ItemStack getCrushingResult(ItemStack itemStack)
    {
        if (itemStack == null) { return null; }
        return this.metaList.get(Arrays.asList(itemStack.itemID, itemStack.getItemDamage()));

    }

}
