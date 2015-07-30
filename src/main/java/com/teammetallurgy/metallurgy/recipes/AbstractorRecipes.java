package com.teammetallurgy.metallurgy.recipes;

import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;

public class AbstractorRecipes
{
    private static final AbstractorRecipes instance = new AbstractorRecipes();
    
    public HashMap<ItemStack, Float> baseEssenceList = new HashMap<ItemStack, Float>();
    public HashMap<ItemStack, Float> catalystMultiplierList = new HashMap<ItemStack, Float>();
    public HashMap<ItemStack, Float> bottleConsumedEssanceList = new HashMap<ItemStack, Float>();
    public HashMap<ItemStack, ItemStack> filledBottleList = new HashMap<ItemStack, ItemStack>();
    
    public static AbstractorRecipes getInstance ()
    {
        return AbstractorRecipes.instance;
    }
    
    public void addBase (ItemStack base, float essence)
    {
        baseEssenceList.put(base, Float.valueOf(essence));
    }
    
    public void addCatalyst (ItemStack catalyst, float multiplier)
    {
        catalystMultiplierList.put(catalyst, multiplier);
    }
    
    public void addBottle(ItemStack emptyBottle, ItemStack filledBottle, float consumedEssance)
    {
        bottleConsumedEssanceList.put(emptyBottle, Float.valueOf(consumedEssance));
        filledBottleList.put(emptyBottle, filledBottle);
    }
    
    public float getEssance (ItemStack catalyst, ItemStack base)
    {
        if (catalyst == null || catalyst.getItem() == null || base == null || base.getItem() == null)
            return 0F;
        
        Float multiplier = null;
        Float essance = null;
        
        for(Entry<ItemStack, Float> entry : catalystMultiplierList.entrySet())
        {
            ItemStack keyItemStack = entry.getKey();
            
            if (areItemStacksEqual(keyItemStack, catalyst))
            {
                multiplier = entry.getValue();
                break;
            }
        }
        
        if (multiplier == null)
            return 0;
        
        for(Entry<ItemStack, Float> entry : baseEssenceList.entrySet())
        {
            ItemStack keyItemStack = entry.getKey();
            
            if (areItemStacksEqual (keyItemStack, base))
            {
                essance = entry.getValue();
                break;
            }
        }
        
        if (essance == null)
            return 0;
        
        return multiplier.floatValue() * essance.floatValue();
        
    }
    
    public ItemStack getFilledBottle (ItemStack emptyBottle)
    {
        if (emptyBottle == null || emptyBottle.getItem() == null)
            return null;
        
        for (Entry<ItemStack, ItemStack> entry : filledBottleList.entrySet())
        {
            ItemStack keyItemStack = entry.getKey();
            
            if (areItemStacksEqual (keyItemStack, emptyBottle))
            {
                return entry.getValue().copy();
            }
        }
        
        return null;
    }
    
    public float essanceRequiredToFill (ItemStack emptyBottle)
    {
        if (emptyBottle == null || emptyBottle.getItem() == null)
            return 0F;
        
        for (Entry<ItemStack, Float> entry : bottleConsumedEssanceList.entrySet())
        {
            ItemStack keyItemStack = entry.getKey();
            
            if (areItemStacksEqual(keyItemStack, emptyBottle))
            {
                return entry.getValue().floatValue();
            }
        }
        
        return 0F;
    }
    
    private boolean areItemStacksEqual(ItemStack firstStack, ItemStack secondStack)
    {
        return (firstStack.getItem() == secondStack.getItem() && firstStack.getItemDamage() == secondStack.getItemDamage()); 
    }
}
