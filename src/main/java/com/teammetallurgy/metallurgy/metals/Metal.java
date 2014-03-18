package com.teammetallurgy.metallurgy.metals;

import java.util.HashMap;

public class Metal
{
    public enum MetalType
    {
        Respawn, Ore, Catalyst, Alloy, Drop, Default
    };

    private String name;
    public MetalType type;
    public HashMap<String, Integer> ids;
    public int abstractorXp;
    public int blockLvl;
    // list of parent materials
    public String[] alloyRecipe;
    public String dropName;
    // 0: min, 1:max
    public int[] dropRate;
    // 0: HarvestLvl, 1:ToolDura, 2:Damage, 3:Efficiency, 4:Enchant
    // 5: Helmet, 6:Chestplate, 7:Leggings, 8:Boots, 9:ArmorDuraMultiplier
    public int[] equipment;
    // 0: Chance, 1: Amount
    public int[] dugeonLoot;
    // 0: Veins Pre Chunk, 1: ores Pre Chunk, 2: minLvl, 3:maxLvl,
    // 4: Vein Chance PreChunk, 5: Vine Density
    public int[] generation;
    public String dimensions;

    public String getName()
    {
        return this.name;
    }

    /**
     * Gets the tool's attack damage
     * 
     * @return
     *         The tool's attack damage, and -1 if invalid
     */
    public int getToolDamage()
    {
        if (this.haveTools())
        {
            return this.equipment[2];
        }
        else
        {
            return -1;
        }
    }

    /**
     * Gets the tool's durability
     * 
     * @return
     *         The tool's durability, and -1 if invalid
     */
    public int getToolDurability()
    {
        if (this.haveTools())
        {
            return this.equipment[1];
        }
        else
        {
            return -1;
        }
    }

    /**
     * Gets the tool's efficiency (speed)
     * 
     * @return
     *         The tool's efficiency , and -1 if invalid
     */
    public int getToolEfficiency()
    {
        if (this.haveTools())
        {
            return this.equipment[3];
        }
        else
        {
            return -1;
        }
    }

    /**
     * Gets the tool's efficiency (speed)
     * 
     * @return
     *         The tool's efficiency , and -1 if invalid
     */
    public int getToolEncantabilty()
    {
        if (this.haveTools())
        {
            return this.equipment[4];
        }
        else
        {
            return -1;
        }
    }

    /**
     * Gets the tool's harvest level
     * 
     * @return
     *         the tool's harvest level, and -1 if invalid
     */
    public int getToolHarvestLevel()
    {
        if (this.haveTools())
        {
            return this.equipment[0];
        }
        else
        {
            return -1;
        }
    }

    public boolean haveTools()
    {
        return this.equipment != null && this.equipment.length >= 5;
    }
    
    public boolean haveArmor()
    {
        return this.equipment != null && this.equipment.length >=10;
    }
    
    /**
     * Gets the Armor Multiplier
     * 
     * @return
     *         the armor's multiplier, and -1 if invalid.
     */
    public int getArmorMultiplier ()
    {
        if (this.haveArmor())
        {
            return this.equipment[9];
        }
        else 
        {
            return -1;
        }
    }

    /**
     * Gets the Armor Damage Reduction array
     *
     * @return
     *         The armor's Damage Reduction array, and null if invalid.
     */
    public int[] getArmorDamageReduction ()
    {
        if (this.haveArmor())
        {
            int damageReduction [] = {this.equipment[5],this.equipment[6],this.equipment[7],this.equipment[8]};
            return damageReduction;
        }
        else
        {
            return null;
        }
    }

    /**
     * Gets the Armor enchantability
     *
     * @return
     *         the armor's enchantability, and -1 if invalid.
     */
    public int getArmorEnchantability ()
    {
        if (this.haveArmor())
        {
            return this.equipment[4];
        }
        else
        {
            return -1;
        }
    }
}
