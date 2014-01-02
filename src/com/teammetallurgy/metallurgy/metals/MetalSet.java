package com.teammetallurgy.metallurgy.metals;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;

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
			System.out.println(metal.getName());
			System.out.println(metal.type);
			System.out.println(metal.ids.get("ore") + ":" + metal.ids.get("oreMeta"));
			System.out.println(metal.blockLvl);
			if ((metal.dropRate != null) && (metal.dropName != null)) 
			{
				
				System.out.print(metal.dropName + ", Rate: ");
				
				if (metal.dropRate.length == 2)
				{
					System.out.println(metal.dropRate[0] + "-" + metal.dropRate[1]);
				} 
				else 
				{
					System.out.println();
				}
					
			}
			if (metal.alloyRecipe != null) 
			{
				System.out.println("Alloy recipe: " +  metal.alloyRecipe[0] + " + "
						+ metal.alloyRecipe[1]);
			}
			
			if (metal.equipment != null)
			{
				System.out.println(metal.equipment[0]);
			}
			
			System.out.println(metal.dimensions);
		}
	}
}
