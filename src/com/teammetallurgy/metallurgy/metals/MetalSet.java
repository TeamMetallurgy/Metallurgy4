package com.teammetallurgy.metallurgy.metals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import net.minecraft.block.Block;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import com.google.gson.Gson;
import com.teammetallurgy.metallurgy.Metallurgy;
import com.teammetallurgy.metallurgy.Utils;
import com.teammetallurgy.metallurgy.armor.ItemMetallurgyArmor;
import com.teammetallurgy.metallurgy.tools.Axe;
import com.teammetallurgy.metallurgy.tools.Hoe;
import com.teammetallurgy.metallurgy.tools.Pickaxe;
import com.teammetallurgy.metallurgy.tools.Shovel;
import com.teammetallurgy.metallurgy.tools.Sword;
import com.teammetallurgy.metallurgy.world.WorldGenMetals;
import com.teammetallurgy.metallurgycore.handlers.ConfigHandler;

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
        this.setTag = this.name.substring(0, 1).toUpperCase() + this.name.substring(1);
    }

    private MetalBlock createBlock(int id, int meta, int harvestLvl, String tag, String texture, String identifier)
    {
        MetalBlock metalBlock = this.getMetalBlock(id);

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

        for (Metal metal : this.metals)
        {
            if (metal.type == Metal.MetalType.Default) { return metal; }
        }

        return null;
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
        if (this.defaultInfo.ids.get(identifier) != null)
        {
            return ConfigHandler.getBlock(identifier + this.setTag, this.defaultInfo.ids.get(identifier));
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
        if (this.defaultInfo.ids.get(identifier) != null)
        {
            return ConfigHandler.getItem(identifier + this.setTag, this.defaultInfo.ids.get(identifier));
        }
        else
        {
            return 0;
        }
    }

    public void load(InputStream inputStream)

    {
        Reader reader = null;
        try
        {
            reader = new InputStreamReader(inputStream, "UTF-8");

        }
        catch (IOException e)
        {
            e.getLocalizedMessage();
            e.printStackTrace();
        }

        this.metals = new Gson().fromJson(reader, Metal[].class);

        // Getting Default IDs
        this.defaultInfo = this.getDefaultInfo();

        // Getting Block IDs
        this.defaultOreId = this.getSetConfigBlockId("ore");
        this.defaultBlockId = this.getSetConfigBlockId("block");
        this.defaultBrickId = this.getSetConfigBlockId("brick");

        // Getting Item IDs
        this.defaultDustId = this.getSetConfigItemId("dust");
        this.defaultIngotId = this.getSetConfigItemId("ingot");
        this.defaultItemId = this.getSetConfigItemId("item");

        for (Metal metal : this.metals)
        {

            if (metal.type == Metal.MetalType.Default)
            {
                continue;
            }

            MetalBlock ore = null;
            MetalBlock block = null;
            MetalBlock brick = null;
            MetalItem dust = null;
            MetalItem ingot = null;
            MetalItem item = null;

            String texture = metal.getName().replace(" ", "_");
            texture = Metallurgy.MODID + ":" + this.name + "/" + texture.toLowerCase();

            String tag = metal.getName().replace(" ", "");
            String configTag = tag.substring(0, 1).toUpperCase() + tag.substring(1);

            int metaId = metal.ids.get("meta");

            if ((metal.ids.get("ore") != null || this.defaultOreId != 0) && metal.type != Metal.MetalType.Alloy)
            {
                String identifier = "ore";

                int oreId = this.defaultOreId;

                // if there is no set defaultid

                if (oreId == 0)
                {
                    oreId = ConfigHandler.getBlock(identifier + configTag, metal.ids.get(identifier));
                }

                if (metal.type != Metal.MetalType.Respawn)
                {
                    ore = this.createBlock(oreId, metaId, metal.blockLvl, tag, texture, identifier);

                    int itemId = this.defaultItemId;

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

            if ((metal.ids.get("block") != null || this.defaultBlockId != 0) && (metal.type != Metal.MetalType.Drop || metal.type != Metal.MetalType.Respawn))
            {
                String identifier = "block";

                int blockId = this.defaultBlockId;

                if (blockId == 0)
                {
                    blockId = ConfigHandler.getBlock(identifier + configTag, metal.ids.get(identifier));
                }

                block = this.createBlock(blockId, metaId, metal.blockLvl, tag, texture, identifier);
                block.addSubBlock(metaId, metal.getName(), 1, texture + "_" + identifier);
            }
            
            if ((metal.ids.get("ingot") != null || this.defaultIngotId != 0) && metal.type != Metal.MetalType.Drop)
            {
                String identifier = "ingot";
                
                int ingotId = this.defaultIngotId;
                
                if (ingotId == 0)
                {
                    ingotId = ConfigHandler.getItem(identifier + configTag, metal.ids.get(identifier));
                }
                
                ingot = this.getMetalItem(ingotId);
                ingot.addSubItem(0, metal.getName(), 1, texture + "_" + identifier);
                
                OreDictionary.registerOre(identifier + tag, new ItemStack(ingot, 1, 0));
                
                if (GameRegistry.findUniqueIdentifierFor(ingot) == null)
                {
                    String registryName = metal.getName().toLowerCase();
                    registryName = registryName.replace(" ", ".");
                    registryName = registryName + "." + identifier;
                    
                    GameRegistry.registerItem(ingot, registryName);
                }
            }


            if ((metal.ids.get("brick") != null || this.defaultBrickId != 0) && (metal.type != Metal.MetalType.Drop || metal.type != Metal.MetalType.Respawn))
            {
                String identifier = "brick";

                int brickId = this.defaultBrickId;

                if (brickId == 0)
                {
                    brickId = ConfigHandler.getBlock(identifier + configTag, metal.ids.get(identifier));
                }

                brick = this.createBlock(brickId, metaId, metal.blockLvl, tag, texture, identifier);
                brick.addSubBlock(metaId, metal.getName(), 2, texture + "_" + identifier);
                CraftingManager.getInstance().addRecipe(new ItemStack(brick, 1, metaId), new Object[] {"iii", "iii", "iii", 'i', ingot});
            }

            if ((metal.ids.get("dust") != null || this.defaultDustId != 0) && metal.type != Metal.MetalType.Drop)
            {
                String identifier = "dust";

                int dustId = this.defaultDustId;

                if (dustId == 0)
                {
                    dustId = ConfigHandler.getItem(identifier + configTag, metal.ids.get(identifier));
                }

                dust = this.getMetalItem(dustId);
                dust.addSubItem(metaId, metal.getName(), 0, texture + "_" + identifier);

                this.registerItem(dust, tag, metaId, identifier);

            }

            if ((metal.ids.get("item") != null || this.defaultItemId != 0) && metal.type == Metal.MetalType.Drop)
            {
                String identifier = "item";

                int itemId = this.defaultItemId;

                if (itemId == 0)
                {
                    itemId = ConfigHandler.getItem(identifier + configTag, metal.ids.get(identifier));
                }

                item = this.getMetalItem(itemId);

                // Some items have different names than the ores
                String itemName = metal.dropName;
                String itemTexture = metal.dropName.replace(" ", "_");
                itemTexture = Metallurgy.MODID + ":" + this.name + "/" + itemTexture.toLowerCase();

                if (itemName.compareTo("") == 0)
                {
                    itemName = metal.getName();
                    itemTexture = texture;
                }

                item.addSubItem(metaId, itemName, 2, itemTexture);

                this.registerItem(item, tag, metaId, identifier);

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

            // Tools and weapons

            if (ingot != null && metal.haveTools())
            {
                String statsName = metal.getName().toUpperCase();
                statsName = statsName.replace(" ", "_");

                int harvestLevel = metal.getToolHarvestLevel();
                int maxUses = metal.getToolDurability();
                int efficiency = metal.getToolEncantabilty();
                int damage = metal.getToolDamage();
                int enchantability = metal.getToolEncantabilty();

                EnumToolMaterial toolMaterial = EnumHelper.addToolMaterial(statsName, harvestLevel, maxUses, efficiency, damage, enchantability);

                toolMaterial.customCraftingMaterial = ingot;

                // Unlocalized Name
                String toolUName = metal.getName().toLowerCase();
                toolUName = toolUName.replace(" ", ".");

                if (metal.ids.get("axe") != null)
                {
                    String axeTexture = texture + "_" + "axe";
                    String axeUName = toolUName + ".axe";

                    int axeId = ConfigHandler.getItem("Axes", "axe" + configTag, metal.ids.get("axe"));

                    Axe axe = new Axe(axeId, toolMaterial, axeUName, axeTexture);
                    MinecraftForge.setToolClass(axe, "axe", harvestLevel);
                    GameRegistry.registerItem(axe, axeUName);
                    CraftingManager.getInstance().addRecipe(new ItemStack(axe), new Object[] {"iii", "is ", " s ", 'i', ingot, 's', Item.stick});
                }

                if (metal.ids.get("hoe") != null)
                {
                    String hoeTexture = texture + "_" + "hoe";
                    String hoeUName = toolUName + ".hoe";

                    int hoeId = ConfigHandler.getItem("Hoes", "hoe" + configTag, metal.ids.get("hoe"));

                    Hoe hoe = new Hoe(hoeId, toolMaterial, hoeUName, hoeTexture);
                    MinecraftForge.setToolClass(hoe, "hoe", harvestLevel);
                    GameRegistry.registerItem(hoe, hoeUName);
                    CraftingManager.getInstance().addRecipe(new ItemStack(hoe), new Object[] {"ii ", " s ", " s ", 'i', ingot, 's', Item.stick});
                }

                if (metal.ids.get("pickaxe") != null)
                {
                    String pickaxeTexture = texture + "_" + "pick";
                    String pickaxeUName = toolUName + ".pickaxe";

                    int pickaxeId = ConfigHandler.getItem("Pickaxes", "pickaxe" + configTag, metal.ids.get("pickaxe"));

                    Pickaxe pickaxe = new Pickaxe(pickaxeId, toolMaterial, pickaxeUName, pickaxeTexture);
                    MinecraftForge.setToolClass(pickaxe, "pickaxe", harvestLevel);
                    GameRegistry.registerItem(pickaxe, pickaxeUName);
                    CraftingManager.getInstance().addRecipe(new ItemStack(pickaxe), new Object[] {"iii", " s ", " s ", 'i', ingot, 's', Item.stick});
                }

                if (metal.ids.get("shovel") != null)
                {
                    String shovelTexture = texture + "_" + "shovel";
                    String shovelUName = toolUName + ".shovel";

                    int shovelId = ConfigHandler.getItem("Shovels", "shovel" + configTag, metal.ids.get("shovel"));

                    Shovel shovel = new Shovel(shovelId, toolMaterial, shovelUName, shovelTexture);
                    MinecraftForge.setToolClass(shovel, "shovel", harvestLevel);
                    GameRegistry.registerItem(shovel, shovelUName);
                    CraftingManager.getInstance().addRecipe(new ItemStack(shovel), new Object[] {"i", "s", "s", 'i', ingot, 's', Item.stick});
                }

                if (metal.ids.get("sword") != null)
                {
                    String swordTexture = texture + "_" + "sword";
                    String swordUName = toolUName + ".sword";

                    int swordId = ConfigHandler.getItem("Swords", "sword" + configTag, metal.ids.get("sword"));

                    Sword sword = new Sword(swordId, toolMaterial, swordUName, swordTexture);
                    GameRegistry.registerItem(sword, swordUName);
                    CraftingManager.getInstance().addRecipe(new ItemStack(sword), new Object[] {"i", "i", "s", 'i', ingot, 's', Item.stick});
                }
            }

            // Armor
            if (ingot != null && metal.haveArmor())
            {
                String modelTexture = metal.getName().replace(" ", "_").toLowerCase();
                modelTexture = this.name.toLowerCase() + "/" + modelTexture;

                String armorUName = metal.getName().toLowerCase();
                armorUName = armorUName.replace(" ", ".");
                armorUName = Metallurgy.MODID.toLowerCase() + "." + armorUName;

                int mutiplier = metal.getArmorMultiplier();
                int[] damageReduction = metal.getArmorDamageReduction();
                int enchantablilty = metal.getArmorEnchantability();

                EnumArmorMaterial armorMaterial = EnumHelper.addArmorMaterial(armorUName, mutiplier, damageReduction, enchantablilty);

                armorMaterial.customCraftingMaterial = ingot;

                // Set default model texture to diamond, model texture will be
                // overridden by the class.
                int renderIndex = 3;

                if (metal.ids.get("helmet") != null)
                {
                    String helmetIconTexture = texture + "_helmet";
                    String helmetUName = armorUName + ".helmet";

                    int helmetID = ConfigHandler.getItem("Helmets", "helmet" + configTag, metal.ids.get("helmet"));

                    ItemMetallurgyArmor helmet = new ItemMetallurgyArmor(helmetID, armorMaterial, renderIndex, 0, modelTexture);
                    helmet = (ItemMetallurgyArmor) helmet.setUnlocalizedName(helmetUName);
                    helmet = (ItemMetallurgyArmor) helmet.setTextureName(helmetIconTexture);
                    GameRegistry.registerItem(helmet, helmetUName);
                    CraftingManager.getInstance().addRecipe(new ItemStack(helmet), new Object[] {"iii", "i i", 'i', ingot});
                }

                if (metal.ids.get("chestplate") != null)
                {
                    String chestplateIconTexture = texture + "_chest";
                    String chestplateUName = armorUName + ".chestplate";

                    int chestplateID = ConfigHandler.getItem("Chestplates", "chestplate" + configTag, metal.ids.get("chestplate"));

                    ItemMetallurgyArmor chestplate = new ItemMetallurgyArmor(chestplateID, armorMaterial, renderIndex, 1, modelTexture);
                    chestplate = (ItemMetallurgyArmor) chestplate.setUnlocalizedName(chestplateUName);
                    chestplate = (ItemMetallurgyArmor) chestplate.setTextureName(chestplateIconTexture);
                    GameRegistry.registerItem(chestplate, chestplateUName);
                    CraftingManager.getInstance().addRecipe(new ItemStack(chestplate), new Object[] {"i i", "iii", "iii", 'i', ingot});
                }

                if (metal.ids.get("leggings") != null)
                {
                    String leggingsIconTexture = texture + "_legs";
                    String leggingsUName = armorUName + ".leggings";

                    int leggingsID = ConfigHandler.getItem("Leggings", "leggings" + configTag, metal.ids.get("leggings"));

                    ItemMetallurgyArmor leggings = new ItemMetallurgyArmor(leggingsID, armorMaterial, renderIndex, 2, modelTexture);
                    leggings = (ItemMetallurgyArmor) leggings.setUnlocalizedName(leggingsUName);
                    leggings = (ItemMetallurgyArmor) leggings.setTextureName(leggingsIconTexture);
                    GameRegistry.registerItem(leggings, leggingsUName);
                    CraftingManager.getInstance().addRecipe(new ItemStack(leggings), new Object[] {"iii", "i i", "i i", 'i', ingot});
                }

                if (metal.ids.get("boots") != null)
                {
                    String bootsIconTexture = texture + "_boots";
                    String bootsUName = armorUName + ".boots";

                    int bootsID = ConfigHandler.getItem("Boots", "boots" + configTag, metal.ids.get("boots"));

                    ItemMetallurgyArmor boots = new ItemMetallurgyArmor(bootsID, armorMaterial, renderIndex, 3, modelTexture);
                    boots = (ItemMetallurgyArmor) boots.setUnlocalizedName(bootsUName);
                    boots = (ItemMetallurgyArmor) boots.setTextureName(bootsIconTexture);
                    GameRegistry.registerItem(boots, bootsUName);
                    CraftingManager.getInstance().addRecipe(new ItemStack(boots), new Object[] {"i i", "i i", 'i', ingot});
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
}
