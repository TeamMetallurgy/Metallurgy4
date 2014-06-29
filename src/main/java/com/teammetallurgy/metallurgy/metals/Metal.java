package com.teammetallurgy.metallurgy.metals;

import com.teammetallurgy.metallurgy.api.IMetalInfo;
import com.teammetallurgy.metallurgy.api.MetalType;

public class Metal implements IMetalInfo
{

    private String name;
    private String[] nameAliases;
    public MetalType type;
    public int meta;
    public int abstractorXp;
    public int blockLvl;
    // list of parent materials
    public String[] alloyRecipe;
    public boolean requireAlloyer;
    public String dropName;
    public String[] dropOreDicNames;
    // 0: min, 1:max
    public int[] dropRate;
    // 0: HarvestLvl, 1:ToolDura, 2:Damage, 3:Efficiency, 4:Enchant
    // 5: Helmet, 6:Chestplate, 7:Leggings, 8:Boots, 9:ArmorDuraMultiplier
    public int[] equipment;
    // 0: Potion, 1: Duration, 2: Amplifier,
    // 3: Receiver (0: Target, 1: Attacker. 2: Both)
    public int[] entityEffect;
    // 0: Type, 1: Red, 2: Green, 3:Blue
    public int[] particleEffect;
    // 0: Chance, 1: Amount
    public int[] dugeonLoot;
    // 0: Veins Pre Chunk, 1: ores Pre Chunk, 2: minLvl, 3:maxLvl,
    // 4: Vein Chance PreChunk, 5: Vine Density
    public int[] generation;
    public String dimensions;

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public MetalType getType()
    {
        return this.type;
    }

    @Override
    public int getBlockLevel()
    {
        return this.blockLvl;
    }

    /**
     * Gets the tool's attack damage
     * 
     * @return
     *         The tool's attack damage, and -1 if invalid
     */
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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

    @Override
    public boolean haveTools()
    {
        return this.equipment != null && this.equipment.length >= 5;
    }

    @Override
    public boolean haveArmor()
    {
        return this.equipment != null && this.equipment.length >= 10;
    }

    /**
     * Gets the Armor Multiplier
     * 
     * @return
     *         the armor's multiplier, and -1 if invalid.
     */
    public int getArmorMultiplier()
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
    @Override
    public int[] getArmorDamageReduction()
    {
        if (this.haveArmor())
        {
            int damageReduction[] = { this.equipment[5], this.equipment[6], this.equipment[7], this.equipment[8] };
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
    @Override
    public int getArmorEnchantability()
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

    public boolean haveEntityEffects()
    {
        return entityEffect != null && entityEffect.length == 4;
    }

    /**
     * Gets the effect ID for the Metal
     * 
     * @return
     *         The effect id, -1 if invalid, -2 if fire, -3 if heal.
     */
    public int getEffectId()
    {
        if (this.haveEntityEffects())
        {
            return this.entityEffect[0];
        }
        else
        {
            return -1;
        }
    }

    /**
     * Gets the effect duration in ticks
     * 
     * @return
     *         The effect duration, -1 if invalid.
     */
    public int getEffectDuration()
    {
        if (this.haveEntityEffects())
        {
            return this.entityEffect[1];
        }
        else
        {
            return -1;
        }
    }

    /**
     * Gets the effect Amplifier
     * 
     * @return
     *         The effect Amplifier, -1 if invalid.
     */
    public int getEffectAmplifier()
    {
        if (this.haveEntityEffects())
        {
            return this.entityEffect[2];
        }
        else
        {
            return -1;
        }
    }

    /**
     * Gets the effect receiver, it will be 0 for target
     * 1 for Attacker, 2 for both
     * 
     * @return
     *         The effect receiver, -1 if invalid.
     */
    public int getEffectReceiver()
    {
        if (this.haveEntityEffects())
        {
            return this.entityEffect[3];
        }
        else
        {
            return -1;
        }
    }

    public boolean haveParticles()
    {
        return (this.particleEffect != null && this.particleEffect.length >= 4);
    }

    public int getParticleType()
    {
        if (this.haveParticles())
        {
            return this.particleEffect[0];
        }
        else
        {
            return -1;
        }
    }

    public int[] getParticleColors()
    {
        if (this.haveParticles())
        {
            int[] colors = { this.particleEffect[1], this.particleEffect[2], this.particleEffect[3] };
            return colors;
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean isAlloyerRequired()
    {
        return this.requireAlloyer;
    }

    @Override
    public String[] getAliases()
    {
        return this.nameAliases;
    }

    @Override
    public String[] getDropOreDicNames()
    {
        return this.dropOreDicNames;
    }

    @Override
    public int[] getGeneration()
    {
        return this.generation;
    }

    @Override
    public String getDimentions()
    {
        return this.dimensions;
    }

}
