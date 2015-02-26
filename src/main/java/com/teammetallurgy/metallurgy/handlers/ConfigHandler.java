package com.teammetallurgy.metallurgy.handlers;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler
{

    private static Configuration configuration;

    public static boolean generates(String name)
    {
        boolean b = ConfigHandler.configuration.get("generators", name, true).getBoolean(true);

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
        String string = ConfigHandler.configuration.get(categories, key, defaultValue).getString();

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
        String dimInfo = ConfigHandler.configuration.get("generators." + oreTag, "dimensions", defaultDimensions).getString();

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

        int veinsPerChunk = ConfigHandler.configuration.get("generators." + oreTag, "veins_per_chunk", defaultInfo[0]).getInt(defaultInfo[0]);
        int oresPreVein = ConfigHandler.configuration.get("generators." + oreTag, "ores_per_vein", defaultInfo[1]).getInt(defaultInfo[1]);
        int minYLevel = ConfigHandler.configuration.get("generators." + oreTag, "min_Y_level", defaultInfo[2]).getInt(defaultInfo[2]);
        int maxYLevel = ConfigHandler.configuration.get("generators." + oreTag, "max_Y_level", defaultInfo[3]).getInt(defaultInfo[3]);
        int chunkChance = ConfigHandler.configuration.get("generators." + oreTag, "chunk_chance", defaultInfo[4]).getInt(defaultInfo[4]);

        ConfigHandler.saveChanges();

        int[] generationInfo = { veinsPerChunk, oresPreVein, minYLevel, maxYLevel, chunkChance };
        return generationInfo;
    }

    public static boolean itemEnabled(String itemName)
    {
        boolean b = ConfigHandler.configuration.get("items", itemName, true).getBoolean(true);

        ConfigHandler.saveChanges();
        return b;
    }

    public static boolean regen()
    {
        return ConfigHandler.getBoolean("world_Regen", "regen_ores", false);
    }

    public static String regenKey()
    {
        return ConfigHandler.getName("world_Regen", "regen_key", "DEFAULT");
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
        boolean b = ConfigHandler.configuration.get("sets", setName, true).getBoolean(true);

        ConfigHandler.saveChanges();

        return b;
    }

    public static void setFile(File file)
    {
        ConfigHandler.configuration = new Configuration(file);

        ConfigHandler.configuration.load();

        ConfigHandler.saveChanges();
    }

    public static boolean recipeEnabled(String recipe)
    {
        boolean enabled = ConfigHandler.configuration.get("recipes", recipe, true).getBoolean(true);

        ConfigHandler.saveChanges();

        return enabled;
    }

    public static boolean armorEnabled(String metal)
    {
        boolean enabled = ConfigHandler.configuration.get("recipes_armour", metal, true).getBoolean(true);

        ConfigHandler.saveChanges();

        return enabled;
    }

    public static boolean weaponsEnabled(String metal)
    {
        boolean enabled = ConfigHandler.configuration.get("recipes_weapons", metal, true).getBoolean(true);

        ConfigHandler.saveChanges();

        return enabled;
    }

    public static int[] toolsStats(String metal, int[] defaultStats)
    {
        int harvestLevel = ConfigHandler.configuration.get("metal_stats." + metal, "pick_harvest_level", defaultStats[0]).getInt(defaultStats[0]);
        int maxUses = ConfigHandler.configuration.get("metal_stats." + metal, "tool_durability", defaultStats[1]).getInt(defaultStats[1]);
        int efficiency = ConfigHandler.configuration.get("metal_stats." + metal, "tool_efficiency", defaultStats[2]).getInt(defaultStats[2]);
        int damage = ConfigHandler.configuration.get("metal_stats." + metal, "tool_attack_damage", defaultStats[3]).getInt(defaultStats[3]);
        int enchantability = ConfigHandler.configuration.get("metal_stats." + metal, "tool_enchantability", defaultStats[4]).getInt(defaultStats[4]);

        ConfigHandler.saveChanges();

        int[] newStats = { harvestLevel, maxUses, efficiency, damage, enchantability };
        return newStats;
    }

    public static int[] armourStats(String metal, int[] defaultStats)
    {
        int mutiplier = ConfigHandler.configuration.get("metal_stats." + metal, "armour_multiplier", defaultStats[0]).getInt(defaultStats[0]);
        int helmetDmgReduction = ConfigHandler.configuration.get("metal_stats." + metal, "helmet_reduction", defaultStats[1]).getInt(defaultStats[1]);
        int chestDmgReduction = ConfigHandler.configuration.get("metal_stats." + metal, "chestplate_reduction", defaultStats[2]).getInt(defaultStats[2]);
        int leggingsDmgReduction = ConfigHandler.configuration.get("metal_stats." + metal, "leggings_reduction", defaultStats[3]).getInt(defaultStats[3]);
        int bootDmgReduction = ConfigHandler.configuration.get("metal_stats." + metal, "boots_reduction", defaultStats[4]).getInt(defaultStats[4]);
        int enchantablilty = ConfigHandler.configuration.get("metal_stats." + metal, "armour_enchantability", defaultStats[5]).getInt(defaultStats[5]);

        ConfigHandler.saveChanges();

        int[] newStats = { mutiplier, helmetDmgReduction, chestDmgReduction, leggingsDmgReduction, bootDmgReduction, enchantablilty };
        return newStats;
    }

    public static boolean swordEffectEnabled(String metal)
    {
        boolean enabled = ConfigHandler.configuration.get("metal_stats." + metal, "sword_effects", true).getBoolean(true);

        ConfigHandler.saveChanges();

        return enabled;
    }

    public static int blockHarvestLevel(String metal, int defaultLevel)
    {
        int blockLevel = ConfigHandler.configuration.get("metal_stats." + metal, "block_harvest_level", defaultLevel).getInt(defaultLevel);

        ConfigHandler.saveChanges();

        return blockLevel;
    }

    public static boolean clientEnabled(String key, boolean defaultValue)
    {
        boolean config = ConfigHandler.getBoolean("client", key, defaultValue);

        ConfigHandler.saveChanges();

        return config;
    }

}
