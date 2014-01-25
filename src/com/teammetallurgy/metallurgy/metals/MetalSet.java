package com.teammetallurgy.metallurgy.metals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;

import javax.annotation.Resource;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.teammetallurgy.metallurgy.Metallurgy;
import com.teammetallurgy.metallurgy.handlers.ConfigHandler;
import com.teammetallurgy.metallurgy.recipes.AlloyerRecipes;
import com.teammetallurgy.metallurgy.world.WorldGenMetals;

import cpw.mods.fml.common.registry.GameRegistry;

public class MetalSet
{
    private String name;
    private Metal[] metals = null;
    
    private Metal defaultInfo;
    private String setTag;
    
    private int oreId = 0;
    private int blockId = 0;
    private int brickId = 0;
    private int dustId = 0;
    private int ingotId = 0;

    public MetalSet(String setName)
    {
        this.name = setName;
        this.setTag = name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public void load()
    {
        String path = "assets/metallurgy/data/";

        URL resource = Resources.getResource(path + name + ".json");

        Reader reader = null;
        try
        {
            InputStream dataStream = resource.openStream();
            reader = new InputStreamReader(dataStream, "UTF-8");

        }
        catch (IOException e)
        {
            e.getLocalizedMessage();
            e.printStackTrace();
        }

        metals = new Gson().fromJson(reader, Metal[].class);
        
        // Getting Default IDs
        defaultInfo = getDefaultInfo();
        
        // Getting Block IDs
        oreId = getSetConfigBlockId("ore");
        blockId =  getSetConfigBlockId("block");
        brickId = getSetConfigBlockId("brick");
        
        // Getting Item IDs
        dustId = getSetConfigItemId("dust");
        ingotId = getSetConfigItemId("ingot");
        
        for (Metal metal : metals)
        {
            
            if (metal.type == Metal.MetalType.Default)
            {
                continue;
            }
            
            MetalBlock ore;
            MetalBlock block;
            MetalBlock brick;
            MetalItem dust;
            MetalItem ingot;

            String texture = metal.getName().replace(" ", "_");
            texture = Metallurgy.MODID + ":" + name + "/" + texture.toLowerCase();

            String tag = metal.getName().replace(" ", "");
            // tag = tag.substring(0,1) + tag.substring(1);

            int metaId = metal.ids.get("meta");

            if (metal.ids.get("ore") != null)
            {
                String identifier = "ore";
                
                if (oreId == 0)
                {
                    oreId = ConfigHandler.getBlock(identifier + setTag, metal.ids.get(identifier));
                }
                
                ore = createBlock(oreId, metaId, metal.blockLvl, tag, texture, identifier);
                ore.addSubBlock(metaId, metal.getName(), 0, texture + "_" + identifier);

                if (ConfigHandler.generates(tag))
                {
                    WorldGenMetals worldGen = new WorldGenMetals(oreId, metaId, metal.generation, metal.dimensions);

                    GameRegistry.registerWorldGenerator(worldGen);
                }
            }

            if (metal.ids.get("block") != null)
            {
                String identifier = "block";
                
                if (blockId == 0)
                {
                    blockId = ConfigHandler.getBlock(identifier + setTag, metal.ids.get(identifier));
                }

                block = createBlock(blockId, metaId, metal.blockLvl, tag, texture, identifier);
                block.addSubBlock(metaId, metal.getName(), 1, texture + "_" + identifier);
            }

            if (metal.ids.get("brick") != null)
            {
                String identifier = "brick";
                
                if (brickId == 0)
                {
                    brickId = ConfigHandler.getBlock(identifier + setTag, metal.ids.get(identifier));
                }

                brick = createBlock(brickId, metaId, metal.blockLvl, tag, texture, identifier);
                brick.addSubBlock(metaId, metal.getName(), 2, texture + "_" + identifier);
            }

            if (metal.ids.get("dust") != null)
            {
                String identifier = "dust";

                if (dustId == 0)
                {
                    dustId = ConfigHandler.getItem(identifier + setTag, metal.ids.get(identifier));
                }

                dust = getMetalItem(dustId);
                dust.addSubItem(metaId, metal.getName(), 0, texture + "_" + identifier);

                registerItem(dust, tag, metaId, identifier);

            }

            if (metal.ids.get("ingot") != null)
            {
                String identifier = "ingot";

                if (ingotId == 0)
                {
                    ingotId = ConfigHandler.getItem(identifier + setTag, metal.ids.get(identifier));
                }

                ingot = getMetalItem(ingotId);
                ingot.addSubItem(metaId, metal.getName(), 1, texture + "_" + identifier);

                registerItem(ingot, tag, metaId, identifier);
            }

            if (metal.alloyRecipe != null && metal.alloyRecipe.length == 2)
            {
                String ore1 = metal.alloyRecipe[0];
                String ore2 = metal.alloyRecipe[1];

                ore1.replace(" ", "");
                ore2.replace(" ", "");

                ore1 = "dust" + ore1;
                ore2 = "dust" + ore2;

                List<ItemStack> retList = OreDictionary.getOres(ore1);
                if (retList.size() > 0)
                {
                    ItemStack itemStack = retList.get(0).copy();
                    List<ItemStack> retList2 = OreDictionary.getOres(ore2);
                    if (retList2.size() > 0)
                    {
                        ItemStack otherItemStack = retList2.get(0).copy();
                        List<ItemStack> output = OreDictionary.getOres("dust" + tag);
                        if (output.size() > 0)
                        {
                            ItemStack outputStack = output.get(0).copy();

                            outputStack.stackSize = 2;
                            AlloyerRecipes.getInstance().addRecipe(itemStack, otherItemStack, outputStack);
                        }
                    }
                }
            }
        }

    }

    private void registerItem(MetalItem item, String tag, int metaId, String intentifier)
    {
        OreDictionary.registerOre(intentifier + tag, new ItemStack(item, 1, metaId));
        if (GameRegistry.findUniqueIdentifierFor(item) == null)
        {
            GameRegistry.registerItem(item, this.name + "." + intentifier);
        }
    }

    private MetalItem getMetalItem(int id)
    {
        MetalItem metalItem;
        Item item = Item.itemsList[id + 256];
        if (item == null)
        {
            metalItem = new MetalItem(id);
            return metalItem;
        }
        else if (item instanceof MetalItem)
        {
            return (MetalItem) item;
        }
        else
        {
            throw new RuntimeException("Invalid metal item, possibly an ID conflict, Item ID: " + id);
        }

    }

    private MetalBlock getMetalBlock(int id)
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
            return (MetalBlock) block;
        }
        else
        {
            throw new RuntimeException("Invalid metal block, possibly an ID conflict, Block ID: " + id);
        }

    }
    
    private MetalBlock createBlock (int id, int meta, int harvestLvl,
            String tag, String texture, String identifier)
    {
        MetalBlock metalBlock = getMetalBlock(id);

        MinecraftForge.setBlockHarvestLevel(metalBlock, meta, "pickaxe", harvestLvl);

        OreDictionary.registerOre(identifier + tag, new ItemStack(metalBlock, 1, meta));
        
        if (GameRegistry.findUniqueIdentifierFor(metalBlock) == null)
        {
            GameRegistry.registerBlock(metalBlock, ItemMetalBlock.class, this.name + "." + identifier);
        }
        
        return metalBlock;
    }
    
    /**
     * Gets the default information from metal from JSON
     * 
     * @return
     *          Default info, if not found returns null
     */
    public Metal getDefaultInfo() {
    	
        for (Metal metal : metals)
        {
            if ( metal.type == Metal.MetalType.Default)
            {
                return metal;
            }
        }
    	
        return null;
    }
    
    /**
     * Retrieves the default MetalSet Block Id from configuration file using an identifier.
     * 
     * @param identifier
     *          Identifier, Like ore, block and brick.
     * @return
     *          Block ID, in case the default info is invalid it would return 0.
     */
    private int getSetConfigBlockId(String identifier)
    {
        if (defaultInfo.ids.get(identifier) != null)
        {
            return ConfigHandler.getBlock(identifier + setTag, defaultInfo.ids.get(identifier));
        }
        else
        {
            return 0;
        }
    }
    
    /**
     * Retrieves the default MetalSet Item Id from configuration file using an identifier.
     * 
     * @param identifier
     *          Identifier, Like dust and ingot.
     * @return
     *          Item ID, in case the default info is invalid it would return 0.
     */
    private int getSetConfigItemId(String identifier)
    {
        if (defaultInfo.ids.get(identifier) != null)
        {
            return ConfigHandler.getItem(identifier + setTag, defaultInfo.ids.get(identifier));
        }
        else
        {
            return 0;
        }
    }
}
