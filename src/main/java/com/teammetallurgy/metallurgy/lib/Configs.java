package com.teammetallurgy.metallurgy.lib;

import com.teammetallurgy.metallurgy.handlers.ConfigHandler;

public class Configs
{
    public static boolean enabledOreParticles = true;
    
    public static void init()
    {
        Configs.enabledOreParticles = ConfigHandler.clientEnabled("ore_particales", enabledOreParticles);
    }
}
