package com.teammetallurgy.metallurgy.handlers;

import net.minecraft.entity.player.EntityPlayer;

import com.teammetallurgy.metallurgy.machines.alloyer.ContainerAlloyer;
import com.teammetallurgy.metallurgy.machines.alloyer.GUIAlloyer;
import com.teammetallurgy.metallurgy.machines.crusher.ContainerCrusher;
import com.teammetallurgy.metallurgy.machines.crusher.GUICrusher;
import com.teammetallurgy.metallurgy.machines.forge.ContainerForge;
import com.teammetallurgy.metallurgy.machines.forge.GUIForge;
import com.teammetallurgy.metallurgycore.handlers.GUIHandler;
import com.teammetallurgy.metallurgycore.machines.TileEntityMetallurgy;

public class GUIHandlerMetallurgy extends GUIHandler
{

    @Override
    public Object getContainer(int ID, EntityPlayer player, TileEntityMetallurgy te)
    {
        switch (ID)
        {
            case 0:
                return new ContainerCrusher(player.inventory, te);
            case 1:
                return new ContainerAlloyer(player.inventory, te);
            case 2:
                return new ContainerForge(player.inventory, te);
            default:
                return null;
        }
    }

    @Override
    public Object getGui(int ID, EntityPlayer player, TileEntityMetallurgy te)
    {
        switch (ID)
        {
            case 0:
                return new GUICrusher(new ContainerCrusher(player.inventory, te));
            case 1:
                return new GUIAlloyer(new ContainerAlloyer(player.inventory, te));
            case 2:
                return new GUIForge(new ContainerForge(player.inventory, te));
            default:
                return null;
        }
    }

}
