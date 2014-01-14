package com.teammetallurgy.metallurgy.machines.alloyer;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

import com.teammetallurgy.metallurgy.machines.TileEntityMetallurgySided;
import com.teammetallurgy.metallurgy.recipes.CrusherRecipes;

public class TileEntityAlloyer extends TileEntityMetallurgySided
{

    public TileEntityAlloyer()
    {
        super(4, new int[] { 0, 1, 2 }, new int[] { 0, 1, 2 }, new int[] { 1, 3 });
    }

    @Override
    public String getInvName()
    {
        return "container.alloyer";
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return i >= 3 ? false : (i == 1 ? TileEntityFurnace.isItemFuel(itemstack) : true);
    }

    @Override
    protected void processItem()
    {
        if (this.canProcessItem())
        {
            ItemStack itemstack = getSmeltingResult(this.itemStacks[0]);

            if (this.itemStacks[3] == null)
            {
                this.itemStacks[3] = itemstack.copy();
            }
            else if (this.itemStacks[3].isItemEqual(itemstack))
            {
                itemStacks[3].stackSize += itemstack.stackSize;
            }

            --this.itemStacks[0].stackSize;

            if (this.itemStacks[0].stackSize <= 0)
            {
                this.itemStacks[0] = null;
            }
        }
    }

    @Override
    protected boolean canProcessItem()
    {
        if (this.itemStacks[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack itemstack = getSmeltingResult(this.itemStacks[0]);
            if (itemstack == null) return false;
            if (slotsAreEmtpty(3, 3)) return true;
            return canAcceptStackRange(3, 3, itemstack);
        }
    }

    private ItemStack getSmeltingResult(ItemStack itemStack)
    {
        return CrusherRecipes.getInstance().getCrushingResult(itemStack);
    }

    private boolean canAcceptStackRange(int start, int end, ItemStack itemstack)
    {
        Boolean retVal = false;

        for (int i = start; i <= end; i++)
        {
            boolean itemEqual = this.itemStacks[i].isItemEqual(itemstack);

            if (itemEqual)
            {
                int stackSize = this.itemStacks[i].stackSize + itemstack.stackSize;

                retVal |= stackSize <= getInventoryStackLimit() && stackSize <= itemstack.getMaxStackSize();
            }
            else
            {
                retVal |= false;
            }
        }

        return retVal;
    }

    private boolean slotsAreEmtpty(int start, int end)
    {
        Boolean retVal = false;
        for (int i = start; i <= end; i++)
        {
            retVal |= this.itemStacks[i] == null;
        }

        return retVal;
    }
}
