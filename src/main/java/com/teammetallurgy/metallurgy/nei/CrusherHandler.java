package com.teammetallurgy.metallurgy.nei;

import codechicken.nei.recipe.IUsageHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;

/**
 * Created by freyja
 */
public class CrusherHandler extends TemplateRecipeHandler
{
    @Override public String getGuiTexture()
    {
        return "metallurgy:textures/gui/Crusher.png";
    }

    @Override public String getRecipeName()
    {
        return "Crusher Recipe";
    }
}
