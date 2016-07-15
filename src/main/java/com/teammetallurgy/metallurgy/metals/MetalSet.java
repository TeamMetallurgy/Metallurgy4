package com.teammetallurgy.metallurgy.metals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.google.gson.Gson;
import com.teammetallurgy.metallurgy.Metallurgy;
import com.teammetallurgy.metallurgy.Utils;
import com.teammetallurgy.metallurgy.api.IMetalInfo;
import com.teammetallurgy.metallurgy.api.IMetalSet;
import com.teammetallurgy.metallurgy.api.MetalType;
import com.teammetallurgy.metallurgy.armor.ItemMetallurgyArmor;
import com.teammetallurgy.metallurgy.handlers.ConfigHandler;
import com.teammetallurgy.metallurgy.handlers.LogHandler;
import com.teammetallurgy.metallurgy.recipes.AbstractorRecipes;
import com.teammetallurgy.metallurgy.tools.Axe;
import com.teammetallurgy.metallurgy.tools.Hoe;
import com.teammetallurgy.metallurgy.tools.Pickaxe;
import com.teammetallurgy.metallurgy.tools.Shovel;
import com.teammetallurgy.metallurgy.tools.Sword;
import com.teammetallurgy.metallurgy.world.WorldGenMetals;

import cpw.mods.fml.common.registry.GameRegistry;

public class MetalSet implements IMetalSet
{
    private String name;
    private Metal[] metals = null;

    private String setTag;

    private MetalBlock defaultOre;
    private MetalBlock defaultBlock;
    private MetalBlock defaultBricks;

    private MetalItem defaultDust;
    private MetalItem defaultDrops;
    private MetalItem defaultNugget;

    private HashMap<String, ItemStack> oreStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> blockStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> brickStacks = new HashMap<String, ItemStack>();

    private HashMap<String, ItemStack> ingotStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> dustStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> dropStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> nuggetStacks = new HashMap<String, ItemStack>();

    private HashMap<String, Item.ToolMaterial> toolMaterials = new HashMap<String, Item.ToolMaterial>();

    private HashMap<String, ItemStack> axeStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> hoeStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> pickaxeStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> shovelStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> swordStacks = new HashMap<String, ItemStack>();

    private HashMap<String, ItemArmor.ArmorMaterial> armorMaterials = new HashMap<String, ItemArmor.ArmorMaterial>();

    private HashMap<String, ItemStack> helmetStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> chestplateStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> leggingsStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> bootsStacks = new HashMap<String, ItemStack>();

    public MetalSet(String setName)
    {
        this.name = setName;
        this.setTag = this.name.substring(0, 1).toUpperCase(Locale.US) + this.name.substring(1);
        this.initDefaults();
    }

    private MetalBlock createBlock(MetalBlock metalBlock, int meta, int harvestLvl, String metalTag, String identifier, boolean defaultOreDic, String[] oreDicAliases)
    {

        metalBlock.setHarvestLevel("pickaxe", harvestLvl, meta);

        if (meta == 0)
        {
            GameRegistry.registerBlock(metalBlock, ItemMetalBlock.class, this.name + "." + identifier);
        }

        ItemStack blockStack = new ItemStack(metalBlock, 1, meta);

        if (defaultOreDic)
        {
            OreDictionary.registerOre(identifier + metalTag, blockStack.copy());
        }

        if (oreDicAliases != null && oreDicAliases.length > 0)
        {
            for (int i = 0; i < oreDicAliases.length; i++)
            {
                OreDictionary.registerOre(oreDicAliases[i], blockStack.copy());

            }
        }

        return metalBlock;
    }

    private MetalItem createItem(MetalItem metalItem, int meta, String metalTag, String identifier, boolean defaultOreDic, String[] oreDicAliases)
    {

        if (meta == 0)
        {
            GameRegistry.registerItem(metalItem, this.name + "." + identifier);
        }

        ItemStack itemStack = new ItemStack(metalItem, 1, meta);

        if (defaultOreDic)
        {
            OreDictionary.registerOre(identifier + metalTag, itemStack.copy());
        }

        if (oreDicAliases != null && oreDicAliases.length > 0)
        {
            for (int i = 0; i < oreDicAliases.length; i++)
            {
                OreDictionary.registerOre(oreDicAliases[i], itemStack.copy());
            }
        }

        return metalItem;
    }

    public String[] createOreDicAliases(String identifier, String[] aliases)
    {

        String[] oreDicAliases = null;

        if (aliases != null && aliases.length > 0)
        {
            oreDicAliases = new String[aliases.length];

            for (int i = 0; i < aliases.length; i++)
            {
                String alias = aliases[i];

                alias = alias.replace(" ", "");

                oreDicAliases[i] = identifier + alias;
            }
        }

        return oreDicAliases;
    }

    @Override
    public ItemStack getAxe(String metal)
    {
        return this.axeStacks.get(metal);
    }

    @Override
    public ArmorMaterial getArmorMaterial(String metal)
    {
        return this.armorMaterials.get(metal);
    }

    @Override
    public ItemStack getBlock(String metal)
    {
        return this.blockStacks.get(metal);
    }

    @Override
    public ItemStack getBoots(String metal)
    {
        return this.bootsStacks.get(metal);
    }

    @Override
    public ItemStack getBrick(String metal)
    {
        return this.brickStacks.get(metal);
    }

    @Override
    public ItemStack getChestplate(String metal)
    {
        return this.chestplateStacks.get(metal);
    }

    @Override
    public Block getDefaultOre()
    {
        return this.defaultOre;
    }

    @Override
    public Block getDefaultBlock()
    {
        return this.defaultBlock;
    }

    @Override
    public Block getDefaultBricks()
    {
        return this.defaultBricks;
    }

    @Override
    public ItemStack getDrop(String metal)
    {
        return this.dropStacks.get(metal);
    }

    @Override
    public ItemStack getDust(String metal)
    {
        return this.dustStacks.get(metal);
    }

    @Override
    public ItemStack getHelmet(String metal)
    {
        return this.helmetStacks.get(metal);
    }

    @Override
    public ItemStack getHoe(String metal)
    {
        return this.hoeStacks.get(metal);
    }

    @Override
    public ItemStack getIngot(String metal)
    {
        return this.ingotStacks.get(metal);
    }

    @Override
    public ItemStack getLeggings(String metal)
    {
        return this.leggingsStacks.get(metal);
    }

    @Override
    public IMetalInfo getMetal(String metal)
    {
        IMetalInfo metalInfo = null;

        for (int i = 0; i < metals.length; i++)
        {
            if (metals[i].getType() == MetalType.Default)
            {
                continue;
            }

            if (metal.equalsIgnoreCase((metals[i].getName())))
            {
                metalInfo = (IMetalInfo) metals[i];
                break;
            }
        }

        return metalInfo;
    }

    @Override
    public String[] getMetalNames()
    {
        ArrayList<String> names = new ArrayList<String>();

        for (int i = 0; i < metals.length; i++)
        {
            if (metals[i].getType() != MetalType.Default)
            {
                names.add(metals[i].getName());
            }
        }

        String[] outputNames = new String[names.size()];
        outputNames = names.toArray(outputNames);

        return outputNames;
    }

    @Override
    public ItemStack getNugget(String metal)
    {
        return this.nuggetStacks.get(metal);
    }

    @Override
    public ItemStack getOre(String metal)
    {
        return this.oreStacks.get(metal);
    }

    @Override
    public ItemStack getPickaxe(String metal)
    {
        return this.pickaxeStacks.get(metal);
    }

    @Override
    public ItemStack getShovel(String metal)
    {
        return this.shovelStacks.get(metal);
    }

    @Override
    public ItemStack getSword(String metal)
    {
        return this.swordStacks.get(metal);
    }

    @Override
    public ToolMaterial getToolMaterial(String metal)
    {
        return this.toolMaterials.get(metal);
    }

    private void initDefaults()
    {
        String postfix = this.name.toLowerCase(Locale.US);
        postfix = postfix.replace(" ", ".");

        this.defaultOre = new MetalBlock(postfix + ".ore");
        this.defaultBlock = new MetalBlock(postfix + ".block");
        this.defaultBricks = new MetalBlock(postfix + ".brick");

        this.defaultDust = new MetalItem(postfix + ".dust");
        this.defaultDrops = new MetalItem(postfix + ".item");
        this.defaultNugget = new MetalItem(postfix + ".nugget");
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
            LogHandler.log(e.getLocalizedMessage());
        }

        this.metals = new Gson().fromJson(reader, Metal[].class);

        for (Metal metal : this.metals)
        {

            if (metal.type == MetalType.Default)
            {
                continue;
            }

            MetalBlock ore = null;
            MetalBlock block = null;
            MetalBlock brick = null;
            MetalItem dust = null;
            MetalItem ingot = null;
            MetalItem item = null;
            MetalItem nugget = null;
            
            //find the recipe element and its oreDict entry
            ItemStack recipeElement = null;
            String recipeOreDict = "";

            String texture = metal.getName().replace(" ", "_");
            texture = Metallurgy.MODID + ":" + this.name + "/" + texture.toLowerCase(Locale.US);

            String tag = metal.getName().replace(" ", "");
            String configTag = tag.substring(0, 1).toUpperCase(Locale.US) + tag.substring(1);

            int metaId = metal.meta;

            String metalTag = metal.getName().trim().toLowerCase(Locale.US).replace(" ", "_");
            int blockLvl = ConfigHandler.blockHarvestLevel(metalTag, metal.blockLvl);

            if (metal.type != MetalType.Drop)
            {
            	//Dust is exclusively for non-drops
                String identifier = "dust";

                String[] oreDicAliases = createOreDicAliases(identifier, metal.getAliases());

                dust = this.createItem(this.defaultDust, metaId, tag, identifier, true, oreDicAliases);
                dust.addSubItem(metaId, metal.getName(), 0, texture + "_" + identifier);
                this.dustStacks.put(metal.getName(), new ItemStack(dust, 1, metaId));
                
                //Ingots are exclusively for non-drops
                identifier = "ingot";

                ingot = new MetalItem(configTag + "." + identifier);
                ingot.addSubItem(0, metal.getName(), 1, texture + "_" + identifier);

                String registryName = metal.getName().toLowerCase(Locale.US);
                registryName = registryName.replace(" ", ".");
                registryName = registryName + "." + identifier;

                GameRegistry.registerItem(ingot, registryName);
                
                //For non-drops we have found the oreDict entry we need for recipes
                recipeOreDict = identifier + tag; 
                OreDictionary.registerOre(recipeOreDict, new ItemStack(ingot));
                
                //For non-drops we will use the ingot as the recipe element
                recipeElement = new ItemStack(ingot);
                this.ingotStacks.put(metal.getName(), recipeElement);                

                //Non-drops have nuggets!
                identifier = "nugget";

                oreDicAliases = createOreDicAliases(identifier, metal.getAliases());

                nugget = this.createItem(this.defaultNugget, metaId, tag, identifier, true, oreDicAliases);
                nugget.addSubItem(metaId, metal.getName(), 3, texture + "_" + identifier);
                ItemStack nuggetStack = new ItemStack(nugget, 1, metaId);
                this.nuggetStacks.put(metal.getName(), nuggetStack.copy());

                ItemStack returnedItems = nuggetStack.copy();
                returnedItems.stackSize = 9;

                GameRegistry.addShapedRecipe(new ItemStack(ingot), new Object[] { "iii", "iii", "iii", 'i', nuggetStack.copy() });
                GameRegistry.addShapelessRecipe(returnedItems, new ItemStack(ingot));

            }
            else
            {
            	//"Ingots" for drops are called "item"
                String identifier = "item";

                boolean defaultOreDic = true;
                String[] customOreDic = metal.getDropOreDicNames();
                String[] oreDicAliases = createOreDicAliases(identifier, metal.getAliases());

                ArrayList<String> oreDicList = new ArrayList<String>();

                if (customOreDic != null && customOreDic.length > 0)
                {
                    oreDicList.addAll(Arrays.asList(customOreDic));
                    defaultOreDic = false;
                }

                if (oreDicAliases != null && oreDicAliases.length > 0)
                {
                    oreDicList.addAll(Arrays.asList(oreDicAliases));
                }

                String[] oreDic = null;

                if (oreDicList.size() > 0)
                {
                    oreDic = new String[oreDicList.size()];
                    oreDic = oreDicList.toArray(oreDic);

                    //we have found our item oreDict entry
                    recipeOreDict = oreDic[0];
                }
                
                item = this.createItem(this.defaultDrops, metaId, tag, identifier, defaultOreDic, oreDic);

                // Some items have different names than the ores
                String itemName = metal.dropName;
                String itemTexture = metal.dropName.replace(" ", "_");
                itemTexture = Metallurgy.MODID + ":" + this.name + "/" + itemTexture.toLowerCase(Locale.US);

                if (itemName.compareTo("") == 0)
                {
                    itemName = metal.getName();
                    itemTexture = texture;
                }

                item.addSubItem(metaId, itemName, 2, itemTexture);
                
                //we have found our recipe item
                recipeElement = new ItemStack(item, 1, metaId);
                this.dropStacks.put(metal.getName(), recipeElement);

            }

            if (metal.type != MetalType.Alloy)
            {
                String identifier = "ore";

                if (metal.type != MetalType.Respawn)
                {
                    String[] oreDicAliases = createOreDicAliases(identifier, metal.getAliases());

                    ore = this.createBlock(this.defaultOre, metaId, blockLvl, tag, identifier, true, oreDicAliases);

                    item = this.defaultDrops;

                    if (metal.type == MetalType.Drop)
                    {
                        ore.addSubBlock(metaId, metal.getName(), 0, texture + "_" + identifier, item);
                    }
                    else
                    {
                        ore.addSubBlock(metaId, metal.getName(), 0, texture + "_" + identifier);
                    }

                    if (metal.haveParticles())
                    {
                        int type = metal.getParticleType();
                        int[] colors = metal.getParticleColors();
                        ore.setSubBlockParticles(metaId, type, colors[0], colors[1], colors[2]);
                    }

                    this.oreStacks.put(metal.getName(), new ItemStack(ore, 1, metaId));

                }

                if (ConfigHandler.generates(tag))
                {
                    String dims = ConfigHandler.getOreGenerationDimInfo(configTag, metal.dimensions);
                    int[] genInfo = ConfigHandler.getOreGenerationInformation(configTag, metal.generation);

                    WorldGenMetals worldGen = new WorldGenMetals(ore, metaId, genInfo, dims);

                    GameRegistry.registerWorldGenerator(worldGen, 5);
                }
            }

            if (metal.type != MetalType.Respawn)
            {
                String identifier = "block";

                String[] oreDicAliases = createOreDicAliases(identifier, metal.getAliases());

                block = this.createBlock(this.defaultBlock, metaId, blockLvl, tag, identifier, true, oreDicAliases);
                block.addSubBlock(metaId, metal.getName(), 1, texture + "_" + identifier);

                this.blockStacks.put(metal.getName(), new ItemStack(block, 1, metaId));

                if (metal.type == MetalType.Drop)
                {
                    recipeElement = new ItemStack(item, 1, metaId);
                }
                else
                {
                    recipeElement = new ItemStack(ingot);
                }

                ItemStack returnedItems = recipeElement.copy();
                returnedItems.stackSize = 9;

                GameRegistry.addShapedRecipe(new ItemStack(block, 1, metaId), new Object[] { "iii", "iii", "iii", 'i', recipeElement });
                GameRegistry.addShapelessRecipe(returnedItems, new ItemStack(block, 1, metaId));

            }

            if (metal.type != MetalType.Drop && metal.type != MetalType.Respawn)
            {
                // Bricks
                String identifier = "brick";

                String[] oreDicAliases = createOreDicAliases(identifier, metal.getAliases());

                brick = this.createBlock(this.defaultBricks, metaId, blockLvl, tag, identifier, true, oreDicAliases);
                brick.addSubBlock(metaId, metal.getName(), 2, texture + "_" + identifier);
                this.brickStacks.put(metal.getName(), new ItemStack(brick, 1, metaId));

                GameRegistry.addShapedRecipe(new ItemStack(brick, 1, metaId), new Object[] { "ii", "ii", 'i', ingot });
                GameRegistry.addShapelessRecipe(new ItemStack(ingot, 4), new ItemStack(brick, 1, metaId));

                // Molten Metals
                String fluidName = ((metal.getName()).trim().replace(" ", ".").toLowerCase(Locale.US)) + ".molten";
                Fluid fluid = new Fluid(fluidName).setLuminosity(15).setDensity(3000).setViscosity(6000).setTemperature(1300);

                boolean registered = FluidRegistry.registerFluid(fluid);

                String registryName = metal.getName().toLowerCase(Locale.US);
                registryName = registryName.trim().replace(" ", ".") + ".molten";

                String unlocalizedName = Metallurgy.MODID.toLowerCase(Locale.US) + "." + registryName;

                String moltenTexture = Metallurgy.MODID.toLowerCase(Locale.US) + ":";
                moltenTexture += this.name.replace(" ", "_").toLowerCase(Locale.US) + "/";
                moltenTexture += metal.getName().trim().replace(" ", "_").toLowerCase(Locale.US);

                MoltenMetalBlock moltenMetal = new MoltenMetalBlock(fluid, unlocalizedName, moltenTexture);

                GameRegistry.registerBlock(moltenMetal, registryName);
                if (!registered)
                {
                    Fluid registeredFluid = FluidRegistry.getFluid(fluidName);
                    Block registeredFluidBlock = registeredFluid.getBlock();
                    if (registeredFluidBlock == null)
                    {
                        fluid.setBlock(moltenMetal);
                    }
                    else
                    {
                        moltenMetal.disableWritingFluidIcons();
                    }
                }
            }

            if (metal.alloyRecipe != null && metal.alloyRecipe.length == 2)
            {
                String ore1 = metal.alloyRecipe[0];
                String ore2 = metal.alloyRecipe[1];

                ore1 = ore1.replace(" ", "");
                ore2 = ore2.replace(" ", "");

                ore1 = "dust" + ore1;
                ore2 = "dust" + ore2;

                Utils.alloys.put(tag, new String[] { ore1, ore2 });
                Utils.requireAlloyer.put(tag, metal.isAlloyerRequired());
            }

            // Tools and weapons
            //don't need an ingot, we need a recipe element
            if (recipeElement != null && metal.haveTools())
            {
                String statsName = metal.getName().toUpperCase(Locale.US);
                statsName = statsName.replace(" ", "_");

                boolean weaponEnabled = ConfigHandler.weaponsEnabled(statsName.toLowerCase(Locale.US));

                int harvestLevel = metal.getToolHarvestLevel();
                int maxUses = metal.getToolDurability();
                int efficiency = metal.getToolEfficiency();
                int damage = metal.getToolDamage();
                int enchantability = metal.getToolEncantabilty();

                int[] stats = { harvestLevel, maxUses, efficiency, damage, enchantability };

                stats = ConfigHandler.toolsStats(statsName.toLowerCase(Locale.US), stats);

                harvestLevel = stats[0];
                maxUses = stats[1];
                efficiency = stats[2];
                damage = stats[3];
                enchantability = stats[4];

                Item.ToolMaterial toolMaterial = EnumHelper.addToolMaterial(statsName, harvestLevel, maxUses, efficiency, damage, enchantability);

                toolMaterial.setRepairItem(recipeElement);

                toolMaterials.put(metal.getName(), toolMaterial);

                // Unlocalized Name
                String toolUName = metal.getName().toLowerCase(Locale.US);
                toolUName = toolUName.replace(" ", ".");

                // Axe
                String axeTexture = texture + "_" + "axe";
                String axeUName = toolUName + ".axe";

                Axe axe = new Axe(toolMaterial, axeUName, axeTexture);
                axe.setHarvestLevel("axe", harvestLevel);
                GameRegistry.registerItem(axe, axeUName);
                if (weaponEnabled)
                {
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(axe), new Object[] { "ii ", "is ", " s ", 'i', recipeOreDict, 's', "stickWood" }));
                }
                this.axeStacks.put(metal.getName(), new ItemStack(axe));

                // Hoe
                String hoeTexture = texture + "_" + "hoe";
                String hoeUName = toolUName + ".hoe";

                Hoe hoe = new Hoe(toolMaterial, hoeUName, hoeTexture);
                hoe.setHarvestLevel("hoe", harvestLevel);
                GameRegistry.registerItem(hoe, hoeUName);
                if (weaponEnabled)
                {
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(hoe), new Object[] { "ii ", " s ", " s ", 'i', recipeOreDict, 's', "stickWood" }));
                }
                this.hoeStacks.put(metal.getName(), new ItemStack(hoe));

                // Pickaxe
                String pickaxeTexture = texture + "_" + "pick";
                String pickaxeUName = toolUName + ".pickaxe";

                Pickaxe pickaxe = new Pickaxe(toolMaterial, pickaxeUName, pickaxeTexture);
                pickaxe.setHarvestLevel("pickaxe", harvestLevel);
                GameRegistry.registerItem(pickaxe, pickaxeUName);
                if (weaponEnabled)
                {
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pickaxe), new Object[] { "iii", " s ", " s ", 'i', recipeOreDict, 's', "stickWood" }));
                }
                this.pickaxeStacks.put(metal.getName(), new ItemStack(pickaxe));

                // Shovel
                String shovelTexture = texture + "_" + "shovel";
                String shovelUName = toolUName + ".shovel";

                Shovel shovel = new Shovel(toolMaterial, shovelUName, shovelTexture);
                shovel.setHarvestLevel("shovel", harvestLevel);
                GameRegistry.registerItem(shovel, shovelUName);
                if (weaponEnabled)
                {
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(shovel), new Object[] { "i", "s", "s", 'i', recipeOreDict, 's', "stickWood" }));
                }
                this.shovelStacks.put(metal.getName(), new ItemStack(shovel));

                // Sword
                String swordTexture = texture + "_" + "sword";
                String swordUName = toolUName + ".sword";

                Sword sword = new Sword(toolMaterial, swordUName, swordTexture);

                if (metal.haveEntityEffects())
                {
                    boolean enableEffect = ConfigHandler.swordEffectEnabled(statsName.toLowerCase(Locale.US));

                    if (enableEffect)
                    {
                        int effectId = metal.getEffectId();
                        int effectDura = metal.getEffectDuration();
                        int effectAmp = metal.getEffectAmplifier();
                        int effectRec = metal.getEffectReceiver();

                        sword.addEffect(effectId, effectDura, effectAmp, effectRec);
                    }
                }

                GameRegistry.registerItem(sword, swordUName);
                if (weaponEnabled)
                {
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sword), new Object[] { "i", "i", "s", 'i', recipeOreDict, 's', "stickWood" }));
                }
                this.swordStacks.put(metal.getName(), new ItemStack(sword));
            }

            // Armor
            if (recipeElement != null && metal.haveArmor())
            {
                String modelTexture = metal.getName().replace(" ", "_").toLowerCase(Locale.US);
                modelTexture = this.name.toLowerCase(Locale.US) + "/" + modelTexture;

                String armorUName = metal.getName().toLowerCase(Locale.US);
                armorUName = armorUName.replace(" ", ".");
                armorUName = Metallurgy.MODID.toLowerCase(Locale.US) + "." + armorUName;

                String armorConfName = metal.getName().toLowerCase(Locale.US).replace(" ", "_");
                boolean armorEnabled = ConfigHandler.armorEnabled(armorConfName);

                int mutiplier = metal.getArmorMultiplier();
                int[] damageReduction = metal.getArmorDamageReduction();
                int enchantablilty = metal.getArmorEnchantability();

                int[] stats = { mutiplier, damageReduction[0], damageReduction[1], damageReduction[2], damageReduction[3], enchantablilty };

                stats = ConfigHandler.armourStats(armorConfName, stats);

                mutiplier = stats[0];
                damageReduction = new int[] { stats[1], stats[2], stats[3], stats[4] };
                enchantablilty = stats[5];

                ItemArmor.ArmorMaterial armorMaterial = EnumHelper.addArmorMaterial(armorUName, mutiplier, damageReduction, enchantablilty);

                armorMaterial.customCraftingMaterial = recipeElement.getItem();

                armorMaterials.put(metal.getName(), armorMaterial);

                // Set default model texture to diamond, model texture will be
                // overridden by the class.
                int renderIndex = 3;

                // Helmet
                String helmetIconTexture = texture + "_helmet";
                String helmetUName = armorUName + ".helmet";

                ItemMetallurgyArmor helmet = new ItemMetallurgyArmor(armorMaterial, renderIndex, 0, modelTexture);
                helmet = (ItemMetallurgyArmor) helmet.setUnlocalizedName(helmetUName);
                helmet = (ItemMetallurgyArmor) helmet.setTextureName(helmetIconTexture);
                GameRegistry.registerItem(helmet, helmetUName);
                if (armorEnabled)
                {
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(helmet), new Object[] { "iii", "i i", 'i', recipeOreDict }));
                }
                this.helmetStacks.put(metal.getName(), new ItemStack(helmet));

                // Chestplate
                String chestplateIconTexture = texture + "_chest";
                String chestplateUName = armorUName + ".chestplate";

                ItemMetallurgyArmor chestplate = new ItemMetallurgyArmor(armorMaterial, renderIndex, 1, modelTexture);
                chestplate = (ItemMetallurgyArmor) chestplate.setUnlocalizedName(chestplateUName);
                chestplate = (ItemMetallurgyArmor) chestplate.setTextureName(chestplateIconTexture);
                GameRegistry.registerItem(chestplate, chestplateUName);
                if (armorEnabled)
                {
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chestplate), new Object[] { "i i", "iii", "iii", 'i', recipeOreDict }));
                }
                this.chestplateStacks.put(metal.getName(), new ItemStack(chestplate));

                // Leggings
                String leggingsIconTexture = texture + "_legs";
                String leggingsUName = armorUName + ".leggings";

                ItemMetallurgyArmor leggings = new ItemMetallurgyArmor(armorMaterial, renderIndex, 2, modelTexture);
                leggings = (ItemMetallurgyArmor) leggings.setUnlocalizedName(leggingsUName);
                leggings = (ItemMetallurgyArmor) leggings.setTextureName(leggingsIconTexture);
                GameRegistry.registerItem(leggings, leggingsUName);
                if (armorEnabled)
                {
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(leggings), new Object[] { "iii", "i i", "i i", 'i', recipeOreDict }));
                }
                this.leggingsStacks.put(metal.getName(), new ItemStack(leggings));

                // Boots
                String bootsIconTexture = texture + "_boots";
                String bootsUName = armorUName + ".boots";

                ItemMetallurgyArmor boots = new ItemMetallurgyArmor(armorMaterial, renderIndex, 3, modelTexture);
                boots = (ItemMetallurgyArmor) boots.setUnlocalizedName(bootsUName);
                boots = (ItemMetallurgyArmor) boots.setTextureName(bootsIconTexture);
                GameRegistry.registerItem(boots, bootsUName);
                if (armorEnabled)
                {
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(boots), new Object[] { "i i", "i i", 'i', recipeOreDict }));
                }
                this.bootsStacks.put(metal.getName(), new ItemStack(boots));
            }

            // Shears and Buckets
            boolean shearsEnabled = ConfigHandler.recipeEnabled("shears");
            boolean bucketsEnabled = ConfigHandler.recipeEnabled("buckets");
            if (ingot != null)
            {
                String ingotOreDicName = "ingot" + tag;

                if (shearsEnabled)
                {
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.shears), new Object[] { " i", "i ", 'i', ingotOreDicName }));
                }

                if (bucketsEnabled)
                {
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.bucket), new Object[] { "i i", " i ", 'i', ingotOreDicName }));
                }

                // Furnace Recipes
                GameRegistry.addSmelting(new ItemStack(dust, 1, metaId), new ItemStack(ingot), 0.7F);

                if (ore != null)
                {
                    GameRegistry.addSmelting(new ItemStack(ore, 1, metaId), new ItemStack(ingot), 0.7F);
                }
                
                // Abstractor Recipes
                if (metal.type != MetalType.Drop)
                {
                    AbstractorRecipes.getInstance().addBase(new ItemStack(ingot), metal.abstractorXp);
                }
            }
        }
    }
}
