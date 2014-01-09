package com.teammetallurgy.metallurgy.handlers;

import java.io.File;

import net.minecraftforge.common.Configuration;

public class ConfigHandler
{

    private static Configuration configuration;

    public static void setFile(File file)
    {
        configuration = new Configuration(file);
        
        configuration.load();
        
        saveChanges();
    }

    private static void saveChanges()
    {

        if (configuration.hasChanged())
        {
            configuration.save();
        }
    }

    public static int getBlock(String blockName,int defaultid)
    {
        int id = configuration.getBlock(blockName, defaultid).getInt();

        saveChanges();
        
        return id;
    }

}
