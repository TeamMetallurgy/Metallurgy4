package com.teammetallurgy.metallurgy.lib;

import com.teammetallurgy.metallurgy.handlers.ConfigHandler;

public class Configs
{
    public static boolean enabledOreParticles = true;
    public static boolean regen = false;
    public static String regen_key = "DEFAULT";
    
    public static void init()
    {
        Configs.enabledOreParticles = ConfigHandler.clientEnabled("ore_particales", enabledOreParticles);
        Configs.regen = ConfigHandler.regen();
        Configs.regen_key = ConfigHandler.regenKey();
    }
}
