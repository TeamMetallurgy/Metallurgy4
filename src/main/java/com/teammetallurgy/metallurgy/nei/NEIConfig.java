package com.teammetallurgy.metallurgy.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

/**
 * Created by freyja
 */
public class NEIConfig implements IConfigureNEI
{
    @Override public void loadConfig()
    {
        API.registerUsageHandler(new AlloyerHandler());
        API.registerRecipeHandler(new AlloyerHandler());

        API.registerUsageHandler(new CrusherHandler());
        API.registerRecipeHandler(new CrusherHandler());

        API.registerUsageHandler(new ForgeHandler());
        API.registerRecipeHandler(new ForgeHandler());
    }

    @Override public String getName()
    {
        return "metallurgy_nei_plugin";
    }

    @Override public String getVersion()
    {
        return "v1.0";
    }
}
