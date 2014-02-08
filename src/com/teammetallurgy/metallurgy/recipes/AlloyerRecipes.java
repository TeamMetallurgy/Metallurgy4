package com.teammetallurgy.metallurgy.recipes;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import com.teammetallurgy.metallurgycore.recipes.RecipeUtils;

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

    public class AlloyRecipe
    {
        private final ItemStack baseItem;
        private final ItemStack first;
        private final ItemStack result;

        public AlloyRecipe(final ItemStack first, final ItemStack baseItem, final ItemStack result)
        {
            this.first = first;
            this.baseItem = baseItem;
            this.result = result;
        }

        public ItemStack getCraftingResult()
        {
            return result.copy();
        }

        public ItemStack[] getIngredients()
        {
            return new ItemStack[] { first, baseItem };
        }

        public boolean matches(final ItemStack first, final ItemStack second)
        {
            if (uses(first) && uses(second)) { return true; }

            if (uses(first)) { return true; }

            if (uses(second)) { return true; }

            return matchesOreDict(first, second);
        }

        private boolean matchesOreDict(final ItemStack first, final ItemStack second)
        {
            if (RecipeUtils.matchesOreDict(first) && RecipeUtils.matchesOreDict(second)) { return true; }

            if (RecipeUtils.matchesOreDict(first)) { return true; }

            if (RecipeUtils.matchesOreDict(second)) { return true; }

            return false;
        }

        public boolean uses(final ItemStack ingredient)
        {
            if (ingredient == null) { return false; }

            if (first != null && first.isItemEqual(ingredient))
            {
                return true;
            }
            else if (baseItem != null && baseItem.isItemEqual(ingredient)) { return true; }

            return false;
        }
    }
}
