package com.teammetallurgy.metallurgy.machines.crusher;

import net.minecraft.entity.player.InventoryPlayer;

import com.teammetallurgy.metallurgy.machines.ContainerMetallurgy;
import com.teammetallurgy.metallurgy.machines.SlotMetallurgy;
import com.teammetallurgy.metallurgy.machines.TileEntityMetallurgy;

public class ContainerCrusher extends ContainerMetallurgy
{

    public ContainerCrusher(InventoryPlayer inventoryPlayer, TileEntityMetallurgy tileEntity)
    {
        super(1, 5, tileEntity);
        addSlotToContainer(new SlotMetallurgy(tileEntity, 0, 80, 8));
        addSlotToContainer(new SlotMetallurgy(tileEntity, 1, 8, 42));
        addSlotToContainer(new SlotMetallurgy(tileEntity, 2, 62, 62));
        addSlotToContainer(new SlotMetallurgy(tileEntity, 3, 80, 62));
        addSlotToContainer(new SlotMetallurgy(tileEntity, 4, 98, 62));
        addPlayersInventoryToContainer(inventoryPlayer, 8, 84);

    }
}
