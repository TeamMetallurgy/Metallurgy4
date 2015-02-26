package com.teammetallurgy.metallurgy.networking;

import com.teammetallurgy.metallurgy.handlers.FuelHandler;
import com.teammetallurgy.metallurgy.handlers.WorldTickerMetallurgy;
import com.teammetallurgy.metallurgy.lib.Configs;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{

    public void injectZipAsResource(String zipDir)
    {
        // TODO Auto-generated method stub

    }

    public void registerTickHandlers()
    {
        if (Configs.regen)
        {
            FMLCommonHandler.instance().bus().register(new WorldTickerMetallurgy());
        }
    }

    public void registerBlockRenderers()
    {

    }

    public void registerEntityRenderers()
    {

    }

    public void registerFuelHandlers()
    {
        GameRegistry.registerFuelHandler(new FuelHandler());
    }

}
