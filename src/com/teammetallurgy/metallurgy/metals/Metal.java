package com.teammetallurgy.metallurgy.metals;

import java.util.HashMap;

public class Metal 
{
	public enum MetalType {Respawn,Ore,Catalyst,Alloy,Drop};
	
	private String name;
	public MetalType type;
	public HashMap <String,Integer> ids;
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
	
	public String getName () 
	{
		return name;
	}
}
