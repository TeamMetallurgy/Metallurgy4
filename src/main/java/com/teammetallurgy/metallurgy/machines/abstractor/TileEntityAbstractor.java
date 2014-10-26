package com.teammetallurgy.metallurgy.machines.abstractor;

import net.minecraft.item.ItemStack;

import com.teammetallurgy.metallurgycore.machines.TileEntityMetallurgySided;

public class TileEntityAbstractor extends TileEntityMetallurgySided
{
    private static final int FuelSlot = 1;
    private static int[] inputSlots = new int[] { 0 };
    private static int[] outputSlots = new int[] { 2 };

    public TileEntityAbstractor()
    {
        super(3, inputSlots, new int[] { FuelSlot }, outputSlots);
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
