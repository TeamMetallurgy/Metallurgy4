package com.teammetallurgy.metallurgy.networking;

import net.minecraftforge.common.MinecraftForge;

import com.teammetallurgy.metallurgy.handlers.FuelHandler;
import com.teammetallurgy.metallurgy.handlers.WorldTickerMetallurgy;

import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{

    public void injectZipAsResource(String zipDir)
    {
        // TODO Auto-generated method stub

    }

    public void registerTickHandlers()
    {
        MinecraftForge.EVENT_BUS.register(new WorldTickerMetallurgy());
    }

    public void registerBlockRenderers()
    {

    }

    public void registerFuelHandlers()
    {
        GameRegistry.registerFuelHandler(new FuelHandler());
    }

}
