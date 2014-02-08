package com.teammetallurgy.metallurgy.networking;

import com.teammetallurgy.metallurgy.handlers.WorldTickerMetallurgy;

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy
{

    public void injectZipAsResource(String zipDir)
    {
        // TODO Auto-generated method stub

    }

    public void registerTickHandlers()
    {
        TickRegistry.registerTickHandler(new WorldTickerMetallurgy(), Side.SERVER);
    }

}
