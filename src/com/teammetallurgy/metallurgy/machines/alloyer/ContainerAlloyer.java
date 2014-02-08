package com.teammetallurgy.metallurgy.machines.alloyer;

import net.minecraft.entity.player.InventoryPlayer;

import com.teammetallurgy.metallurgycore.machines.ContainerMetallurgy;
import com.teammetallurgy.metallurgycore.machines.SlotMetallurgy;
import com.teammetallurgy.metallurgycore.machines.TileEntityMetallurgy;

public class ContainerAlloyer extends ContainerMetallurgy
{

    public ContainerAlloyer(InventoryPlayer inventoryPlayer, TileEntityMetallurgy tileEntity)
    {
        super(1, 4, tileEntity);
        addSlotToContainer(new SlotMetallurgy(tileEntity, 0, 62, 8));
        addSlotToContainer(new SlotMetallurgy(tileEntity, 1, 8, 42));
        addSlotToContainer(new SlotMetallurgy(tileEntity, 2, 98, 8));
        addSlotToContainer(new SlotMetallurgy(tileEntity, 3, 80, 62));
        addPlayersInventoryToContainer(inventoryPlayer, 8, 84);
    }
}
