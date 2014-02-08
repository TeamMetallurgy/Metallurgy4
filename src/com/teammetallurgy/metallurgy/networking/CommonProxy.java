package com.teammetallurgy.metallurgy.networking;

import com.teammetallurgy.metallurgy.handlers.WorldTickerMetallurgy;
import com.teammetallurgy.metallurgycore.handlers.WorldTicker;

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy
{

    public void registerTickHandlers()
    {
        TickRegistry.registerTickHandler(new WorldTickerMetallurgy(), Side.SERVER);
    }

    public void injectZipAsResource(String zipDir)
    {
        // TODO Auto-generated method stub

    }

}
