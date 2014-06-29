package com.teammetallurgy.metallurgy.api;

public interface IMetalInfo
{
    public String getName();

    public MetalType getType();

    public int getBlockLevel();

    /**
     * Gets the tool's attack damage
     * 
     * @return
     *         The tool's attack damage, and -1 if invalid
     */
    public int getToolDamage();

    /**
     * Gets the tool's durability
     * 
     * @return
     *         The tool's durability, and -1 if invalid
     */
    public int getToolDurability();

    /**
     * Gets the tool's efficiency (speed)
     * 
     * @return
     *         The tool's efficiency , and -1 if invalid
     */
    public int getToolEfficiency();

    /**
     * Gets the tool's efficiency (speed)
     * 
     * @return
     *         The tool's efficiency , and -1 if invalid
     */
    public int getToolEncantabilty();

    /**
     * Gets the tool's harvest level
     * 
     * @return
     *         the tool's harvest level, and -1 if invalid
     */
    public int getToolHarvestLevel();

    public boolean haveTools();

    public boolean haveArmor();

    /**
     * Gets the Armor Multiplier
     * 
     * @return
     *         the armor's multiplier, and -1 if invalid.
     */
    public int getArmorMultiplier();

    /**
     * Gets the Armor Damage Reduction array
     * 
     * @return
     *         The armor's Damage Reduction array, and null if invalid.
     */
    public int[] getArmorDamageReduction();

    /**
     * Gets the Armor enchantability
     * 
     * @return
     *         the armor's enchantability, and -1 if invalid.
     */
    public int getArmorEnchantability();

    public boolean isAlloyerRequired();

    public String[] getAliases();

    public String[] getDropOreDicNames();

    /**
     * Gets Generation information
     * 
     * @return
     *         An integer array with the following: <br />
     *         0: Veins Pre Chunk, 1: ores Pre Chunk, 2: minLvl, 3:maxLvl, <br />
     *         4: Vein Chance PreChunk, 5: Vine Density
     */
    public int[] getGeneration();

    public String getDimentions();
}
