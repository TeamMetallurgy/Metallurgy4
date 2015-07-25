package com.teammetallurgy.metallurgy.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgy.items.ContainerDrawer;
import com.teammetallurgy.metallurgy.items.GUIDrawer;
import com.teammetallurgy.metallurgy.lib.GUIIds;
import com.teammetallurgy.metallurgy.machines.abstractor.ContainerAbstrator;
import com.teammetallurgy.metallurgy.machines.abstractor.GUIAbstrator;
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
    public Object getTEContainer(int ID, EntityPlayer player, TileEntityMetallurgy te)
    {
        switch (ID)
        {
            case GUIIds.CRUSHER:
                return new ContainerCrusher(player.inventory, te);
            case GUIIds.ALLOYER:
                return new ContainerAlloyer(player.inventory, te);
            case GUIIds.FORGE:
                return new ContainerForge(player.inventory, te);
            case GUIIds.ABSTRACTOR:
                return new ContainerAbstrator(player.inventory, te);
            default:
                return null;
        }
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        Object serverGuiElement = super.getServerGuiElement(ID, player, world, x, y, z);

        if (serverGuiElement == null)
        {
            serverGuiElement = getItemContainer(ID, player, player.getCurrentEquippedItem());
        }

        return serverGuiElement;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {

        Object clientGuiElement = super.getClientGuiElement(ID, player, world, x, y, z);

        if (clientGuiElement == null)
        {
            clientGuiElement = getItemGUI(ID, player, player.getCurrentEquippedItem());
        }

        return clientGuiElement;
    }

    private Object getItemGUI(int ID, EntityPlayer player, ItemStack itemStack)
    {
        switch (ID)
        {
            case GUIIds.DRAWER:
                return new GUIDrawer(new ContainerDrawer(player.inventory, itemStack));
            default:
                return null;
        }
    }

    private Object getItemContainer(int ID, EntityPlayer player, ItemStack itemStack)
    {
        switch (ID)
        {
            case GUIIds.DRAWER:
                return new ContainerDrawer(player.inventory, itemStack);
            default:
                return null;
        }
    }

    @Override
    public Object getTEGui(int ID, EntityPlayer player, TileEntityMetallurgy te)
    {
        switch (ID)
        {
            case GUIIds.CRUSHER:
                return new GUICrusher(new ContainerCrusher(player.inventory, te));
            case GUIIds.ALLOYER:
                return new GUIAlloyer(new ContainerAlloyer(player.inventory, te));
            case GUIIds.FORGE:
                return new GUIForge(new ContainerForge(player.inventory, te));
            case GUIIds.ABSTRACTOR:
                return new GUIAbstrator(new ContainerAbstrator(player.inventory, te));
            default:
                return null;
        }
    }

}
