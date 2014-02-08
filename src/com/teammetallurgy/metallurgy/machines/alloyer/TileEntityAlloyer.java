package com.teammetallurgy.metallurgy.machines.alloyer;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

import com.teammetallurgy.metallurgy.recipes.AlloyerRecipes;
import com.teammetallurgy.metallurgycore.machines.TileEntityMetallurgySided;

public class TileEntityAlloyer extends TileEntityMetallurgySided
{

    public TileEntityAlloyer()
    {
        super(4, new int[] { 0, 1, 2 }, new int[] { 0, 1, 2 }, new int[] { 1, 3 });
    }

    @Override
    protected boolean canProcessItem()
    {
        if (this.itemStacks[0] == null || this.itemStacks[2] == null)
        {
            return false;
        }
        else
        {
            ItemStack itemstack = this.getSmeltingResult(this.itemStacks[0], this.itemStacks[2]);
            if (itemstack == null) { return false; }
            if (this.slotsAreEmtpty(3, 3)) { return true; }
            return this.canAcceptStackRange(3, 3, itemstack);
        }
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public String getInvName()
    {
        return "container.alloyer";
    }

    @Override
    protected ItemStack getSmeltingResult(ItemStack... itemStack)
    {
        return AlloyerRecipes.getInstance().getAlloyResult(itemStack[0], itemStack[1]);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return i >= 3 ? false : i == 1 ? TileEntityFurnace.isItemFuel(itemstack) : true;
    }

    @Override
    protected void processItem()
    {
        if (this.canProcessItem())
        {
            ItemStack itemstack = this.getSmeltingResult(this.itemStacks[0], this.itemStacks[2]);

            if (this.itemStacks[3] == null)
            {
                this.itemStacks[3] = itemstack.copy();
            }
            else if (this.itemStacks[3].isItemEqual(itemstack))
            {
                this.itemStacks[3].stackSize += itemstack.stackSize;
            }

            --this.itemStacks[0].stackSize;

            if (this.itemStacks[0].stackSize <= 0)
            {
                this.itemStacks[0] = null;
            }

            --this.itemStacks[2].stackSize;

            if (this.itemStacks[2].stackSize <= 0)
            {
                this.itemStacks[2] = null;
            }
        }
    }
}
