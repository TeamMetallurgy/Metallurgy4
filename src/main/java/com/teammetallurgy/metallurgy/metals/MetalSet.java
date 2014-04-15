package com.teammetallurgy.metallurgy.metals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
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

    private HashMap<String, ItemStack> axeStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> hoeStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> pickaxeStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> shovelStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> swordStacks = new HashMap<String, ItemStack>();

    private HashMap<String, ItemStack> helmetStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> chestplateStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> leggingsStacks = new HashMap<String, ItemStack>();
    private HashMap<String, ItemStack> bootsStacks = new HashMap<String, ItemStack>();

    public MetalSet(String setName)
    {
        this.name = setName;
        this.setTag = this.name.substring(0, 1).toUpperCase() + this.name.substring(1);
        this.initDefaults();
    }

    private MetalBlock createBlock(MetalBlock metalBlock, int meta, int harvestLvl, String metalTag, String identifier)
    {

        metalBlock.setHarvestLevel("pickaxe", harvestLvl, meta);

        if (meta == 0)
        {
            GameRegistry.registerBlock(metalBlock, ItemMetalBlock.class, this.name + "." + identifier);
        }

        OreDictionary.registerOre(identifier + metalTag, new ItemStack(metalBlock, 1, meta));

        return metalBlock;
    }

    private MetalItem createItem(MetalItem metalItem, int meta, String metalTag, String identifier)
    {

        if (meta == 0)
        {
            GameRegistry.registerItem(metalItem, this.name + "." + identifier);
        }

        OreDictionary.registerOre(identifier + metalTag, new ItemStack(metalItem, 1, meta));

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

    private void initDefaults()
    {
        String postfix = this.name.toLowerCase();
        postfix = postfix.replace(" ", ".");

        this.defaultOre = new MetalBlock(postfix + ".ore");
        this.defaultBlock = new MetalBlock(postfix + ".block");
        this.defaultBricks = new MetalBlock(postfix + ".brick");

        this.defaultDust = new MetalItem(postfix + ".dust");
        this.defaultDrops = new MetalItem(postfix + ".item");
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

            int metaId = metal.meta;

            if (metal.type != Metal.MetalType.Alloy)
            {
                String identifier = "ore";

                if (metal.type != Metal.MetalType.Respawn)
                {
                    ore = this.createBlock(this.defaultOre, metaId, metal.blockLvl, tag, identifier);

                    item = this.defaultDrops;

                    if (metal.type == Metal.MetalType.Drop)
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

                    this.oreStacks.put(tag, new ItemStack(ore, 1, metaId));

                }

                if (ConfigHandler.generates(tag))
                {
                    WorldGenMetals worldGen = new WorldGenMetals(ore, metaId, metal.generation, metal.dimensions);

                    GameRegistry.registerWorldGenerator(worldGen, 5);
                }
            }

            if (metal.type != Metal.MetalType.Drop && metal.type != Metal.MetalType.Respawn)
            {
                String identifier = "block";

                block = this.createBlock(this.defaultBlock, metaId, metal.blockLvl, tag, identifier);
                block.addSubBlock(metaId, metal.getName(), 1, texture + "_" + identifier);

                this.blockStacks.put(tag, new ItemStack(block, 1, metaId));
            }

            if (metal.type != Metal.MetalType.Drop)
            {
                String identifier = "ingot";

                ingot = new MetalItem(configTag + "." + identifier);
                ingot.addSubItem(0, metal.getName(), 1, texture + "_" + identifier);

                OreDictionary.registerOre(identifier + tag, new ItemStack(ingot, 1, 0));

                String registryName = metal.getName().toLowerCase();
                registryName = registryName.replace(" ", ".");
                registryName = registryName + "." + identifier;

                GameRegistry.registerItem(ingot, registryName);
                this.ingotStacks.put(tag, new ItemStack(ingot));

            }

            if (metal.type != Metal.MetalType.Drop && metal.type != Metal.MetalType.Respawn)
            {
                String identifier = "brick";

                brick = this.createBlock(this.defaultBricks, metaId, metal.blockLvl, tag, identifier);
                brick.addSubBlock(metaId, metal.getName(), 2, texture + "_" + identifier);
                this.brickStacks.put(metal.getName(), new ItemStack(brick, 1, metaId));

                GameRegistry.addShapedRecipe(new ItemStack(brick, 1, metaId), new Object[] { "ii", "ii", 'i', ingot });
                GameRegistry.addShapelessRecipe(new ItemStack(ingot, 4), new ItemStack(brick, 1, metaId));

            }

            if (metal.type != Metal.MetalType.Drop)
            {
                String identifier = "dust";

                dust = this.createItem(this.defaultDust, metaId, tag, identifier);
                dust.addSubItem(metaId, metal.getName(), 0, texture + "_" + identifier);
                this.dustStacks.put(tag, new ItemStack(dust, 1, metaId));

            }

            if (metal.type == Metal.MetalType.Drop)
            {
                String identifier = "item";

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
                this.dropStacks.put(tag, new ItemStack(item, 1, metaId));

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

                // Axe
                String axeTexture = texture + "_" + "axe";
                String axeUName = toolUName + ".axe";

                Axe axe = new Axe(toolMaterial, axeUName, axeTexture);
                axe.setHarvestLevel("axe", harvestLevel);
                GameRegistry.registerItem(axe, axeUName);
                GameRegistry.addRecipe(new ItemStack(axe), new Object[] { "iii", "is ", " s ", 'i', ingot, 's', Items.stick });
                this.axeStacks.put(tag, new ItemStack(axe));

                // Hoe
                String hoeTexture = texture + "_" + "hoe";
                String hoeUName = toolUName + ".hoe";

                Hoe hoe = new Hoe(toolMaterial, hoeUName, hoeTexture);
                hoe.setHarvestLevel("hoe", harvestLevel);
                GameRegistry.registerItem(hoe, hoeUName);
                GameRegistry.addRecipe(new ItemStack(hoe), new Object[] { "ii ", " s ", " s ", 'i', ingot, 's', Items.stick });
                this.hoeStacks.put(tag, new ItemStack(hoe));

                // Pickaxe
                String pickaxeTexture = texture + "_" + "pick";
                String pickaxeUName = toolUName + ".pickaxe";

                Pickaxe pickaxe = new Pickaxe(toolMaterial, pickaxeUName, pickaxeTexture);
                pickaxe.setHarvestLevel("pickaxe", harvestLevel);
                GameRegistry.registerItem(pickaxe, pickaxeUName);
                GameRegistry.addRecipe(new ItemStack(pickaxe), new Object[] { "iii", " s ", " s ", 'i', ingot, 's', Items.stick });
                this.pickaxeStacks.put(tag, new ItemStack(pickaxe));

                // Shovel
                String shovelTexture = texture + "_" + "shovel";
                String shovelUName = toolUName + ".shovel";

                Shovel shovel = new Shovel(toolMaterial, shovelUName, shovelTexture);
                shovel.setHarvestLevel("shovel", harvestLevel);
                GameRegistry.registerItem(shovel, shovelUName);
                GameRegistry.addRecipe(new ItemStack(shovel), new Object[] { "i", "s", "s", 'i', ingot, 's', Items.stick });
                this.shovelStacks.put(tag, new ItemStack(shovel));

                // Sword
                String swordTexture = texture + "_" + "sword";
                String swordUName = toolUName + ".sword";

                Sword sword = new Sword(toolMaterial, swordUName, swordTexture);

                if (metal.haveEntityEffects())
                {
                    int effectId = metal.getEffectId();
                    int effectDura = metal.getEffectDuration();
                    int effectAmp = metal.getEffectAmplifier();
                    int effectRec = metal.getEffectReceiver();

                    sword.addEffect(effectId, effectDura, effectAmp, effectRec);
                }

                GameRegistry.registerItem(sword, swordUName);
                GameRegistry.addRecipe(new ItemStack(sword), new Object[] { "i", "i", "s", 'i', ingot, 's', Items.stick });
                this.swordStacks.put(tag, new ItemStack(sword));
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

                // Helmet
                String helmetIconTexture = texture + "_helmet";
                String helmetUName = armorUName + ".helmet";

                ItemMetallurgyArmor helmet = new ItemMetallurgyArmor(armorMaterial, renderIndex, 0, modelTexture);
                helmet = (ItemMetallurgyArmor) helmet.setUnlocalizedName(helmetUName);
                helmet = (ItemMetallurgyArmor) helmet.setTextureName(helmetIconTexture);
                GameRegistry.registerItem(helmet, helmetUName);
                GameRegistry.addRecipe(new ItemStack(helmet), new Object[] { "iii", "i i", 'i', ingot });
                this.helmetStacks.put(tag, new ItemStack(helmet));

                // Chestplate
                String chestplateIconTexture = texture + "_chest";
                String chestplateUName = armorUName + ".chestplate";

                ItemMetallurgyArmor chestplate = new ItemMetallurgyArmor(armorMaterial, renderIndex, 1, modelTexture);
                chestplate = (ItemMetallurgyArmor) chestplate.setUnlocalizedName(chestplateUName);
                chestplate = (ItemMetallurgyArmor) chestplate.setTextureName(chestplateIconTexture);
                GameRegistry.registerItem(chestplate, chestplateUName);
                GameRegistry.addRecipe(new ItemStack(chestplate), new Object[] { "i i", "iii", "iii", 'i', ingot });
                this.chestplateStacks.put(tag, new ItemStack(chestplate));

                // Leggings
                String leggingsIconTexture = texture + "_legs";
                String leggingsUName = armorUName + ".leggings";

                ItemMetallurgyArmor leggings = new ItemMetallurgyArmor(armorMaterial, renderIndex, 2, modelTexture);
                leggings = (ItemMetallurgyArmor) leggings.setUnlocalizedName(leggingsUName);
                leggings = (ItemMetallurgyArmor) leggings.setTextureName(leggingsIconTexture);
                GameRegistry.registerItem(leggings, leggingsUName);
                GameRegistry.addRecipe(new ItemStack(leggings), new Object[] { "iii", "i i", "i i", 'i', ingot });
                this.leggingsStacks.put(tag, new ItemStack(leggings));

                // Boots
                String bootsIconTexture = texture + "_boots";
                String bootsUName = armorUName + ".boots";

                ItemMetallurgyArmor boots = new ItemMetallurgyArmor(armorMaterial, renderIndex, 3, modelTexture);
                boots = (ItemMetallurgyArmor) boots.setUnlocalizedName(bootsUName);
                boots = (ItemMetallurgyArmor) boots.setTextureName(bootsIconTexture);
                GameRegistry.registerItem(boots, bootsUName);
                GameRegistry.addRecipe(new ItemStack(boots), new Object[] { "i i", "i i", 'i', ingot });
                this.bootsStacks.put(tag, new ItemStack(boots));
            }
        }
    }

}
