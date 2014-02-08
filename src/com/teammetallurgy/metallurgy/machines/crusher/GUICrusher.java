package com.teammetallurgy.metallurgy.machines.crusher;

import com.teammetallurgy.metallurgycore.machines.ContainerMetallurgy;
import com.teammetallurgy.metallurgycore.machines.GUIMetallurgy;

public class GUICrusher extends GUIMetallurgy
{
    public GUICrusher(ContainerMetallurgy container)
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
