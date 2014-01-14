package com.teammetallurgy.metallurgy.recipes;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class AlloyerRecipes
{

    private static AlloyerRecipes instance = new AlloyerRecipes();

    public static AlloyerRecipes getInstance()
    {
        return instance;
    }

    private ArrayList<AlloyRecipe> recipes = new ArrayList<AlloyRecipe>();

    public ItemStack getAlloyResult(ItemStack itemStack, ItemStack otherItemStack)
    {
        for (int j = 0; j < recipes.size(); ++j)
        {
            AlloyRecipe irecipe = recipes.get(j);

            if (irecipe.matches(itemStack, otherItemStack)) { return irecipe.getCraftingResult(); }
        }

        return null;
    }

    public void addRecipe(ItemStack itemStack, ItemStack otherItemStack, ItemStack output)
    {
        recipes.add(new AlloyRecipe(itemStack, otherItemStack, output));
    }

    public boolean hasUsage(ItemStack itemStack)
    {
        for (int j = 0; j < recipes.size(); ++j)
        {
            AlloyRecipe irecipe = recipes.get(j);

            if (irecipe.uses(itemStack)) { return true; }
        }
        return false;
    }

}
