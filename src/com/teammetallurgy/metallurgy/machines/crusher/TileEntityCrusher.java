package com.teammetallurgy.metallurgy.machines.crusher;

import com.teammetallurgy.metallurgy.machines.TileEntityMetallurgySided;
import com.teammetallurgy.metallurgy.recipes.CrusherRecipes;

import net.minecraft.block.BlockFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityCrusher extends TileEntityMetallurgySided
{

    private static final int CRUSHERMAXCOOKTIME = 200;
    public int crusherBurnTime;
    public int currentItemBurnTime;
    public int crusherCookTime;

    public TileEntityCrusher()
    {
        super(5);
        setNumberOfDrawers(2);
    }

    @Override
    protected void writeCustomNBT(NBTTagCompound data)
    {
        super.writeCustomNBT(data);

        data.setShort("BurnTime", (short) this.crusherBurnTime);
        data.setShort("CookTime", (short) this.crusherCookTime);
    }

    @Override
    protected void readCustomNBT(NBTTagCompound data)
    {
        super.readCustomNBT(data);

        this.crusherBurnTime = data.getShort("BurnTime");
        this.crusherCookTime = data.getShort("CookTime");
        this.currentItemBurnTime = TileEntityFurnace.getItemBurnTime(this.itemStacks[1]);
    }

    @Override
    public String getInvName()
    {
        return "container.crusher";
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public void updateEntity()
    {
        boolean burning = this.crusherBurnTime > 0;
        boolean changed = false;

        if (this.crusherBurnTime > 0)
        {
            --this.crusherBurnTime;
        }

        if (!this.worldObj.isRemote)
        {
            if (this.crusherBurnTime == 0 && this.canSmelt())
            {
                this.currentItemBurnTime = this.crusherBurnTime = TileEntityFurnace.getItemBurnTime(this.itemStacks[1]);

                if (this.crusherBurnTime > 0)
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

            if (this.isBurning() && this.canSmelt())
            {
                ++this.crusherCookTime;

                if (this.crusherCookTime == CRUSHERMAXCOOKTIME)
                {
                    this.crusherCookTime = 0;
                    this.smeltItem();
                    changed = true;
                }
            }
            else
            {
                this.crusherCookTime = 0;
            }

            if (burning != this.crusherBurnTime > 0)
            {
                changed = true;
            }
        }

        if (changed)
        {
            this.onInventoryChanged();
        }
    }

    private void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack itemstack = getSmeltingResult(this.itemStacks[0]);

            if (this.itemStacks[2] == null)
            {
                this.itemStacks[2] = itemstack.copy();
            }
            else if (this.itemStacks[2].isItemEqual(itemstack))
            {
                itemStacks[2].stackSize += itemstack.stackSize;
            }

            else if (this.itemStacks[3] == null)
            {
                this.itemStacks[3] = itemstack.copy();
            }
            else if (this.itemStacks[3].isItemEqual(itemstack))
            {
                itemStacks[3].stackSize += itemstack.stackSize;
            }

            else if (this.itemStacks[4] == null)
            {
                this.itemStacks[4] = itemstack.copy();
            }
            else if (this.itemStacks[4].isItemEqual(itemstack))
            {
                itemStacks[4].stackSize += itemstack.stackSize;
            }

            --this.itemStacks[0].stackSize;

            if (this.itemStacks[0].stackSize <= 0)
            {
                this.itemStacks[0] = null;
            }
        }
    }

    private boolean canSmelt()
    {
        if (this.itemStacks[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack itemstack = getSmeltingResult(this.itemStacks[0]);
            if (itemstack == null) return false;
            if (slotsAreEmtpty(2, 4)) return true;
            return canAcceptStackRange(2, 4, itemstack);
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

    protected boolean isBurning()
    {
        return this.crusherBurnTime > 0;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return i >= 2 ? false : (i == 1 ? TileEntityFurnace.isItemFuel(itemstack) : true);
    }

    public int getBurnTimeRemainingScaled(int i)
    {
        if (this.currentItemBurnTime == 0)
        {
            this.currentItemBurnTime = CRUSHERMAXCOOKTIME;
        }

        return this.crusherBurnTime * i / this.currentItemBurnTime;
    }

    public int getCookProgressScaled(int i)
    {
        return this.crusherCookTime * i / CRUSHERMAXCOOKTIME;
    }
}
