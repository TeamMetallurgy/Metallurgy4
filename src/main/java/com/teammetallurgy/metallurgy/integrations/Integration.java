package com.teammetallurgy.metallurgy.integrations;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Integration
{
    public static void preinit(FMLPreInitializationEvent event)
    {
        if (Loader.isModLoaded("UndergroundBiomes"))
        {
            IntegrationUBC.init();
        }
    }

    public static void init(FMLInitializationEvent event)
    {

    }

    public static void postinit(FMLPostInitializationEvent event)
    {

    }
}
