package com.teammetallurgy.metallurgy.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public abstract class ContainerMetallurgy extends Container
{

    public TileEntityMetallurgy tileEntity;
    private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;

    protected int machineFuelID;
    protected int machineInventoryEndID;

    public ContainerMetallurgy(int machineFuelID, int machineInventoryEndID, TileEntityMetallurgy tileEntityMetallurgy)
    {
        this.machineFuelID = machineFuelID;
        this.machineInventoryEndID = machineInventoryEndID;
        this.tileEntity = tileEntityMetallurgy;
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafter)
    {
        super.addCraftingToCrafters(crafter);
        crafter.sendProgressBarUpdate(this, 0, this.tileEntity.cookTime);
        crafter.sendProgressBarUpdate(this, 1, this.tileEntity.burnTime);
        crafter.sendProgressBarUpdate(this, 2, this.tileEntity.currentItemBurnTime);
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if (this.lastCookTime != this.tileEntity.cookTime)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.tileEntity.cookTime);
            }

            if (this.lastBurnTime != this.tileEntity.burnTime)
            {
                icrafting.sendProgressBarUpdate(this, 1, this.tileEntity.burnTime);
            }

            if (this.lastItemBurnTime != this.tileEntity.currentItemBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 2, this.tileEntity.currentItemBurnTime);
            }
        }

        this.lastCookTime = this.tileEntity.cookTime;
        this.lastBurnTime = this.tileEntity.burnTime;
        this.lastItemBurnTime = this.tileEntity.currentItemBurnTime;
    }

    @Override
    public void updateProgressBar(int id, int newValue)
    {
        if (id == 0)
        {
            this.tileEntity.cookTime = newValue;
        }

        if (id == 1)
        {
            this.tileEntity.burnTime = newValue;
        }

        if (id == 2)
        {
            this.tileEntity.currentItemBurnTime = newValue;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return this.tileEntity.isUseableByPlayer(entityplayer);
    }

    protected void addPlayersInventoryToContainer(InventoryPlayer inventoryPlayer, int xStart, int yStart)
    {
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, xStart + j * 18, yStart + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, xStart + i * 18, 142));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotID)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(slotID);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotID > this.machineInventoryEndID)
            {
                if (TileEntityFurnace.isItemFuel(itemstack1) && this.machineFuelID > 0)
                {
                    if (!this.mergeItemStack(itemstack1, this.machineFuelID, this.machineFuelID + 1, false)) { return null; }
                }
                else if (!mergeItemStack(itemstack1, 0, this.machineInventoryEndID, false)) { return null; }
            }
            else if (!mergeItemStack(itemstack1, this.machineInventoryEndID + 1, this.inventorySlots.size(), false)) { return null; }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack) null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) { return null; }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }

}