package com.teammetallurgy.metallurgy.nei;

import net.minecraft.item.ItemStack;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

import com.teammetallurgy.metallurgy.BlockList;
import com.teammetallurgy.metallurgy.ItemList;

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
        
        API.hideItem(new ItemStack(BlockList.tabBlock));
        API.hideItem(new ItemStack(ItemList.tabItem));
        API.hideItem(new ItemStack(ItemList.tabItem,1,1));
        API.hideItem(new ItemStack(ItemList.tabItem,1,2));
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
