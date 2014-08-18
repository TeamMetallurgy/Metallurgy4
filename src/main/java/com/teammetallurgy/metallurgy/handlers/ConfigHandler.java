package com.teammetallurgy.metallurgy.handlers;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler
{

    private static Configuration configuration;

    public static boolean generates(String name)
    {
        boolean b = ConfigHandler.configuration.get("Generators", name, true).getBoolean(true);

        ConfigHandler.saveChanges();

        return b;
    }

    private static boolean getBoolean(String categories, String key, boolean defaultValue)
    {
        boolean b = ConfigHandler.configuration.get(categories, key, defaultValue).getBoolean(defaultValue);

        ConfigHandler.saveChanges();

        return b;
    }

    private static String getName(String categories, String key, String defaultValue)
    {
        String string = ConfigHandler.configuration.get(categories, key, defaultValue).toString();

        ConfigHandler.saveChanges();

        return string;
    }

    /**
     * Gets ore generation dimension information
     * 
     * @param oreTag
     *            Ore configuration tag, this will be use to create sub
     *            category under generation category.
     * @param defaultDimensions
     *            Default dimensions information for ore generation
     * @return
     *         dimensions ore generation information from configuration file
     */
    public static String getOreGenerationDimInfo(String oreTag, String defaultDimensions)
    {
        String dimInfo = ConfigHandler.configuration.get("Generators." + oreTag, "dimensions", defaultDimensions).getString();

        return dimInfo;
    }

    /**
     * Gets ore generation information from configuration file.
     * 
     * @param oreTag
     *            Ore configuration tag, this will be use to create sub
     *            category under generation category.
     * @param defaultInfo
     *            Integer array with the default info.
     * @return
     *         Integer array with generation info from the configuration file
     *         with the following: <br />
     *         0: Veins per Chunk, 1: Ores per vein, 2: Minimum Y Level, <br/>
     *         3: Maximum Y Level, 4: Chunk chance.
     */
    public static int[] getOreGenerationInformation(String oreTag, int[] defaultInfo)
    {

        int veinsPerChunk = ConfigHandler.configuration.get("Generators." + oreTag, "veins_per_chunk", defaultInfo[0]).getInt(defaultInfo[0]);
        int oresPreVein = ConfigHandler.configuration.get("Generators." + oreTag, "ores_per_vein", defaultInfo[1]).getInt(defaultInfo[1]);
        int minYLevel = ConfigHandler.configuration.get("Generators." + oreTag, "min_Y_level", defaultInfo[2]).getInt(defaultInfo[2]);
        int maxYLevel = ConfigHandler.configuration.get("Generators." + oreTag, "max_Y_level", defaultInfo[3]).getInt(defaultInfo[3]);
        int chunkChance = ConfigHandler.configuration.get("Generators." + oreTag, "chunk_chance", defaultInfo[4]).getInt(defaultInfo[4]);

        int[] generationInfo = { veinsPerChunk, oresPreVein, minYLevel, maxYLevel, chunkChance };

        return generationInfo;
    }

    public static boolean itemEnabled(String itemName)
    {
        boolean b = ConfigHandler.configuration.get("Items", itemName, true).getBoolean(true);

        ConfigHandler.saveChanges();
        return b;
    }

    public static boolean regen()
    {
        return ConfigHandler.getBoolean("World_Regen", "regenOres", false);
    }

    public static String regenKey()
    {
        return ConfigHandler.getName("World_Regen", "regen_key", "DEFAULT");
    }

    private static void saveChanges()
    {

        if (ConfigHandler.configuration.hasChanged())
        {
            ConfigHandler.configuration.save();
        }
    }

    public static boolean setEnabled(String setName)
    {
        boolean b = ConfigHandler.configuration.get("Sets", setName, true).getBoolean(true);

        ConfigHandler.saveChanges();

        return b;
    }

    public static void setFile(File file)
    {
        ConfigHandler.configuration = new Configuration(file);

        ConfigHandler.configuration.load();

        ConfigHandler.saveChanges();
    }

}
