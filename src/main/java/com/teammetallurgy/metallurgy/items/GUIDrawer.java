package com.teammetallurgy.metallurgy.items;

import net.minecraft.inventory.Container;

import com.teammetallurgy.metallurgycore.machines.GUIMetallurgy;

public class GUIDrawer extends GUIMetallurgy
{

    public GUIDrawer(Container container)
    {
        super(container, "metallurgy:textures/gui/drawer.png");
    }

    @Override
    protected String getInvName()
    {
        return "container.drawer";
    }

}
