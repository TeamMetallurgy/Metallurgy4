package com.teammetallurgy.metallurgy.networking;

import com.teammetallurgy.metallurgy.handlers.WorldTicker;

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy
{

    public void registerTickHandlers()
    {
        TickRegistry.registerTickHandler(new WorldTicker(), Side.SERVER);
    }

}
