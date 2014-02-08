package com.teammetallurgy.metallurgy.machines.crusher;

import net.minecraft.entity.player.InventoryPlayer;

import com.teammetallurgy.metallurgycore.machines.ContainerMetallurgy;
import com.teammetallurgy.metallurgycore.machines.SlotMetallurgy;
import com.teammetallurgy.metallurgycore.machines.TileEntityMetallurgy;

public class ContainerCrusher extends ContainerMetallurgy
{

    public ContainerCrusher(InventoryPlayer inventoryPlayer, TileEntityMetallurgy tileEntity)
    {
        super(1, 5, tileEntity);
        this.addSlotToContainer(new SlotMetallurgy(tileEntity, 0, 80, 8));
        this.addSlotToContainer(new SlotMetallurgy(tileEntity, 1, 8, 42));
        this.addSlotToContainer(new SlotMetallurgy(tileEntity, 2, 62, 62));
        this.addSlotToContainer(new SlotMetallurgy(tileEntity, 3, 80, 62));
        this.addSlotToContainer(new SlotMetallurgy(tileEntity, 4, 98, 62));
        this.addPlayersInventoryToContainer(inventoryPlayer, 8, 84);

    }
}
