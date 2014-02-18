package com.teammetallurgy.metallurgy.machines.alloyer;

import com.teammetallurgy.metallurgycore.machines.ContainerMetallurgyMachine;
import com.teammetallurgy.metallurgycore.machines.GUIMetallurgyMachine;

public class GUIAlloyer extends GUIMetallurgyMachine
{

    public GUIAlloyer(ContainerMetallurgyMachine container)
    {
        super(container, "metallurgy:textures/gui/alloyer.png");
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        super.drawGuiContainerBackgroundLayer(f, i, j);

        int i1;
        if (this.tileEntity.isBurning())
        {
            i1 = this.tileEntity.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(this.guiLeft + 9, this.guiTop + 27 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }

        i1 = this.tileEntity.getCookProgressScaled(21);
        this.drawTexturedModalRect(this.guiLeft + 67, this.guiTop + 33, 176, 14, 50, i1);
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.xSize = 176;
        this.ySize = 165;
    }

}
