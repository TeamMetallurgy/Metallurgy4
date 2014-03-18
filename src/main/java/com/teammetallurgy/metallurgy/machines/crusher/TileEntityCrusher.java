package com.teammetallurgy.metallurgy.machines.crusher;

import net.minecraft.item.ItemStack;

import com.teammetallurgy.metallurgy.machines.TileEntityMetallurgySidedDrawers;
import com.teammetallurgy.metallurgy.recipes.CrusherRecipes;

public class TileEntityCrusher extends TileEntityMetallurgySidedDrawers
{

    private static final int INPUT_SLOT = 0;
    private static final int FUEL_SLOT = 1;
    private static final int OUTPUT_START = 2;
    private static final int OUTPUT_END = 4;
    private static final int DRAWER_INPUT = 5;
    private static final int DRAWER_OUTPUT = 6;

    public TileEntityCrusher()
    {
        super(6, new int[] { INPUT_SLOT, TileEntityCrusher.FUEL_SLOT }, new int[] { INPUT_SLOT, TileEntityCrusher.FUEL_SLOT }, new int[] { TileEntityCrusher.FUEL_SLOT, TileEntityCrusher.OUTPUT_START, 3, TileEntityCrusher.OUTPUT_END });
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public String getInventoryName()
    {
        return "container.crusher";
    }

    @Override
    protected ItemStack getSmeltingResult(ItemStack... itemStack)
    {
        return CrusherRecipes.getInstance().getCrushingResult(itemStack[0]);
    }

    @Override
    protected int getFuelSlot()
    {
        return TileEntityCrusher.FUEL_SLOT;
    }

    @Override
    protected int[] getInputSlots()
    {
        return new int[] { TileEntityCrusher.INPUT_SLOT };
    }

    @Override
    public int[] getOutputSlots()
    {
        return new int[] { TileEntityCrusher.OUTPUT_START, 3, TileEntityCrusher.OUTPUT_END };
    }

    @Override
    protected int getDrawerInput()
    {
        return TileEntityCrusher.DRAWER_INPUT;
    }

    @Override
    protected int getDrawerOutput()
    {
        return TileEntityCrusher.DRAWER_OUTPUT;
    }
}
