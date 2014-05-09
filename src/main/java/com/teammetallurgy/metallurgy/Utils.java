package com.teammetallurgy.metallurgy;

import java.util.HashMap;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;

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
                if (output.size() > 0)
                {
                    boolean isAlloyerRequired = Utils.requireAlloyer.get(tag);
                    ItemStack outputStack = output.get(0).copy();

                    outputStack.stackSize = 2;
                    if (!isAlloyerRequired)
                    {
                        GameRegistry.addShapelessRecipe(outputStack, itemStack, otherItemStack);
                    }

                    ItemStack copy = outputStack.copy();
                    copy.stackSize = 4;
                    AlloyerRecipes.getInstance().addRecipe(itemStack, otherItemStack, copy);
                }
            }
        }

    }

    private static void injectCrusherDustRecipe(ItemStack oreItem, String replacement, String name)
    {
        if (replacement.equals("dust")) { return; }

        List<ItemStack> retList = OreDictionary.getOres(name);
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

    private static void injectFurnaceDustRecipe(ItemStack oreItem, String replacement, String name)
    {

        if (replacement.equals("ingot")) { return; }

        List<ItemStack> retList = OreDictionary.getOres(name);
        if (retList.size() > 0)
        {
            ItemStack ret = retList.get(0).copy();
            FurnaceRecipes.smelting().func_151394_a(oreItem, ret, 0.7F);
        }
    }

    public static void injectOreDictionaryRecipes()
    {
        for (String name : OreDictionary.getOreNames())
        {
            for (final ItemStack oreItem : OreDictionary.getOres(name))
            {

                String replacement = "";
                replacement = name.contains("ore") ? "ore" : replacement;
                replacement = name.contains("ingot") ? "ingot" : replacement;
                replacement = name.contains("dust") ? "dust" : replacement;

                if (replacement.equals(""))
                {
                    continue;
                }

                Utils.injectCrusherDustRecipe(oreItem, replacement, name.replace(replacement, "dust"));
                Utils.injectFurnaceDustRecipe(oreItem, replacement, name.replace(replacement, "ingot"));
            }
        }

        for (String tag : Utils.alloys.keySet())
        {
            Utils.injectAlloyRecipe(tag, Utils.alloys.get(tag));
        }
    }

}
