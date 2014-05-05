package com.teammetallurgy.metallurgy.recipes;

import java.util.HashMap;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CrusherRecipes
{
    private static CrusherRecipes instance = new CrusherRecipes();

    public static CrusherRecipes getInstance()
    {
        return CrusherRecipes.instance;
    }

    private final HashMap<String, ItemStack> metaList = new HashMap<String, ItemStack>();
    private final HashMap<String, ItemStack[]> inputList = new HashMap<String, ItemStack[]>();

    @Deprecated
    public void addRecipe(int itemID, int itemDamage, ItemStack itemStack)
    {
        this.addRecipe(new ItemStack(Item.getItemById(itemID), 1, itemDamage), itemStack);
    }

    public ItemStack getCrushingResult(ItemStack itemStack)
    {
        if (itemStack == null) { return null; }
        return this.metaList.get(itemStack.getUnlocalizedName());

    }

    public void addRecipe(ItemStack oreItem, ItemStack ret)
    {
        this.metaList.put(oreItem.getUnlocalizedName(), ret);

        ItemStack[] inputList = this.inputList.get(ret.getUnlocalizedName());

        if (inputList == null)
        {
            inputList = new ItemStack[1];
            inputList[0] = oreItem;
        }
        else
        {
            ItemStack[] newList = new ItemStack[inputList.length + 1];
            for (int i = 0; i < inputList.length; i++)
            {
                newList[i] = inputList[i];
            }

            newList[inputList.length] = oreItem;

            inputList = newList;
        }

        this.inputList.put(ret.getUnlocalizedName(), inputList);
    }

    public boolean hasUsage(ItemStack itemStack)
    {

        return metaList.containsKey(itemStack.getUnlocalizedName());

    }

    public HashMap<ItemStack, ItemStack> getInput(ItemStack itemStack)
    {

        if (itemStack == null) { return null; }

        HashMap<ItemStack, ItemStack> result = new HashMap<ItemStack, ItemStack>();

        ItemStack[] inputList = this.inputList.get(itemStack.getUnlocalizedName());

        if (inputList == null) { return null; }

        for (int i = 0; i < inputList.length; i++)
        {
            result.put(inputList[i], this.getCrushingResult(inputList[i]));
        }

        return result;
    }

}
