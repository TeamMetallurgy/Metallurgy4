package com.teammetallurgy.metallurgy.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgy.machines.TileEntityMetallurgy;
import com.teammetallurgy.metallurgy.machines.alloyer.ContainerAlloyer;
import com.teammetallurgy.metallurgy.machines.alloyer.GUIAlloyer;
import com.teammetallurgy.metallurgy.machines.crusher.ContainerCrusher;
import com.teammetallurgy.metallurgy.machines.crusher.GUICrusher;
import com.teammetallurgy.metallurgy.machines.forge.ContainerForge;
import com.teammetallurgy.metallurgy.machines.forge.GUIForge;

import cpw.mods.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler
{

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (tileEntity instanceof TileEntityMetallurgy)
        {
            TileEntityMetallurgy te = (TileEntityMetallurgy) tileEntity;
            switch (ID)
            {
                case 0:
                    return new ContainerCrusher(player.inventory, te);
                case 1:
                    return new ContainerAlloyer(player.inventory, te);
                case 2:
                    return new ContainerForge(player.inventory, te);
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (tileEntity instanceof TileEntityMetallurgy)
        {
            TileEntityMetallurgy te = (TileEntityMetallurgy) tileEntity;
            switch (ID)
            {
                case 0:
                    return new GUICrusher(new ContainerCrusher(player.inventory, te));
                case 1:
                    return new GUIAlloyer(new ContainerAlloyer(player.inventory, te));
                case 2:
                    return new GUIForge(new ContainerForge(player.inventory, te));
            }
        }
        return null;
    }

}
