package com.teammetallurgy.metallurgy.machines.alloyer;

import net.minecraft.entity.player.InventoryPlayer;

import com.teammetallurgy.metallurgycore.machines.ContainerMetallurgyMachine;
import com.teammetallurgy.metallurgycore.machines.SlotMetallurgyMachine;
import com.teammetallurgy.metallurgycore.machines.TileEntityMetallurgy;

public class ContainerAlloyer extends ContainerMetallurgyMachine
{

    public ContainerAlloyer(InventoryPlayer inventoryPlayer, TileEntityMetallurgy tileEntity)
    {
        super(1, 4, tileEntity);
        this.addSlotToContainer(new SlotMetallurgyMachine(tileEntity, 0, 62, 8));
        this.addSlotToContainer(new SlotMetallurgyMachine(tileEntity, 1, 8, 42));
        this.addSlotToContainer(new SlotMetallurgyMachine(tileEntity, 2, 98, 8));
        this.addSlotToContainer(new SlotMetallurgyMachine(tileEntity, 3, 80, 62));
        this.addPlayersInventoryToContainer(inventoryPlayer, 8, 84);
    }
}
