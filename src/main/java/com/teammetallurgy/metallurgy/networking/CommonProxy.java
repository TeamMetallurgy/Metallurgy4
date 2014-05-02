package com.teammetallurgy.metallurgy.networking;

import net.minecraftforge.common.MinecraftForge;

import com.teammetallurgy.metallurgy.handlers.WorldTickerMetallurgy;

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

}
