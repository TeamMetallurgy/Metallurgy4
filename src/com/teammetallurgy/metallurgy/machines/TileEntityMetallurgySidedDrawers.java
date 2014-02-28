package com.teammetallurgy.metallurgy.machines;

import net.minecraft.item.ItemStack;

import com.teammetallurgy.metallurgy.ItemList;
import com.teammetallurgy.metallurgy.items.ItemDrawer;
import com.teammetallurgy.metallurgy.machines.crusher.TileEntityCrusher;
import com.teammetallurgy.metallurgycore.machines.TileEntityMetallurgySided;

public abstract class TileEntityMetallurgySidedDrawers extends TileEntityMetallurgySided
{

    public TileEntityMetallurgySidedDrawers(int numberOfItemStacks, int[] slotsTop, int[] slotsSide, int[] slotsBottom)
    {
        super(numberOfItemStacks, slotsTop, slotsSide, slotsBottom);
    }

    @Override
    protected boolean hasMaterialAndRoom(ItemStack... itemStacks)
    {
        return canUseDrawerItem() || super.hasMaterialAndRoom(itemStacks);
    }

    @Override
    protected boolean hasInput()
    {
        return canUseDrawerItem() || super.hasInput();
    }

    @Override
    protected void processItem()
    {
        if (this.canProcessItem())
        {
            ItemStack[] inputStacks = getStacksInSlots(getInputSlots());
            ItemStack outputStack = this.getSmeltingResult(inputStacks);

            if (outputStack == null)
            {
                outputStack = this.getSmeltingResult(getDrawerItem());
            }

            outputItem(outputStack);

            if (!useMaterialInSlots(getInputSlots()))
            {
                useDrawerMaterial();
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        if (i >= getDrawerInput() && i <= getDrawerOutput()) { return itemstack.getItem() == ItemList.drawer; }
        return super.isItemValidForSlot(i, itemstack);
    }

    private boolean canUseDrawerItem()
    {
        ItemStack drawerInput = getStackInSlot(getDrawerInput());
        if (drawerInput != null)
        {
            ItemStack firstStack = getDrawerItem();

            if (firstStack != null) { return super.hasMaterialAndRoom(firstStack); }
        }
        return false;
    }

    protected void useDrawerMaterial()
    {
        ItemStack firstStack = getDrawerItem();

        --firstStack.stackSize;

        if (firstStack.stackSize <= 0)
        {
            firstStack = null;
        }

        setInventorySlotContents(getDrawerInput(), ItemDrawer.writeFirstStack(getStackInSlot(getDrawerInput()), firstStack));
    }

    protected ItemStack getDrawerItem()
    {
        return ItemDrawer.getFirstStack(getStackInSlot(getDrawerInput()));
    }

    protected abstract int getDrawerInput();

    protected abstract int getDrawerOutput();

}