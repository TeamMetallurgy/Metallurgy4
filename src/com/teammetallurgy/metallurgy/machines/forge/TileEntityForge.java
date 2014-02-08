package com.teammetallurgy.metallurgy.machines.forge;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidEvent;
import net.minecraftforge.fluids.FluidEvent.FluidDrainingEvent;
import net.minecraftforge.fluids.FluidEvent.FluidFillingEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.teammetallurgy.metallurgycore.machines.TileEntityMetallurgySided;

public class TileEntityForge extends TileEntityMetallurgySided implements IFluidHandler
{
    FluidTank tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 10);

    public TileEntityForge()
    {
        super(2, new int[] { 0 }, new int[] { 0 }, new int[] { 1 });
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        return true;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        return FluidRegistry.LAVA.equals(fluid);
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
            if (this.tank.getFluidAmount() <= 0) { return false; }
            ItemStack itemstack = this.getSmeltingResult(this.itemStacks[0]);
            if (itemstack == null) { return false; }
            if (this.slotsAreEmtpty(1, 1)) { return true; }
            return this.canAcceptStackRange(1, 1, itemstack);
        }
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        FluidDrainingEvent fluidDrainingEvent = new FluidEvent.FluidDrainingEvent(resource, this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.tank);
        FluidEvent.fireEvent(fluidDrainingEvent);

        if (resource == null || !resource.isFluidEqual(this.tank.getFluid())) { return null; }
        return this.drain(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {

        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        return this.tank.drain(maxDrain, doDrain);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        FluidFillingEvent fluidFillingEvent = new FluidEvent.FluidFillingEvent(resource, this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.tank);
        FluidEvent.fireEvent(fluidFillingEvent);

        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        return this.tank.fill(resource, doFill);
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public String getInvName()
    {
        return "container.forge";
    }

    @Override
    protected ItemStack getSmeltingResult(ItemStack... itemStack)
    {
        return FurnaceRecipes.smelting().getSmeltingResult(itemStack[0]);
    }

    public FluidTank getTank()
    {
        return this.tank;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        return new FluidTankInfo[] { this.tank.getInfo() };
    }

    @Override
    public boolean isBurning()
    {
        return this.tank.getFluidAmount() > 0;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return i != 1;
    }

    @Override
    protected void processItem()
    {
        if (this.canProcessItem())
        {
            ItemStack itemstack = this.getSmeltingResult(this.itemStacks[0]);

            if (this.itemStacks[1] == null)
            {
                this.itemStacks[1] = itemstack.copy();
            }
            else if (this.itemStacks[1].isItemEqual(itemstack))
            {
                this.itemStacks[1].stackSize += itemstack.stackSize;
            }

            --this.itemStacks[0].stackSize;

            if (this.itemStacks[0].stackSize <= 0)
            {
                this.itemStacks[0] = null;
            }

            this.tank.drain(100, true);

            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
    }

    @Override
    protected void readCustomNBT(NBTTagCompound data)
    {
        super.readCustomNBT(data);

        this.tank = this.tank.readFromNBT(data);
    }

    @Override
    protected void writeCustomNBT(NBTTagCompound compound)
    {
        super.writeCustomNBT(compound);

        this.tank.writeToNBT(compound);
    }

}
