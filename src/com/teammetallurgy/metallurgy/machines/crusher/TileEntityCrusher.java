package com.teammetallurgy.metallurgy.machines.crusher;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

import com.teammetallurgy.metallurgy.ItemList;
import com.teammetallurgy.metallurgy.items.ItemDrawer;
import com.teammetallurgy.metallurgy.recipes.CrusherRecipes;
import com.teammetallurgy.metallurgycore.machines.TileEntityMetallurgySided;

public class TileEntityCrusher extends TileEntityMetallurgySided
{

    protected static final int INPUT_SLOT = 0;
    protected static final int FUEL_SLOT = 1;
    protected static final int OUTPUT_START = 2;
    protected static final int OUTPUT_END = 4;
    protected static final int DRAWER_INPUT = 5;
    protected static final int DRAWER_OUTPUT = 6;

    public TileEntityCrusher()
    {
        super(6, new int[] { TileEntityCrusher.INPUT_SLOT, TileEntityCrusher.FUEL_SLOT }, new int[] { TileEntityCrusher.INPUT_SLOT, TileEntityCrusher.FUEL_SLOT }, new int[] {
                TileEntityCrusher.FUEL_SLOT, TileEntityCrusher.OUTPUT_START, 3, TileEntityCrusher.OUTPUT_END });
    }

    @Override
    protected boolean canProcessItem()
    {
        ItemStack inputStack = getStackInSlot(TileEntityCrusher.INPUT_SLOT);

        ItemStack drawerInput = getStackInSlot(TileEntityCrusher.DRAWER_INPUT);
        if (drawerInput != null)
        {
            ItemStack firstStack = getDrawerItem(getStackInSlot(TileEntityCrusher.DRAWER_INPUT));

            if (firstStack != null) { return canProcessItem(firstStack); }
        }

        if (inputStack == null)
        {
            return false;
        }
        else
        {
            return canProcessItem(inputStack);
        }
    }

    protected boolean canProcessItem(ItemStack inputStack)
    {
        ItemStack outputStack = this.getSmeltingResult(inputStack);
        if (outputStack == null) { return false; }
        if (this.slotsAreEmtpty(TileEntityCrusher.OUTPUT_START, TileEntityCrusher.OUTPUT_END)) { return true; }
        return this.canAcceptStackRange(TileEntityCrusher.OUTPUT_START, TileEntityCrusher.OUTPUT_END, outputStack);
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public String getInvName()
    {
        return "container.crusher";
    }

    @Override
    protected ItemStack getSmeltingResult(ItemStack... itemStack)
    {
        return CrusherRecipes.getInstance().getCrushingResult(itemStack[0]);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        if (i >= TileEntityCrusher.DRAWER_INPUT && i <= TileEntityCrusher.DRAWER_OUTPUT) { return itemstack.getItem() == ItemList.drawer; }

        return i >= TileEntityCrusher.OUTPUT_START && i <= TileEntityCrusher.OUTPUT_END ? false : i == TileEntityCrusher.FUEL_SLOT ? TileEntityFurnace.isItemFuel(itemstack) : true;
    }

    @Override
    protected void processItem()
    {
        if (this.canProcessItem())
        {
            ItemStack inputStack = getStackInSlot(TileEntityCrusher.INPUT_SLOT);
            ItemStack itemstack = this.getSmeltingResult(inputStack);

            if (itemstack == null)
            {
                itemstack = this.getSmeltingResult(getDrawerItem(getStackInSlot(TileEntityCrusher.DRAWER_INPUT)));
            }

            for (int i = TileEntityCrusher.OUTPUT_START; i <= TileEntityCrusher.OUTPUT_END; i++)
            {
                boolean handled = false;
                ItemStack stackInSlot = getStackInSlot(i);
                if (stackInSlot == null)
                {
                    setInventorySlotContents(i, itemstack.copy());
                }
                else if (stackInSlot.isItemEqual(itemstack))
                {
                    stackInSlot.stackSize += itemstack.stackSize;
                }

                if (handled)
                {
                    break;
                }
            }

            if (inputStack != null)
            {
                --inputStack.stackSize;

                if (inputStack.stackSize <= TileEntityCrusher.INPUT_SLOT)
                {
                    this.itemStacks[TileEntityCrusher.INPUT_SLOT] = null;
                }
            }
            else
            {
                ItemStack firstStack = getDrawerItem(getStackInSlot(TileEntityCrusher.DRAWER_INPUT));

                --firstStack.stackSize;

                if (firstStack.stackSize <= TileEntityCrusher.INPUT_SLOT)
                {
                    firstStack = null;
                }

                setInventorySlotContents(TileEntityCrusher.DRAWER_INPUT, ItemDrawer.writeFirstStack(getStackInSlot(TileEntityCrusher.DRAWER_INPUT), firstStack));
            }
        }
    }

    private ItemStack getDrawerItem(ItemStack itemStack)
    {
        return ItemDrawer.getFirstStack(getStackInSlot(TileEntityCrusher.DRAWER_INPUT));
    }
}
