package com.teammetallurgy.metallurgy.machines.crusher;

import com.teammetallurgy.metallurgycore.machines.ContainerMetallurgyMachine;
import com.teammetallurgy.metallurgycore.machines.GUIMetallurgyMachine;

public class GUICrusher extends GUIMetallurgyMachine
{
    public GUICrusher(ContainerMetallurgyMachine container)
    {
        super(container, "metallurgy:textures/gui/crusher.png");
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.xSize = 176;
        this.ySize = 165;
    }
}
