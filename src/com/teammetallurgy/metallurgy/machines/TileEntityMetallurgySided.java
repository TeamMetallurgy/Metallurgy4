package com.teammetallurgy.metallurgy.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class TileEntityMetallurgySided extends TileEntityMetallurgy implements ISidedInventory
{

    private static final int[] slots_top = new int[] { 0 };
    private static int[] slots_bottom;
    private static final int[] slots_sides = new int[] { 1 };

    protected ItemStack[] itemStacks;
    private ItemStack[] drawers;
    private int numberOfDrawers;

    public TileEntityMetallurgySided(int numberOfItemStacks)
    {
        itemStacks = new ItemStack[numberOfItemStacks];

        slots_bottom = new int[numberOfItemStacks];

        for (int i = 0; i < slots_bottom.length; i++)
        {
            slots_bottom[i] = i;
        }
    }

    @Override
    public int getSizeInventory()
    {
        return itemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {
        return itemStacks[i];
    }

    @Override
    protected void readCustomNBT(NBTTagCompound data)
    {

        this.itemStacks = new ItemStack[this.getSizeInventory()];
        this.drawers = new ItemStack[this.numberOfDrawers];

        readItemListFromNBT(data, "Items", this.itemStacks);
        readItemListFromNBT(data, "Drawers", this.drawers);
    }

    private void readItemListFromNBT(NBTTagCompound data, String name, ItemStack[] stacks)
    {
        NBTTagList nbttaglist = data.getTagList(name);

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.itemStacks.length)
            {
                stacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }

    @Override
    protected void writeCustomNBT(NBTTagCompound compound)
    {
        writeItemListToNBT(compound, itemStacks, "Items");
        writeItemListToNBT(compound, drawers, "Drawers");

    }

    private void writeItemListToNBT(NBTTagCompound compound, ItemStack[] stacks, String name)
    {
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < stacks.length; ++i)
        {
            if (stacks[i] != null)
            {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) i);
                stacks[i].writeToNBT(tagCompound);
                nbttaglist.appendTag(tagCompound);
            }
        }

        compound.setTag(name, nbttaglist);
    }

    @Override
    public ItemStack decrStackSize(int i, int j)
    {
        if (this.itemStacks[i] != null)
        {
            ItemStack itemstack;

            if (this.itemStacks[i].stackSize <= j)
            {
                itemstack = this.itemStacks[i];
                this.itemStacks[i] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.itemStacks[i].splitStack(j);

                if (this.itemStacks[i].stackSize == 0)
                {
                    this.itemStacks[i] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i)
    {
        if (this.itemStacks[i] != null)
        {
            ItemStack itemstack = this.itemStacks[i];
            this.itemStacks[i] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        this.itemStacks[i] = itemstack;

        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
        {
            itemstack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public abstract String getInvName();

    @Override
    public boolean isInvNameLocalized()
    {
        return false;
    }

    @Override
    public abstract int getInventoryStackLimit();

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : entityplayer.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openChest()
    {
    }

    @Override
    public void closeChest()
    {
    }

    @Override
    public abstract boolean isItemValidForSlot(int i, ItemStack itemstack);

    @Override
    public int[] getAccessibleSlotsFromSide(int side)
    {
        return side == 0 ? slots_bottom : (side == 1 ? slots_top : slots_sides);
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j)
    {
        return this.isItemValidForSlot(i, itemstack);
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j)
    {
        return i != 0 || i != 1 || itemstack.itemID == Item.bucketEmpty.itemID;
    }

    protected void setNumberOfDrawers(int drawers)
    {
        this.numberOfDrawers = drawers;
        this.drawers = new ItemStack[drawers];
    }

}