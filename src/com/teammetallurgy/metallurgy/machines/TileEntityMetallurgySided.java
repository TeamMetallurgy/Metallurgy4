package com.teammetallurgy.metallurgy.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;

public abstract class TileEntityMetallurgySided extends TileEntityMetallurgy implements ISidedInventory
{

    private static int[] slots_top;
    private static int[] slots_bottom;
    private static int[] slots_sides;

    protected static final int MAXCOOKTIME = 200;

    protected ItemStack[] itemStacks;
    public int burnTime;
    public int currentItemBurnTime;
    public int cookTime;

    public TileEntityMetallurgySided(int numberOfItemStacks, int[] slotsTop, int[] slotsSide, int[] slotsBottom)
    {
        itemStacks = new ItemStack[numberOfItemStacks];
        slots_top = slotsTop;
        slots_bottom = slotsBottom;
        slots_sides = slotsSide;
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

        readItemListFromNBT(data, "Items", this.itemStacks);
        this.burnTime = data.getShort("BurnTime");
        this.cookTime = data.getShort("CookTime");
        this.currentItemBurnTime = TileEntityFurnace.getItemBurnTime(this.itemStacks[1]);
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

        compound.setShort("BurnTime", (short) this.burnTime);
        compound.setShort("CookTime", (short) this.cookTime);

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
        return i != 1 || itemstack.itemID == Item.bucketEmpty.itemID;
    }

    @Override
    public void updateEntity()
    {
        boolean burning = this.burnTime > 0;
        boolean changed = false;

        if (this.burnTime > 0)
        {
            --this.burnTime;
        }

        if (!this.worldObj.isRemote)
        {
            if (this.burnTime == 0 && this.canProcessItem())
            {
                this.currentItemBurnTime = this.burnTime = TileEntityFurnace.getItemBurnTime(this.itemStacks[1]);

                if (this.burnTime > 0)
                {
                    changed = true;

                    if (this.itemStacks[1] != null)
                    {
                        --this.itemStacks[1].stackSize;

                        if (this.itemStacks[1].stackSize == 0)
                        {
                            this.itemStacks[1] = this.itemStacks[1].getItem().getContainerItemStack(itemStacks[1]);
                        }
                    }
                }
            }

            if (this.isBurning() && this.canProcessItem())
            {
                ++this.cookTime;

                if (this.cookTime == MAXCOOKTIME)
                {
                    this.cookTime = 0;
                    this.processItem();
                    changed = true;
                }
            }
            else
            {
                this.cookTime = 0;
            }

            if (burning != this.burnTime > 0)
            {
                changed = true;
            }
        }

        if (changed)
        {
            this.onInventoryChanged();
        }
    }

    protected abstract void processItem();

    protected abstract boolean canProcessItem();

    public boolean isBurning()
    {
        return this.burnTime > 0;
    }

    public int getBurnTimeRemainingScaled(int i)
    {
        if (this.currentItemBurnTime == 0)
        {
            this.currentItemBurnTime = MAXCOOKTIME;
        }

        return this.burnTime * i / this.currentItemBurnTime;
    }

    public int getCookProgressScaled(int i)
    {
        return this.cookTime * i / MAXCOOKTIME;
    }

    protected boolean canAcceptStackRange(int start, int end, ItemStack itemstack)
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

    protected boolean slotsAreEmtpty(int start, int end)
    {
        Boolean retVal = false;
        for (int i = start; i <= end; i++)
        {
            retVal |= this.itemStacks[i] == null;
        }

        return retVal;
    }
}