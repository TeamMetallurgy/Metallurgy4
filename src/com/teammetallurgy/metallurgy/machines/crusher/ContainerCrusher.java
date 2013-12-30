package com.teammetallurgy.metallurgy.machines.crusher;

import com.teammetallurgy.metallurgy.machines.TileEntityMetallurgy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerCrusher extends Container
{

    public ContainerCrusher(InventoryPlayer inventoryPlayer, TileEntityMetallurgy tileEntity)
    {

    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }

}
