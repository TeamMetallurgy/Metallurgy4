package com.teammetallurgy.metallurgy.machines.crusher;

import com.teammetallurgy.metallurgy.machines.SlotMetallurgy;
import com.teammetallurgy.metallurgy.machines.TileEntityMetallurgySided;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerCrusher extends Container
{

    private TileEntityMetallurgySided crusher;

    public ContainerCrusher(InventoryPlayer inventoryPlayer, TileEntityMetallurgySided tileEntity)
    {
        this.crusher = tileEntity;
        addSlotToContainer(new SlotMetallurgy(tileEntity, 0, 80, 8));
        addSlotToContainer(new SlotMetallurgy(tileEntity, 1, 8, 42));
        addSlotToContainer(new SlotMetallurgy(tileEntity, 2, 62, 62));
        addSlotToContainer(new SlotMetallurgy(tileEntity, 3, 80, 62));
        addSlotToContainer(new SlotMetallurgy(tileEntity, 4, 98, 62));
        addPlayersInventoryToContainer(inventoryPlayer, 8, 84);
    }

    private void addPlayersInventoryToContainer(InventoryPlayer inventoryPlayer, int xStart, int yStart)
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
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return this.crusher.isUseableByPlayer(entityplayer);
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

            int playerInventoryStartID = 30;
            int playerInventoryEndID = 39;
            
            int machineInputID = 0;
            int machineFuelID = 1;
            int machineOutputStartID = 2;
            int machineInventoryEndID = 5;
            
            if (slotID >= machineOutputStartID && slotID < machineInventoryEndID)
            {
                if (!this.mergeItemStack(itemstack1, machineInventoryEndID, playerInventoryEndID, true)) { return null; }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else
            {

                if (slotID != machineFuelID && slotID != machineInputID)
                {
                    if (FurnaceRecipes.smelting().getSmeltingResult(itemstack1) != null)
                    {
                        if (!this.mergeItemStack(itemstack1, machineInputID, machineFuelID, false)) { return null; }
                    }
                    else if (TileEntityFurnace.isItemFuel(itemstack1))
                    {
                        if (!this.mergeItemStack(itemstack1, machineFuelID, machineOutputStartID, false)) { return null; }
                    }
                    else
                    {
                        if (slotID >= machineInventoryEndID && slotID < playerInventoryStartID)
                        {
                            if (!this.mergeItemStack(itemstack1, playerInventoryStartID, playerInventoryEndID, false)) { return null; }
                        }
                        else if (slotID >= playerInventoryStartID && slotID < playerInventoryEndID && !this.mergeItemStack(itemstack1, machineInventoryEndID, playerInventoryStartID, false)) { return null; }
                    }
                }
                else if (!this.mergeItemStack(itemstack1, machineInventoryEndID, playerInventoryEndID, false)) { return null; }
            }

            if (itemstack1.stackSize == machineInputID)
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
