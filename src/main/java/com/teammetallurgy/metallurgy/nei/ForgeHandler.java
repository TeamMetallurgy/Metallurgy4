package com.teammetallurgy.metallurgy.nei;

import codechicken.nei.NEIClientUtils;
import codechicken.nei.recipe.IUsageHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;

/**
 * Created by freyja
 */
public class ForgeHandler extends TemplateRecipeHandler
{
    @Override public String getGuiTexture()
    {
        return "metallurgy:textures/gui/nei_forge.png";
    }

    @Override public String getRecipeName()
    {
        return NEIClientUtils.translate("recipe.metallurgy.forge");
    }
}
