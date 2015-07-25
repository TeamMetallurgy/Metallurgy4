package com.teammetallurgy.metallurgy.machines.abstractor;

import net.minecraft.item.ItemStack;

import com.teammetallurgy.metallurgycore.machines.TileEntityMetallurgySided;

public class TileEntityAbstractor extends TileEntityMetallurgySided
{
    private static final int FuelSlot = 0;
    private static int[] inputSlots = new int[] { 1,2,3 };
    private static int[] outputSlots = new int[] { 4 };

    public TileEntityAbstractor()
    {
        super(5, inputSlots, new int[] { FuelSlot }, outputSlots);
    }

    @Override
    protected int getFuelSlot()
    {
        return 0;
    }

    @Override
    protected int[] getInputSlots()
    {
        return inputSlots;
    }

    @Override
    public String getInventoryName()
    {
        return "container.abstractor";
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    protected int[] getOutputSlots()
    {
        return outputSlots;
    }

    @Override
    protected ItemStack getSmeltingResult(ItemStack... stacks)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
