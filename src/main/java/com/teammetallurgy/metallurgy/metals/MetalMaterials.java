package com.teammetallurgy.metallurgy.metals;

import java.util.ArrayList;

import com.teammetallurgy.metallurgy.handlers.ConfigHandler;
import com.teammetallurgy.metallurgycore.handlers.LogHandler;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class MetalMaterials
{
    public static MetalMaterials Instance = new MetalMaterials();

    public void addRecipes()
    {
        if (ConfigHandler.recipeEnabled("alternative_rails"))
            addRailRecipes();
        if (ConfigHandler.recipeEnabled("midasium_to_gold"))
            addMidasiumRecipes();
        if (ConfigHandler.recipeEnabled("alternative_iron_dust"))
            addIronDustRecipes();
        if (ConfigHandler.recipeEnabled("alternative_blaze_rod"))
            addBlazeRodRecipe();
        if (ConfigHandler.recipeEnabled("alternative_ender_perl"))
            addEnderPerlRecipe();
        if (ConfigHandler.recipeEnabled("alternative_gunpowder"))
            addGunpowderRecipe();
    }

    private void addRailRecipes()
    {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.rail, 4), "X X", "XSX", "X X", 'X', "ingotCopper", 'S', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.rail, 10), "X X", "XSX", "X X", 'X', "ingotBronze", 'S', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.rail, 14), "X X", "XSX", "X X", 'X', "ingotHepatizon", 'S', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.rail, 26), "X X", "XSX", "X X", 'X', "ingotDamascusSteel", 'S', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.rail, 32), "X X", "XSX", "X X", 'X', "ingotAngmallen", 'S', "stickWood"));

    }

    private void addMidasiumRecipes()
    {

        ArrayList<ItemStack> goldDusts = OreDictionary.getOres("dustGold");

        if (goldDusts.size() < 1)
        {
            LogHandler.log("Gold dust wasn't found in the ore dictionary, skipping adding Midasium recipes");
            return;
        }

        ItemStack dustGold = goldDusts.get(0);

        if (dustGold == null)
        {
            LogHandler.log("Gold dust wasn't found in the ore dictionary, skipping adding Midasium recipes");
            return;
        }

        final String[] ores = OreDictionary.getOreNames();
        for (final String name : ores)
        {
            if (name.contains("dust") && !name.toLowerCase().contains("tiny") && !name.toLowerCase().contains("clay") && !name.toLowerCase().contains("quartz")
                    && !name.toLowerCase().contentEquals("dustgold"))
            {
                // TODO: add finer logging
                // LogHandler.log("Adding recipe for " + name + " midasium = gold");
                GameRegistry.addRecipe(new ShapelessOreRecipe(dustGold.copy(), "dustMidasium", name));
            }
        }
    }

    private void addIronDustRecipes()
    {
        ArrayList<ItemStack> ironDusts = OreDictionary.getOres("dustIron");

        if (ironDusts.size() < 1)
        {
            LogHandler.log("Iron Dust wasn't found in the ore dictionary, skipping adding Iron dust coverting recipes");
            return;
        }
        ItemStack dustIron = ironDusts.get(0);

        if (dustIron == null)
        {
            LogHandler.log("Iron Dust wasn't found in the ore dictionary, skipping adding Iron dust coverting recipes");
            return;
        }

        dustIron = dustIron.copy();
        dustIron.stackSize = 2;

        GameRegistry.addRecipe(new ShapelessOreRecipe(dustIron.copy(), "dustShadowIron", "dustIgnatius"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(dustIron.copy(), "dustDeepIron", "dustPrometheum"));

    }

    private void addBlazeRodRecipe()
    {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.blaze_rod), "I", "I", 'I', "ingotVulcanite"));
    }

    private void addEnderPerlRecipe()
    {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.ender_pearl, 4), " E ", "E E", " E ", 'E', "ingotMeutoite"));
    }
    
    private void addGunpowderRecipe()
    {
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.gunpowder, 4), new ItemStack(Items.coal,1,1), "dustSulfur", "dustSaltpeter"));
    }
}
