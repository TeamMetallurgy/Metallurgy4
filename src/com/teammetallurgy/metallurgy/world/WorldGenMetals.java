package com.teammetallurgy.metallurgy.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenMetals implements IWorldGenerator
{
	/** 0: Veins Pre Chunk, 1: ores Pre Chunk, 2: minLvl, 3:maxLvl, <br /> 
	 4: Vein Chance PreChunk, 5: Vine Density*/
	private int[] generation;
	private String dimensions;
	private WorldGenMinable mineable;
	
	public WorldGenMetals (int blockId, int metaId, int[] generationInfo, String dimensionsInfo)
	{
		
		generation = generationInfo;
		dimensions = dimensionsInfo;
		
		int targetId = Block.stone.blockID;
		
		if (vaildDimension(-1))
		{
			targetId = Block.netherrack.blockID;
		}
		
		if (vaildDimension(1))
		{
			targetId = Block.whiteStone.blockID;
		}
		
		mineable = new WorldGenMinable(blockId, metaId, generation[1], targetId);
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		int dimId = world.provider.dimensionId;
		
		if (!vaildDimension(dimId))
		{
			return;
		}
		
		if (random.nextInt(100) > generation[4])
		{
			return;
		}
		
		for (int i = 0; i <= this.generation[0]; i++ )
		{
			int initX = (chunkX*16) + random.nextInt(16);
			int initY = generation[2] + random.nextInt(generation[3]-generation[2]);
			int initZ = (chunkZ*16) + random.nextInt(16);
			
			mineable.generate(world, random, initX, initY, initZ);
		}
		
		
	}
	
	private boolean vaildDimension(int dimensionId)
	{
		
		boolean vaild = false;
		String[] dims = dimensions.split(" ");
		
		if (dims.length < 1) {
			return false;
		}
		
		for (String dim : dims)
		{
			if (dim.contains("-"))
			{
				String[] range = dim.split("-");
				
				if (range.length > 1)
				{
					
					if (!(range[0].compareTo("") == 0))
					{
						int low = Integer.parseInt(range[0]);
						int high = Integer.parseInt(range[1]);
						
						if ((dimensionId >= low) && (dimensionId <= high))
						{
							vaild = true;
							break;
						}
					}
					else
					{
						int canidateDim = -1 * Integer.parseInt(range[1]);
						
						if (canidateDim == dimensionId) 
						{
							vaild = true;
							break;
						}
					}
					
				}
			}
			else
			{
				int canidateDim = Integer.parseInt(dim);
				
				if (canidateDim == dimensionId) 
				{
					vaild = true;
					break;
				}
			}
		}
		
		return vaild;
	}

}
