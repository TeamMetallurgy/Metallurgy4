package com.teammetallurgy.metallurgy.machines.alloyer;

import net.minecraft.item.ItemStack;

import com.teammetallurgy.metallurgy.recipes.AlloyerRecipes;
import com.teammetallurgy.metallurgycore.machines.TileEntityMetallurgySided;

public class TileEntityAlloyer extends TileEntityMetallurgySided
{

    private static final int FUEL_SLOT = 1;

    public TileEntityAlloyer()
    {
        super(4, new int[] { 0, 2 }, new int[] { TileEntityAlloyer.FUEL_SLOT }, new int[] { TileEntityAlloyer.FUEL_SLOT, 3 });
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public String getInventoryName()
    {
        return "container.alloyer";
    }

    @Override
    public ItemStack getSmeltingResult(ItemStack... itemStack)
    {
        return AlloyerRecipes.getInstance().getAlloyResult(itemStack[0], itemStack[1]);
    }

    @Override
    public int[] getInputSlots()
    {
        return new int[] { 0, 2 };
    }

    @Override
    public int[] getOutputSlots()
    {
        return new int[] { 3 };
    }

    @Override
    protected int getFuelSlot()
    {
        return TileEntityAlloyer.FUEL_SLOT;
    }

}
