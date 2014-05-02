package com.teammetallurgy.metallurgy.nei;

import codechicken.nei.recipe.IUsageHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;

/**
 * Created by freyja
 */
public class ForgeHandler extends TemplateRecipeHandler
{
    @Override public String getGuiTexture()
    {
        return "metallurgy:textures/gui/Forge.png";
    }

    @Override public String getRecipeName()
    {
        return "Forge Recipe";
    }
}
