package com.teammetallurgy.metallurgy.nei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

import com.teammetallurgy.metallurgy.machines.crusher.GUICrusher;
import com.teammetallurgy.metallurgy.recipes.CrusherRecipes;

/**
 * Created by freyja
 */
public class CrusherHandler extends TemplateRecipeHandler
{
    @Override
    public String getGuiTexture()
    {
        return "metallurgy:textures/gui/nei_crusher.png";
    }

    @Override
    public void drawExtras(int recipe)
    {
        drawProgressBar(77, 22, 176, 14, 12, 24, 48, 1);
    }

    @Override
    public String getRecipeName()
    {
        return NEIClientUtils.translate("recipe.metallurgy.crusher");
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return GUICrusher.class;
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        HashMap<ItemStack, ItemStack> recipes = CrusherRecipes.getInstance().getInput(result);

        if ((recipes != null) && (!recipes.isEmpty()))
        {
            for (Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
            {
                NEICrusherRecipe neiRecipe = new NEICrusherRecipe(recipe.getKey(), recipe.getValue());
                arecipes.add(neiRecipe);
            }

        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {

        ItemStack result = CrusherRecipes.getInstance().getCrushingResult(ingredient);
        if (result != null)
        {
            NEICrusherRecipe neiRecipe = new NEICrusherRecipe(ingredient, result);
            arecipes.add(neiRecipe);
        }

    }

    private class NEICrusherRecipe extends CachedRecipe
    {

        public ArrayList<PositionedStack> ingredients;
        public PositionedStack result;

        public NEICrusherRecipe(ItemStack input, ItemStack craftingResult)
        {
            result = new PositionedStack(craftingResult, 75, 48);
            ingredients = new ArrayList<PositionedStack>();
            setIngredients(input, craftingResult);
        }

        @Override
        public List<PositionedStack> getIngredients()
        {
            return getCycledIngredients(CrusherHandler.this.cycleticks / 20, ingredients);
        }

        private void setIngredients(ItemStack input, ItemStack output)
        {
            PositionedStack stack = new PositionedStack(input, 75, 3);
            stack.setMaxSize(1);
            ingredients.add(stack);

        }

        @Override
        public PositionedStack getResult()
        {
            return result;
        }

    }
}
