package com.teammetallurgy.metallurgy.metals;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;

import com.google.gson.Gson;
import com.teammetallurgy.metallurgy.Metallurgy;

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
			tag = tag.substring(0,1) + tag.substring(1);
			
			if ( metal.ids.get("ore")!=null)
			{
				
				try 
				{
					ore = getMetalBlock(metal.ids.get("ore"));
					ore.addSubBlock(metal.ids.get("oreMeta"), metal.getName(), 0,texture+"_ore" );
					MinecraftForge.setBlockHarvestLevel(ore, metal.ids.get("oreMeta"), "pickaxe", metal.blockLvl);
					if (GameRegistry.findUniqueIdentifierFor(ore) == null)
					{
						GameRegistry.registerBlock(ore,ItemMetalBlock.class,"metal.block"+metal.ids.get("ore"));
					}
					
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
					if (GameRegistry.findUniqueIdentifierFor(block) == null)
					{
						GameRegistry.registerBlock(block,ItemMetalBlock.class,"metal.block" + metal.ids.get("block"));
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
					if (GameRegistry.findUniqueIdentifierFor(brick) == null)
					{
						GameRegistry.registerBlock(brick,ItemMetalBlock.class,"metal.block" + metal.ids.get("brick"));
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
			throw new Exception("Invalid Block");
		}
		
	}
}
