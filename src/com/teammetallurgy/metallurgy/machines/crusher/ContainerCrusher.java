package com.teammetallurgy.metallurgy.machines.crusher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

import com.teammetallurgy.metallurgy.machines.ContainerMetallurgy;
import com.teammetallurgy.metallurgy.machines.SlotMetallurgy;
import com.teammetallurgy.metallurgy.machines.TileEntityMetallurgySided;
import com.teammetallurgy.metallurgy.recipes.AlloyerRecipes;
import com.teammetallurgy.metallurgy.recipes.CrusherRecipes;

public class ContainerCrusher extends ContainerMetallurgy
{

    public ContainerCrusher(InventoryPlayer inventoryPlayer, TileEntityMetallurgySided tileEntity)
    {
        this.tileEntity = tileEntity;
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
    public ItemStack transferStackInSlot(EntityPlayer player, int slotID)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(slotID);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            int machineFuelID = 1;
            int machineInventoryEndID = 4;

            if (slotID > machineInventoryEndID)
            {
                if (TileEntityFurnace.isItemFuel(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, machineFuelID, machineFuelID + 1, false)) { return null; }
                }
                else if (!mergeItemStack(itemstack1, 0, machineInventoryEndID, false)) { return null; }
            }
            else if (!mergeItemStack(itemstack1, machineInventoryEndID + 1, inventorySlots.size(), false)) { return null; }

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
