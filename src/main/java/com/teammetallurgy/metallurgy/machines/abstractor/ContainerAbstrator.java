package com.teammetallurgy.metallurgy.machines.abstractor;

import net.minecraft.entity.player.InventoryPlayer;

import com.teammetallurgy.metallurgycore.machines.ContainerMetallurgyMachine;
import com.teammetallurgy.metallurgycore.machines.SlotMetallurgyMachine;
import com.teammetallurgy.metallurgycore.machines.TileEntityMetallurgy;

public class ContainerAbstrator extends ContainerMetallurgyMachine
{

    public ContainerAbstrator(InventoryPlayer inventoryPlayer, TileEntityMetallurgy tileEntity)
    {
        super(0, 5, tileEntity);
        this.addSlotToContainer((new SlotMetallurgyMachine(tileEntity, 0, 26, 56)));
        this.addSlotToContainer((new SlotMetallurgyMachine(tileEntity, 1, 17, 19)));
        this.addSlotToContainer((new SlotMetallurgyMachine(tileEntity, 2, 36, 19)));
        this.addSlotToContainer((new SlotMetallurgyMachine(tileEntity, 3, 62, 56)));
        this.addSlotToContainer((new SlotMetallurgyMachine(tileEntity, 4, 134, 56)));
        this.addPlayersInventoryToContainer(inventoryPlayer, 8, 84);
    }

}
