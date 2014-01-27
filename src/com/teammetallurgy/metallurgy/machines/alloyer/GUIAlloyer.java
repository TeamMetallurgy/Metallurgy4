package com.teammetallurgy.metallurgy.machines.alloyer;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.teammetallurgy.metallurgy.machines.ContainerMetallurgy;
import com.teammetallurgy.metallurgy.machines.GUIMetallurgy;
import com.teammetallurgy.metallurgy.machines.TileEntityMetallurgy;

public class GUIAlloyer extends GUIMetallurgy
{

    public GUIAlloyer(ContainerMetallurgy container)
    {
        super(container, "metallurgy:textures/gui/alloyer.png");
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.xSize = 176;
        this.ySize = 165;
    }

}
