package com.teammetallurgy.metallurgy.metals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.teammetallurgy.metallurgy.Metallurgy;
import com.teammetallurgy.metallurgy.Utils;
import com.teammetallurgy.metallurgy.handlers.ConfigHandler;
import com.teammetallurgy.metallurgy.world.WorldGenMetals;

import cpw.mods.fml.common.registry.GameRegistry;

public class MetalSet
{
    private String name;
    private Metal[] metals = null;

    private Metal defaultInfo;
    private String setTag;

    private int defaultOreId = 0;
    private int defaultBlockId = 0;
    private int defaultBrickId = 0;
    private int defaultDustId = 0;
    private int defaultIngotId = 0;
    private int defaultItemId = 0;

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
        defaultOreId = getSetConfigBlockId("ore");
        defaultBlockId = getSetConfigBlockId("block");
        defaultBrickId = getSetConfigBlockId("brick");

        // Getting Item IDs
        defaultDustId = getSetConfigItemId("dust");
        defaultIngotId = getSetConfigItemId("ingot");
        defaultItemId = getSetConfigItemId("item");

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
            MetalItem item;

            String texture = metal.getName().replace(" ", "_");
            texture = Metallurgy.MODID + ":" + name + "/" + texture.toLowerCase();

            String tag = metal.getName().replace(" ", "");
            String configTag = tag.substring(0, 1) + tag.substring(1);

            int metaId = metal.ids.get("meta");

            if (metal.ids.get("ore") != null)
            {
                String identifier = "ore";

                int oreId = defaultOreId;

                // if there is no set defaultid

                if (oreId == 0)
                {
                    oreId = ConfigHandler.getBlock(identifier + configTag, metal.ids.get(identifier));
                }

                if (metal.type != Metal.MetalType.Respawn)
                {
                    ore = createBlock(oreId, metaId, metal.blockLvl, tag, texture, identifier);

                    int itemId = defaultItemId;

                    if (metal.type == Metal.MetalType.Drop)
                    {
                        ore.addSubBlock(metaId, metal.getName(), 0, texture + "_" + identifier, itemId);
                    }
                    else
                    {
                        ore.addSubBlock(metaId, metal.getName(), 0, texture + "_" + identifier);
                    }

                }

                if (ConfigHandler.generates(tag))
                {
                    WorldGenMetals worldGen = new WorldGenMetals(oreId, metaId, metal.generation, metal.dimensions);

                    GameRegistry.registerWorldGenerator(worldGen);
                }
            }

            if (metal.ids.get("block") != null)
            {
                String identifier = "block";

                int blockId = defaultBlockId;

                if (blockId == 0)
                {
                    blockId = ConfigHandler.getBlock(identifier + configTag, metal.ids.get(identifier));
                }

                block = createBlock(blockId, metaId, metal.blockLvl, tag, texture, identifier);
                block.addSubBlock(metaId, metal.getName(), 1, texture + "_" + identifier);
            }

            if (metal.ids.get("brick") != null)
            {
                String identifier = "brick";

                int brickId = defaultBrickId;

                if (brickId == 0)
                {
                    brickId = ConfigHandler.getBlock(identifier + configTag, metal.ids.get(identifier));
                }

                brick = createBlock(brickId, metaId, metal.blockLvl, tag, texture, identifier);
                brick.addSubBlock(metaId, metal.getName(), 2, texture + "_" + identifier);
            }

            if (metal.ids.get("dust") != null && metal.type != Metal.MetalType.Drop)
            {
                String identifier = "dust";

                int dustId = defaultDustId;

                if (dustId == 0)
                {
                    dustId = ConfigHandler.getItem(identifier + configTag, metal.ids.get(identifier));
                }

                dust = getMetalItem(dustId);
                dust.addSubItem(metaId, metal.getName(), 0, texture + "_" + identifier);

                registerItem(dust, tag, metaId, identifier);

            }

            if (metal.ids.get("ingot") != null && metal.type != Metal.MetalType.Drop)
            {
                String identifier = "ingot";

                int ingotId = defaultIngotId;

                if (ingotId == 0)
                {
                    ingotId = ConfigHandler.getItem(identifier + configTag, metal.ids.get(identifier));
                }

                ingot = getMetalItem(ingotId);
                ingot.addSubItem(metaId, metal.getName(), 1, texture + "_" + identifier);

                registerItem(ingot, tag, metaId, identifier);
            }

            if (metal.ids.get("item") != null && metal.type == Metal.MetalType.Drop)
            {
                String identifier = "item";

                int itemId = defaultItemId;

                if (itemId == 0)
                {
                    itemId = ConfigHandler.getItem(identifier + configTag, metal.ids.get(identifier));
                }

                item = getMetalItem(itemId);

                // Some items have different names than the ores
                String itemName = metal.dropName;
                String itemTexture = metal.dropName.replace(" ", "_");
                itemTexture = Metallurgy.MODID + ":" + name + "/" + itemTexture.toLowerCase();

                if (itemName.compareTo("") == 0)
                {
                    itemName = metal.getName();
                    itemTexture = texture;
                }

                item.addSubItem(metaId, itemName, 2, itemTexture);

                registerItem(item, tag, metaId, identifier);

            }

            if (metal.alloyRecipe != null && metal.alloyRecipe.length == 2)
            {
                String ore1 = metal.alloyRecipe[0];
                String ore2 = metal.alloyRecipe[1];

                ore1.replace(" ", "");
                ore2.replace(" ", "");

                ore1 = "dust" + ore1;
                ore2 = "dust" + ore2;

                Utils.alloys.put(tag, new String[] { ore1, ore2 });
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

    private MetalBlock createBlock(int id, int meta, int harvestLvl, String tag, String texture, String identifier)
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
     * @return Default info, if not found returns null
     */
    public Metal getDefaultInfo()
    {

        for (Metal metal : metals)
        {
            if (metal.type == Metal.MetalType.Default) { return metal; }
        }

        return null;
    }

    /**
     * Retrieves the default MetalSet Block Id from configuration file using an
     * identifier.
     * 
     * @param identifier
     *            Identifier, Like ore, block and brick.
     * @return Block ID, in case the default info is invalid it would return 0.
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
     * Retrieves the default MetalSet Item Id from configuration file using an
     * identifier.
     * 
     * @param identifier
     *            Identifier, Like dust and ingot.
     * @return Item ID, in case the default info is invalid it would return 0.
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
