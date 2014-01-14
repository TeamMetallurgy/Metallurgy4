package com.teammetallurgy.metallurgy.recipes;

import net.minecraft.item.ItemStack;

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