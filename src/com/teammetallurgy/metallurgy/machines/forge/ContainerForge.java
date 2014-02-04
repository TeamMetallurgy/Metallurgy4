package com.teammetallurgy.metallurgy.machines.forge;

import net.minecraft.entity.player.InventoryPlayer;

import com.teammetallurgy.metallurgy.machines.ContainerMetallurgy;
import com.teammetallurgy.metallurgy.machines.SlotMetallurgy;
import com.teammetallurgy.metallurgy.machines.TileEntityMetallurgy;

public class ContainerForge extends ContainerMetallurgy
{

    public ContainerForge(InventoryPlayer inventoryPlayer, TileEntityMetallurgy tileEntity)
    {
        super(-1, 2, tileEntity);
        addSlotToContainer(new SlotMetallurgy(tileEntity, 0, 80, 8));
        addSlotToContainer(new SlotMetallurgy(tileEntity, 1, 80, 62));

        addPlayersInventoryToContainer(inventoryPlayer, 8, 84);

    }

}
