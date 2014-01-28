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

    public static int getBlock(String blockName, int defaultid)
    {
        int id = configuration.getBlock(blockName, defaultid).getInt();

        saveChanges();

        return id;
    }

    public static int getItem(String itemName, Integer defaultid)
    {
        int id = configuration.getItem(itemName, defaultid).getInt();

        saveChanges();

        return id;
    }

    public static boolean generates(String name)
    {
        boolean b = configuration.get("Generators", name, true).getBoolean(true);

        saveChanges();

        return b;
    }

    public static boolean setEnabled(String setName)
    {
        boolean b = configuration.get("Sets", setName, true).getBoolean(true);

        saveChanges();

        return b;
    }

    private static String getName(String categories, String key, String defaultValue)
    {
        String string = configuration.get(categories, key, defaultValue).toString();

        saveChanges();

        return string;
    }

    private static boolean getBoolean(String categories, String key, boolean defaultValue)
    {
        boolean b = configuration.get(categories, key, defaultValue).getBoolean(defaultValue);

        saveChanges();

        return b;
    }

    public static boolean regen()
    {
        return getBoolean("World_Regen", "regenOres", false);
    }

    public static String regenKey()
    {
        return getName("World_Regen", "regen_key", "DEFAULT");
    }

}
