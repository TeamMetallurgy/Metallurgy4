package com.teammetallurgy.metallurgy.metals;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import com.google.gson.Gson;
import com.teammetallurgy.metallurgy.Metallurgy;
import com.teammetallurgy.metallurgy.world.WorldGenMetals;

import cpw.mods.fml.common.registry.GameRegistry;

public class MetalSet 
{
	private String name;
	
	public MetalSet (String setName)
	{
		this.name = setName;
	}
	
	public void load ()
	{
		String path = "/assets/metallurgy/data/";
		InputStream dataStream =
				ClassLoader.class.getResourceAsStream(path + name +".json");
		Reader reader = null;
		Metal[] metals  = null;
		try 
		{
			
			reader = new InputStreamReader(dataStream,"UTF-8");
			
		}
		catch (UnsupportedEncodingException e)
		{
			e.getLocalizedMessage();
			e.printStackTrace();
		}
		
		metals = new Gson().fromJson(reader, Metal[].class);
		
		for (Metal metal : metals) 
		{
			
			MetalBlock ore;
			MetalBlock block;
			MetalBlock brick;
			
			String texture = metal.getName().replace(" ", "_");
			texture = Metallurgy.MODID +":"+ name +"/" + texture.toLowerCase();
			
			String tag = metal.getName().replace(" ", "");
			//tag = tag.substring(0,1) + tag.substring(1);
			
			if ( metal.ids.get("ore")!=null)
			{
				
				try 
				{
					ore = getMetalBlock(metal.ids.get("ore"));
					ore.addSubBlock(metal.ids.get("oreMeta"), metal.getName(), 0,texture+"_ore" );
					MinecraftForge.setBlockHarvestLevel(ore, metal.ids.get("oreMeta"), "pickaxe", metal.blockLvl);
					OreDictionary.registerOre("ore"+tag, new ItemStack(ore,1,metal.ids.get("oreMeta")));
					if (GameRegistry.findUniqueIdentifierFor(ore) == null)
					{
						GameRegistry.registerBlock(ore,ItemMetalBlock.class, this.name  + ".ore");
					}
					
					WorldGenMetals worldGen = new WorldGenMetals(metal.ids.get("ore"),
							metal.ids.get("oreMeta"), metal.generation, metal.dimensions);
					
					GameRegistry.registerWorldGenerator(worldGen);
					
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				
			}
			
			if ( metal.ids.get("block")!=null)
			{
				
				try 
				{
					block = getMetalBlock(metal.ids.get("block"));
					block.addSubBlock(metal.ids.get("blockMeta"), metal.getName(), 1,texture+"_block" );
					MinecraftForge.setBlockHarvestLevel(block, metal.ids.get("blockMeta"), "pickaxe", metal.blockLvl);
					OreDictionary.registerOre("block"+tag, new ItemStack(block,1,metal.ids.get("blockMeta")));
					if (GameRegistry.findUniqueIdentifierFor(block) == null)
					{
						GameRegistry.registerBlock(block,ItemMetalBlock.class, this.name  + ".block");
					}
					
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				
			}
			
			if ( metal.ids.get("brick")!=null)
			{
				
				try 
				{
					brick = getMetalBlock(metal.ids.get("brick"));
					brick.addSubBlock(metal.ids.get("brickMeta"), metal.getName(), 2,texture+"_brick" );
					MinecraftForge.setBlockHarvestLevel(brick, metal.ids.get("brickMeta"), "pickaxe", metal.blockLvl);
					OreDictionary.registerOre("brick"+tag, new ItemStack(brick,1,metal.ids.get("brickMeta")));
					if (GameRegistry.findUniqueIdentifierFor(brick) == null)
					{
						GameRegistry.registerBlock(brick,ItemMetalBlock.class,this.name  + ".brick");
					}
					
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				
			}
			
		}
		
	}
	
	private MetalBlock getMetalBlock (int id) throws Exception
	{
		MetalBlock metalBlock;
		Block block = Block.blocksList[id];
		if (block == null)
		{
			metalBlock = new MetalBlock(id);
			return metalBlock;
		}
		else if (block instanceof MetalBlock)
		{
			return (MetalBlock)block;
		}
		else 
		{
			throw new Exception("Invalid Metal Block");
		}
		
	}
}
