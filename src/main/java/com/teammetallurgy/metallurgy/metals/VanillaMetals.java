package com.teammetallurgy.metallurgy.metals;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.teammetallurgy.metallurgy.Metallurgy;
import com.teammetallurgy.metallurgy.recipes.AbstractorRecipes;
import com.teammetallurgy.metallurgy.recipes.CrusherRecipes;

import cpw.mods.fml.common.registry.GameRegistry;

public class VanillaMetals
{
    public static MetalItem vanillaDust;
    public static MetalBlock vanillaBricks;
    private static String texturePrefix = Metallurgy.MODID + ":vanilla/";
    
    public static void initItems()
    {
        
        vanillaDust = new MetalItem("vanilla.dust");
        vanillaDust.addSubItem(0, "gold", 0, texturePrefix + "gold_dust");
        vanillaDust.addSubItem(1, "iron", 0, texturePrefix + "iron_dust");
        
        GameRegistry.registerItem(vanillaDust, "vanilla.dust");
        
        OreDictionary.registerOre("dustGold", new ItemStack(vanillaDust,1,0));
        OreDictionary.registerOre("dustIron", new ItemStack(vanillaDust,1,1));
    }
    
    public static void initBlocks()
    {
        vanillaBricks = new MetalBlock("vanilla.brick");
        vanillaBricks.addSubBlock(0, "gold", 2, texturePrefix + "gold_brick");
        vanillaBricks.addSubBlock(1, "iron", 2, texturePrefix + "iron_brick");
        
        vanillaBricks.setHarvestLevel("pickaxe", 2, 0);
        vanillaBricks.setHarvestLevel("pickaxe", 1, 1);
        
        GameRegistry.registerBlock(vanillaBricks, ItemMetalBlock.class, "vanilla.brick");
        
        ItemStack goldBricksStack = new ItemStack(vanillaBricks,1,0);
        ItemStack ironBricksStack = new ItemStack(vanillaBricks,1,1);
        
        OreDictionary.registerOre("brickGold", goldBricksStack.copy());
        OreDictionary.registerOre("brickIron", ironBricksStack.copy());
        
    }

    public static void initRecipes()
    {
        
        ItemStack goldBricksStack = new ItemStack(vanillaBricks,1,0);
        ItemStack ironBricksStack = new ItemStack(vanillaBricks,1,1);
        
        GameRegistry.addShapedRecipe(goldBricksStack.copy(), new Object[] { "ii", "ii", 'i', Items.gold_ingot });
        GameRegistry.addShapedRecipe(ironBricksStack.copy(), new Object[] { "ii", "ii", 'i', Items.iron_ingot });
        
        GameRegistry.addShapelessRecipe(new ItemStack(Items.gold_ingot, 4), goldBricksStack.copy());
        GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 4), ironBricksStack.copy());
        
        
        CrusherRecipes.getInstance().addRecipe(new ItemStack(Items.gold_ingot), new ItemStack(vanillaDust,1,0));
        CrusherRecipes.getInstance().addRecipe(new ItemStack(Items.iron_ingot), new ItemStack(vanillaDust,1,1));
        
        GameRegistry.addSmelting(new ItemStack(vanillaDust,1,0), new ItemStack(Items.gold_ingot), 0.7F);
        GameRegistry.addSmelting(new ItemStack(vanillaDust,1,1), new ItemStack(Items.iron_ingot), 0.7F);
        
        AbstractorRecipes.getInstance().addBase(new ItemStack(Items.iron_ingot), 3);
        AbstractorRecipes.getInstance().addBase(new ItemStack(Items.gold_ingot), 9);
    }
    
}
