package com.teammetallurgy.metallurgy.machines.crusher;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

import com.teammetallurgy.metallurgy.ItemList;
import com.teammetallurgy.metallurgy.items.ItemDrawer;
import com.teammetallurgy.metallurgy.recipes.CrusherRecipes;
import com.teammetallurgy.metallurgycore.machines.TileEntityMetallurgySided;

public class TileEntityCrusher extends TileEntityMetallurgySided
{

    public TileEntityCrusher()
    {
        super(6, new int[] { 0, 1 }, new int[] { 0, 1 }, new int[] { 1, 2, 3, 4 });
    }

    @Override
    protected boolean canProcessItem()
    {
        if (this.itemStacks[0] == null)
        {
            if (this.itemStacks[5] != null)
            {
                ItemStack itemStack = this.itemStacks[5];
                ItemStack firstStack = getDrawerItem(itemStack);

                if (firstStack != null) { return canProcessItem(firstStack); }
            }
            return false;
        }
        else
        {
            return canProcessItem(this.itemStacks[0]);
        }
    }

    protected boolean canProcessItem(ItemStack itemStack2)
    {
        ItemStack itemstack = this.getSmeltingResult(itemStack2);
        if (itemstack == null) { return false; }
        if (this.slotsAreEmtpty(2, 4)) { return true; }
        return this.canAcceptStackRange(2, 4, itemstack);
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
        if (i >= 5 && i <= 6) { return itemstack.getItem() == ItemList.drawer; }

        return i >= 2 ? false : i == 1 ? TileEntityFurnace.isItemFuel(itemstack) : true;
    }

    @Override
    protected void processItem()
    {
        if (this.canProcessItem())
        {
            ItemStack itemstack = this.getSmeltingResult(this.itemStacks[0]);
            
            if(itemstack == null)
            {
                itemstack = this.getSmeltingResult(getDrawerItem(this.itemStacks[5]));
            }

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

            if (this.itemStacks[0] != null)
            {
                --this.itemStacks[0].stackSize;

                if (this.itemStacks[0].stackSize <= 0)
                {
                    this.itemStacks[0] = null;
                }
            }
            else
            {
                ItemStack firstStack = getDrawerItem(this.itemStacks[5]);

                --firstStack.stackSize;

                if (firstStack.stackSize <= 0)
                {
                    firstStack = null;
                }
                
               setInventorySlotContents(5, ItemDrawer.writeFirstStack(this.itemStacks[5], firstStack));
            }
        }
    }

    private ItemStack getDrawerItem(ItemStack itemStack)
    {
        return ItemDrawer.getFirstStack(this.itemStacks[5]);
    }
}
