package com.teammetallurgy.metallurgy.machines.forge;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
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
    protected boolean hasMaterialAndRoom(ItemStack... itemStacks)
    {
        if (this.tank.getFluidAmount() <= 0) { return false; }
        return super.hasMaterialAndRoom(itemStacks);
    }

    @Override
    protected boolean preProcessItem()
    {
        FluidStack drain = this.tank.drain(10, false);

        if (drain == null) { return false; }

        if (drain.amount == 10)
        {
            this.tank.drain(drain.amount, true);

            this.currentItemBurnTime = this.burnTime = 200;

            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);

            return true;
        }

        return false;
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
    public String getInventoryName()
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
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return i != 1;
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

    protected int getFuelSlot()
    {
        return 1;
    }

    @Override
    protected int[] getInputSlots()
    {
        return new int[] { 0 };
    }

    @Override
    protected int[] getOutputSlots()
    {
        return new int[] { 1 };
    }

}
