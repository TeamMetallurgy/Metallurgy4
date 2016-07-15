package com.teammetallurgy.metallurgy.machines.crusher;

import net.minecraft.entity.player.InventoryPlayer;

import com.teammetallurgy.metallurgycore.machines.ContainerMetallurgyMachine;
import com.teammetallurgy.metallurgycore.machines.SlotMetallurgyMachine;
import com.teammetallurgy.metallurgycore.machines.TileEntityMetallurgy;

public class ContainerCrusher extends ContainerMetallurgyMachine
{

    public ContainerCrusher(InventoryPlayer inventoryPlayer, TileEntityMetallurgy tileEntity)
    {
        super(1, 6, tileEntity);
        this.addSlotToContainer(new SlotMetallurgyMachine(tileEntity, 0, 80, 8));
        this.addSlotToContainer(new SlotMetallurgyMachine(tileEntity, 1, 8, 42));
        this.addSlotToContainer(new SlotMetallurgyMachine(tileEntity, 2, 62, 62));
        this.addSlotToContainer(new SlotMetallurgyMachine(tileEntity, 3, 80, 62));
        this.addSlotToContainer(new SlotMetallurgyMachine(tileEntity, 4, 98, 62));
        
        //Drawers
        this.addSlotToContainer(new SlotMetallurgyMachine(tileEntity, 5, 8, 62));
        
        this.addPlayersInventoryToContainer(inventoryPlayer, 8, 84);

    }
}
