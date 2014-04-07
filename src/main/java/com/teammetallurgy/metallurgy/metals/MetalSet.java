package com.teammetallurgy.metallurgy.metals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.util.EnumHelper;
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

    private HashMap<Integer, MetalItem> items = new HashMap<Integer, MetalItem>();
    private HashMap<Integer, MetalBlock> blocks = new HashMap<Integer, MetalBlock>();

    private MetalBlock defaultOre;
    private MetalBlock defaultBlock;
    private MetalBlock defaultBricks;

    private MetalItem defaultDust;
    private MetalItem defaultDrops;

    private HashMap<String, ItemStack> oreStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> blockStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> brickStacks = new HashMap<String, ItemStack>();

    private HashMap<String, ItemStack> ingotStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> dustStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> dropStacks = new HashMap<String, ItemStack>();

    public MetalSet(String setName)
    {
        this.name = setName;
        this.setTag = this.name.substring(0, 1).toUpperCase() + this.name.substring(1);
        this.initDefaults();
    }

    private void initDefaults()
    {
        String postfix = name.toLowerCase();
        postfix = postfix.replace(" ", ".");

        this.defaultOre = new MetalBlock(postfix + ".ore");
        this.defaultBlock = new MetalBlock(postfix + ".block");
        this.defaultBricks = new MetalBlock(postfix + ".brick");

        this.defaultDust = new MetalItem(postfix + ".dust");
        this.defaultDrops = new MetalItem(postfix + ".item");
    }

    private MetalBlock createBlock(MetalBlock metalBlock, int meta, int harvestLvl, String metalTag, String identifier)
    {

        metalBlock.setHarvestLevel("pickaxe", harvestLvl, meta);

        OreDictionary.registerOre(identifier + metalTag, new ItemStack(metalBlock, 1, meta));

        if (meta == 0)
        {
            GameRegistry.registerBlock(metalBlock, ItemMetalBlock.class, this.name + "." + identifier);
        }

        return metalBlock;
    }

    private MetalItem createItem(MetalItem metalItem, int meta, String metalTag, String identifier)
    {
        OreDictionary.registerOre(identifier + metalTag, new ItemStack(metalItem, 1, meta));

        if (meta == 0)
        {
            GameRegistry.registerItem(metalItem, this.name + "." + identifier);
        }

        return metalItem;
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
        Block block = this.blocks.get(id);
        if (block == null)
        {
            metalBlock = new MetalBlock(id);
            this.blocks.put(id, metalBlock);
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
        Item item = this.items.get(id);
        if (item == null)
        {
            metalItem = new MetalItem(id);
            this.items.put(id, metalItem);
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
                    ore = this.createBlock(defaultOre, metaId, metal.blockLvl, tag, identifier);

                    int itemId = this.defaultItemId;

                    item = this.defaultDrops;

                    if (metal.type == Metal.MetalType.Drop)
                    {
                        ore.addSubBlock(metaId, metal.getName(), 0, texture + "_" + identifier, item);
                    }
                    else
                    {
                        ore.addSubBlock(metaId, metal.getName(), 0, texture + "_" + identifier);
                    }

                    this.oreStacks.put(tag, new ItemStack(ore, 1, metaId));

                }

                if (ConfigHandler.generates(tag))
                {
                    WorldGenMetals worldGen = new WorldGenMetals(ore, metaId, metal.generation, metal.dimensions);

                    GameRegistry.registerWorldGenerator(worldGen, 5);
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

                block = this.createBlock(defaultBlock, metaId, metal.blockLvl, tag, identifier);
                block.addSubBlock(metaId, metal.getName(), 1, texture + "_" + identifier);

                this.blockStacks.put(tag, new ItemStack(block, 1, metaId));
            }

            if ((metal.ids.get("ingot") != null || this.defaultIngotId != 0) && metal.type != Metal.MetalType.Drop)
            {
                String identifier = "ingot";

                int ingotId = this.defaultIngotId;

                if (ingotId == 0)
                {
                    ingotId = ConfigHandler.getItem(identifier + configTag, metal.ids.get(identifier));
                }

                ingot = new MetalItem(configTag + "." + identifier);
                ingot.addSubItem(0, metal.getName(), 1, texture + "_" + identifier);

                OreDictionary.registerOre(identifier + tag, new ItemStack(ingot, 1, 0));

                String registryName = metal.getName().toLowerCase();
                registryName = registryName.replace(" ", ".");
                registryName = registryName + "." + identifier;

                GameRegistry.registerItem(ingot, registryName);
                ingotStacks.put(tag, new ItemStack(ingot));

            }

            if ((metal.ids.get("brick") != null || this.defaultBrickId != 0) && (metal.type != Metal.MetalType.Drop || metal.type != Metal.MetalType.Respawn))
            {
                String identifier = "brick";

                int brickId = this.defaultBrickId;

                if (brickId == 0)
                {
                    brickId = ConfigHandler.getBlock(identifier + configTag, metal.ids.get(identifier));
                }

                brick = this.createBlock(defaultBricks, metaId, metal.blockLvl, tag, identifier);
                brick.addSubBlock(metaId, metal.getName(), 2, texture + "_" + identifier);
                brickStacks.put(metal.getName(), new ItemStack(brick, 1, metaId));

                GameRegistry.addShapedRecipe(new ItemStack(brick, 1, metaId), new Object[] { "ii", "ii", 'i', ingot });
                GameRegistry.addShapelessRecipe(new ItemStack(ingot,4), new ItemStack(brick, 1, metaId));

            }

            if ((metal.ids.get("dust") != null || this.defaultDustId != 0) && metal.type != Metal.MetalType.Drop)
            {
                String identifier = "dust";

                int dustId = this.defaultDustId;

                if (dustId == 0)
                {
                    dustId = ConfigHandler.getItem(identifier + configTag, metal.ids.get(identifier));
                }

                dust = this.createItem(this.defaultDust, metaId, tag, identifier);
                dust.addSubItem(metaId, metal.getName(), 0, texture + "_" + identifier);
                dustStacks.put(tag, new ItemStack(dust, 1, metaId));

            }

            if ((metal.ids.get("item") != null || this.defaultItemId != 0) && metal.type == Metal.MetalType.Drop)
            {
                String identifier = "item";

                int itemId = this.defaultItemId;

                if (itemId == 0)
                {
                    itemId = ConfigHandler.getItem(identifier + configTag, metal.ids.get(identifier));
                }

                item = this.createItem(this.defaultDrops, metaId, tag, identifier);

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
                dropStacks.put(tag, new ItemStack(item, 1, metaId));

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

                Item.ToolMaterial toolMaterial = EnumHelper.addToolMaterial(statsName, harvestLevel, maxUses, efficiency, damage, enchantability);

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
                    axe.setHarvestLevel("axe", harvestLevel);
                    GameRegistry.registerItem(axe, axeUName);
                    GameRegistry.addRecipe(new ItemStack(axe), new Object[] { "iii", "is ", " s ", 'i', ingot, 's', Items.stick });
                }

                if (metal.ids.get("hoe") != null)
                {
                    String hoeTexture = texture + "_" + "hoe";
                    String hoeUName = toolUName + ".hoe";

                    int hoeId = ConfigHandler.getItem("Hoes", "hoe" + configTag, metal.ids.get("hoe"));

                    Hoe hoe = new Hoe(hoeId, toolMaterial, hoeUName, hoeTexture);
                    hoe.setHarvestLevel("hoe", harvestLevel);
                    GameRegistry.registerItem(hoe, hoeUName);
                    GameRegistry.addRecipe(new ItemStack(hoe), new Object[] { "ii ", " s ", " s ", 'i', ingot, 's', Items.stick });
                }

                if (metal.ids.get("pickaxe") != null)
                {
                    String pickaxeTexture = texture + "_" + "pick";
                    String pickaxeUName = toolUName + ".pickaxe";

                    int pickaxeId = ConfigHandler.getItem("Pickaxes", "pickaxe" + configTag, metal.ids.get("pickaxe"));

                    Pickaxe pickaxe = new Pickaxe(pickaxeId, toolMaterial, pickaxeUName, pickaxeTexture);
                    pickaxe.setHarvestLevel("pickaxe", harvestLevel);
                    GameRegistry.registerItem(pickaxe, pickaxeUName);
                    GameRegistry.addRecipe(new ItemStack(pickaxe), new Object[] { "iii", " s ", " s ", 'i', ingot, 's', Items.stick });
                }

                if (metal.ids.get("shovel") != null)
                {
                    String shovelTexture = texture + "_" + "shovel";
                    String shovelUName = toolUName + ".shovel";

                    int shovelId = ConfigHandler.getItem("Shovels", "shovel" + configTag, metal.ids.get("shovel"));

                    Shovel shovel = new Shovel(shovelId, toolMaterial, shovelUName, shovelTexture);
                    shovel.setHarvestLevel("shovel", harvestLevel);
                    GameRegistry.registerItem(shovel, shovelUName);
                    GameRegistry.addRecipe(new ItemStack(shovel), new Object[] { "i", "s", "s", 'i', ingot, 's', Items.stick });
                }

                if (metal.ids.get("sword") != null)
                {
                    String swordTexture = texture + "_" + "sword";
                    String swordUName = toolUName + ".sword";

                    int swordId = ConfigHandler.getItem("Swords", "sword" + configTag, metal.ids.get("sword"));

                    Sword sword = new Sword(swordId, toolMaterial, swordUName, swordTexture);
                    GameRegistry.registerItem(sword, swordUName);
                    GameRegistry.addRecipe(new ItemStack(sword), new Object[] { "i", "i", "s", 'i', ingot, 's', Items.stick });
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

                ItemArmor.ArmorMaterial armorMaterial = EnumHelper.addArmorMaterial(armorUName, mutiplier, damageReduction, enchantablilty);

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
                    GameRegistry.addRecipe(new ItemStack(helmet), new Object[] { "iii", "i i", 'i', ingot });
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
                    GameRegistry.addRecipe(new ItemStack(chestplate), new Object[] { "i i", "iii", "iii", 'i', ingot });
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
                    GameRegistry.addRecipe(new ItemStack(leggings), new Object[] { "iii", "i i", "i i", 'i', ingot });
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
                    GameRegistry.addRecipe(new ItemStack(boots), new Object[] { "i i", "i i", 'i', ingot });
                }
            }
        }
    }

}
