package com.teammetallurgy.metallurgy.machines.crusher;

import com.teammetallurgy.metallurgy.machines.TileEntityMetallurgySided;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityCrusher extends TileEntityMetallurgySided
{

    public int crusherBurnTime;
    public int currentItemBurnTime;
    public int crusherCookTime;

    public TileEntityCrusher()
    {
        super(5);
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
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return i >= 2 ? false : (i == 1 ? TileEntityFurnace.isItemFuel(itemstack) : true);
    }
}
