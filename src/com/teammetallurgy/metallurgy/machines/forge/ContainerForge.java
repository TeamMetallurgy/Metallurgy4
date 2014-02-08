package com.teammetallurgy.metallurgy.machines.forge;

import net.minecraft.entity.player.InventoryPlayer;

import com.teammetallurgy.metallurgycore.machines.ContainerMetallurgy;
import com.teammetallurgy.metallurgycore.machines.SlotMetallurgy;
import com.teammetallurgy.metallurgycore.machines.TileEntityMetallurgy;

public class ContainerForge extends ContainerMetallurgy
{

    public ContainerForge(InventoryPlayer inventoryPlayer, TileEntityMetallurgy tileEntity)
    {
        super(-1, 2, tileEntity);
        this.addSlotToContainer(new SlotMetallurgy(tileEntity, 0, 80, 8));
        this.addSlotToContainer(new SlotMetallurgy(tileEntity, 1, 80, 62));

        this.addPlayersInventoryToContainer(inventoryPlayer, 8, 84);

    }

}
