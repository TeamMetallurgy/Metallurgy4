package com.teammetallurgy.metallurgy;

import java.util.HashMap;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.teammetallurgy.metallurgy.recipes.AlloyerRecipes;
import com.teammetallurgy.metallurgy.recipes.CrusherRecipes;

import cpw.mods.fml.common.registry.GameRegistry;

public class Utils
{
    public static HashMap<String, String[]> alloys = new HashMap<String, String[]>();
    public static HashMap<String, Boolean> requireAlloyer = new HashMap<String, Boolean>();

    private static void injectAlloyRecipe(String tag, String[] materials)
    {
        String ore1 = materials[0];
        String ore2 = materials[1];

        List<ItemStack> retList = OreDictionary.getOres(ore1);
        if (retList.size() > 0)
        {
            ItemStack itemStack = retList.get(0).copy();
            List<ItemStack> retList2 = OreDictionary.getOres(ore2);
            if (retList2.size() > 0)
            {
                ItemStack otherItemStack = retList2.get(0).copy();
                List<ItemStack> output = OreDictionary.getOres("dust" + tag);
                List<ItemStack> OutputIngot = OreDictionary.getOres("ingot" + tag);
                if (output.size() > 0)
                {
                    boolean isAlloyerRequired = Utils.requireAlloyer.get(tag);
                    ItemStack outputStack = output.get(0).copy();

                    outputStack.stackSize = 2;
                    if (!isAlloyerRequired)
                    {
                        GameRegistry.addRecipe(new ShapelessOreRecipe(outputStack, ore1, ore2));
                    }
                }

                if (OutputIngot.size() > 0)
                {
                    ItemStack outputStack = OutputIngot.get(0).copy();
                    outputStack.stackSize = 2;

                    AlloyerRecipes.getInstance().addRecipe(itemStack, otherItemStack, outputStack);
                }
            }
        }

    }

    private static void injectCrusherDustRecipe(ItemStack oreItem, String replacement, String dustOreDicName)
    {
        if (replacement.startsWith("dust")) { return; }

        List<ItemStack> retList = OreDictionary.getOres(dustOreDicName);
        if (retList.size() > 0)
        {
            ItemStack ret = retList.get(0).copy();
            if (replacement.equals("ore"))
            {
                ret.stackSize = 2;
            }
            CrusherRecipes.getInstance().addRecipe(oreItem, ret);
        }
    }

    public static void injectOreDictionaryRecipes()
    {
        for (String name : OreDictionary.getOreNames())
        {
            if (!name.startsWith("ore") && !name.startsWith("ingot"))
                continue;
            
            for (final ItemStack oreItem : OreDictionary.getOres(name))
            {

                String replacement = "";
                replacement = name.startsWith("ore") ? "ore" : replacement;
                replacement = name.startsWith("ingot") ? "ingot" : replacement;

                if (replacement.equals(""))
                {
                    continue;
                }

                Utils.injectCrusherDustRecipe(oreItem, replacement, name.replace(replacement, "dust"));
            }
        }

        for (String tag : Utils.alloys.keySet())
        {
            Utils.injectAlloyRecipe(tag, Utils.alloys.get(tag));
        }
    }

}
