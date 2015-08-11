package com.teammetallurgy.metallurgy.nei;

import java.util.List;

import com.teammetallurgy.metallurgy.machines.abstractor.GUIAbstrator;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class AbstractorHandler extends TemplateRecipeHandler
{

    @Override
    public String getRecipeName()
    {
        return "Abstractor Recipe";
    }

    @Override
    public String getGuiTexture()
    {
        return "metallurgy:textures/gui/abstractor.png";
    }
    
    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return GUIAbstrator.class;
    }
    
    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        super.loadUsageRecipes(ingredient);
    }
    
    private class AbstractorCacheRecipe extends CachedRecipe
    {
        
        public PositionedStack result;
        public List<PositionedStack> ingredients;
        public int xpAmount;
        
        public AbstractorCacheRecipe()
        {

        }
        
        @Override
        public List<PositionedStack> getIngredients()
        {
            return ingredients;
        }
        
        @Override
        public List<PositionedStack> getOtherStacks()
        {
            return super.getOtherStacks();
        }
        
        @Override
        public PositionedStack getResult()
        {
            return result;
        }
        
    }
    

}
