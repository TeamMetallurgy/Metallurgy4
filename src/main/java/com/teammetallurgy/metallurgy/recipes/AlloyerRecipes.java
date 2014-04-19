package com.teammetallurgy.metallurgy.recipes;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import com.teammetallurgy.metallurgycore.recipes.RecipeUtils;

public class AlloyerRecipes
{

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
            return this.result.copy();
        }

        public ItemStack[] getIngredients()
        {
            return new ItemStack[] { this.first, this.baseItem };
        }

        public boolean matches(final ItemStack first, final ItemStack second)
        {
            if (this.uses(first) && this.uses(second)) { return true; }

            if (this.uses(first) && (second == null)) { return true; }

            if (this.uses(second) && (first == null)) { return true; }

            return this.matchesOreDict(first, second);
        }

        private boolean matchesOreDict(final ItemStack first, final ItemStack second)
        {
            if (RecipeUtils.matchesOreDict(first) && RecipeUtils.matchesOreDict(second)) { return true; }

            if (RecipeUtils.matchesOreDict(first) && (second == null)) { return true; }

            if (RecipeUtils.matchesOreDict(second) && (first == null)) { return true; }

            return false;
        }

        public boolean uses(final ItemStack ingredient)
        {
            if (ingredient == null) { return false; }

            if ((this.first != null) && this.first.isItemEqual(ingredient))
            {
                return true;
            }
            else if ((this.baseItem != null) && this.baseItem.isItemEqual(ingredient)) { return true; }

            return false;
        }
    }

    private static AlloyerRecipes instance = new AlloyerRecipes();

    public static AlloyerRecipes getInstance()
    {
        return AlloyerRecipes.instance;
    }

    private final ArrayList<AlloyRecipe> recipes = new ArrayList<AlloyRecipe>();

    public void addRecipe(ItemStack itemStack, ItemStack otherItemStack, ItemStack output)
    {
        this.recipes.add(new AlloyRecipe(itemStack, otherItemStack, output));
    }

    public ItemStack getAlloyResult(ItemStack itemStack, ItemStack otherItemStack)
    {
        for (int j = 0; j < this.recipes.size(); ++j)
        {
            AlloyRecipe irecipe = this.recipes.get(j);

            if (irecipe.matches(itemStack, otherItemStack)) { return irecipe.getCraftingResult(); }
        }

        return null;
    }

    public boolean hasUsage(ItemStack itemStack)
    {
        for (int j = 0; j < this.recipes.size(); ++j)
        {
            AlloyRecipe irecipe = this.recipes.get(j);

            if (irecipe.uses(itemStack)) { return true; }
        }
        return false;
    }
}
