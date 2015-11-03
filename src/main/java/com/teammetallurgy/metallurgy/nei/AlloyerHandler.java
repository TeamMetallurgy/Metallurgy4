package com.teammetallurgy.metallurgy.nei;

import codechicken.nei.NEIClientUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.recipe.IUsageHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;

import com.teammetallurgy.metallurgy.machines.alloyer.GUIAlloyer;
import com.teammetallurgy.metallurgy.recipes.AlloyerRecipes;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by freyja
 */
public class AlloyerHandler extends TemplateRecipeHandler
{

    @Override public String getGuiTexture()
    {
        return "metallurgy:textures/gui/nei_alloyer.png";
    }

    @Override
    public void drawExtras(int recipe)
    {
        drawProgressBar(62, 22, 176, 14, 50, 24, 48, 1);
    }

    @Override public String getRecipeName()
    {
        return NEIClientUtils.translate("recipe.metallurgy.alloyer");
    }

    @Override public Class<? extends GuiContainer> getGuiClass()
    {
        return GUIAlloyer.class;
    }

    @Override public void loadCraftingRecipes(ItemStack output)
    {
        ArrayList<AlloyerRecipes.AlloyRecipe> recipesFor = AlloyerRecipes.getInstance().getRecipesFor(output);

        for (AlloyerRecipes.AlloyRecipe recipe: recipesFor)
        {
            NEIRecipe neiRecipe = new NEIRecipe(recipe.getIngredients(), recipe.getCraftingResult(), output);
            arecipes.add(neiRecipe);
        }
    }

    @Override public void loadUsageRecipes(ItemStack ingredient)
    {
        ArrayList<AlloyerRecipes.AlloyRecipe> recipesFor = AlloyerRecipes.getInstance().getRecipesUsing(ingredient);

        for (AlloyerRecipes.AlloyRecipe recipe: recipesFor)
        {
            NEIRecipe neiRecipe = new NEIRecipe(recipe.getIngredients(), recipe.getCraftingResult(), ingredient);
            arecipes.add(neiRecipe);
        }
    }

    private class NEIRecipe extends CachedRecipe
    {
        public ArrayList<PositionedStack> ingredients;
        public PositionedStack result;

        public NEIRecipe(ItemStack[] inputs, ItemStack craftingResult, ItemStack ingredient)
        {
            result = new PositionedStack(craftingResult, 75, 46);
            ingredients = new ArrayList<PositionedStack>();
            setIngredients(inputs, craftingResult);
        }

        @Override public List<PositionedStack> getIngredients()
        {
            return getCycledIngredients(AlloyerHandler.this.cycleticks / 20, ingredients);
        }

        private void setIngredients(ItemStack[] inputs, ItemStack output)
        {
            PositionedStack stack = new PositionedStack(inputs[0], 57, 1);
            stack.setMaxSize(1);
            ingredients.add(stack);

            if(inputs.length > 1 && inputs[1] != null)
            {
                stack = new PositionedStack(inputs[1], 93, 1);
                stack.setMaxSize(1);
                ingredients.add(stack);
            }
        }


        @Override public PositionedStack getResult()
        {
            return result;
        }
    }
}
