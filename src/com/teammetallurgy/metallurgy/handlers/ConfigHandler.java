package com.teammetallurgy.metallurgy.handlers;

import java.io.File;

import net.minecraftforge.common.Configuration;

public class ConfigHandler
{

    private static Configuration configuration;

    public static void setFile(File file)
    {
        configuration = new Configuration(file);

        saveChanges();
    }

    private static void saveChanges()
    {
        configuration.load();

        if (configuration.hasChanged())
        {
            configuration.save();
        }
    }

    public static int getBlock(String blockName)
    {
        int id = configuration.getBlock(blockName, 300).getInt();

        saveChanges();
        
        return id;
    }

}
