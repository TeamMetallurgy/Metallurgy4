package com.teammetallurgy.metallurgy.machines.forge;

import net.minecraft.entity.player.InventoryPlayer;

import com.teammetallurgy.metallurgycore.machines.ContainerMetallurgyMachine;
import com.teammetallurgy.metallurgycore.machines.SlotMetallurgyMachine;
import com.teammetallurgy.metallurgycore.machines.TileEntityMetallurgy;

public class ContainerForge extends ContainerMetallurgyMachine
{

    public ContainerForge(InventoryPlayer inventoryPlayer, TileEntityMetallurgy tileEntity)
    {
        super(-1, 2, tileEntity);
        this.addSlotToContainer(new SlotMetallurgyMachine(tileEntity, 0, 80, 8));
        this.addSlotToContainer(new SlotMetallurgyMachine(tileEntity, 1, 80, 62));

        this.addPlayersInventoryToContainer(inventoryPlayer, 8, 84);

    }

}
