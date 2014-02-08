package com.teammetallurgy.metallurgy.machines.crusher;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

import com.teammetallurgy.metallurgy.recipes.CrusherRecipes;
import com.teammetallurgy.metallurgycore.machines.TileEntityMetallurgySided;

public class TileEntityCrusher extends TileEntityMetallurgySided
{

    public TileEntityCrusher()
    {
        super(5, new int[] { 0, 1 }, new int[] { 0, 1 }, new int[] { 1, 2, 3, 4 });
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
            ItemStack itemstack = this.getSmeltingResult(this.itemStacks[0]);
            if (itemstack == null) { return false; }
            if (this.slotsAreEmtpty(2, 4)) { return true; }
            return this.canAcceptStackRange(2, 4, itemstack);
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
        return i >= 2 ? false : i == 1 ? TileEntityFurnace.isItemFuel(itemstack) : true;
    }

    @Override
    protected void processItem()
    {
        if (this.canProcessItem())
        {
            ItemStack itemstack = this.getSmeltingResult(this.itemStacks[0]);

            if (this.itemStacks[2] == null)
            {
                this.itemStacks[2] = itemstack.copy();
            }
            else if (this.itemStacks[2].isItemEqual(itemstack))
            {
                this.itemStacks[2].stackSize += itemstack.stackSize;
            }

            else if (this.itemStacks[3] == null)
            {
                this.itemStacks[3] = itemstack.copy();
            }
            else if (this.itemStacks[3].isItemEqual(itemstack))
            {
                this.itemStacks[3].stackSize += itemstack.stackSize;
            }

            else if (this.itemStacks[4] == null)
            {
                this.itemStacks[4] = itemstack.copy();
            }
            else if (this.itemStacks[4].isItemEqual(itemstack))
            {
                this.itemStacks[4].stackSize += itemstack.stackSize;
            }

            --this.itemStacks[0].stackSize;

            if (this.itemStacks[0].stackSize <= 0)
            {
                this.itemStacks[0] = null;
            }
        }
    }
}
