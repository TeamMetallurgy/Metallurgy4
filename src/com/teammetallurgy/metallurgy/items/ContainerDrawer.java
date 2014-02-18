package com.teammetallurgy.metallurgy.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.metallurgycore.machines.ContainerMetallurgy;

public class ContainerDrawer extends ContainerMetallurgy
{
    private IInventory inventory = new InventoryBasic("drawer", true, 9);
    private EntityPlayer player;
    private ItemStack stack;

    public ContainerDrawer(InventoryPlayer inventory, ItemStack itemStack)
    {
        this.player = inventory.player;
        this.stack = itemStack;
        this.addPlayersInventoryToContainer(inventory, 8, 84);

        for (int i = 0; i < 3; i++)
        {
            for (int y = 0; y < 3; y++)
            {
                this.addSlotToContainer(new Slot(this.inventory, i + y * 3, 62 + 18 * i, 15 + (18 * y)));
            }
        }

        readInventory(itemStack.getTagCompound(), this.inventory);
    }

    @Override
    public void onContainerClosed(EntityPlayer player)
    {
        saveInventoryToStack(this.stack, this.inventory);

        player.setCurrentItemOrArmor(0, stack);

        super.onContainerClosed(player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return entityplayer == player;
    }

}
