package com.teammetallurgy.metallurgy.machines.abstractor;

import com.teammetallurgy.metallurgycore.machines.ContainerMetallurgyMachine;
import com.teammetallurgy.metallurgycore.machines.GUIMetallurgyMachine;

public class GUIAbstrator extends GUIMetallurgyMachine
{

    public GUIAbstrator(ContainerMetallurgyMachine container)
    {
        super(container, "metallurgy:textures/gui/abstractor.png");
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.xSize = 175;
        this.ySize = 165;
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float renderTicks, int x, int y)
    {
        super.drawGuiContainerBackgroundLayer(renderTicks, x, y);
        
        // draw the background of the slots if they are empty
        if (this.tileEntity.getStackInSlot(0) == null)
            this.drawTexturedModalRect(this.guiLeft + 25, this.guiTop + 55, 194, 61, 18, 18);
        
        if (this.tileEntity.getStackInSlot(1) == null)
        this.drawTexturedModalRect(this.guiLeft + 16, this.guiTop + 18, 176, 43, 18, 18);
        
        if (this.tileEntity.getStackInSlot(2) == null)
            this.drawTexturedModalRect(this.guiLeft + 35, this.guiTop + 18, 194, 43, 18, 18);
        
        if (this.tileEntity.getStackInSlot(3) == null)
            this.drawTexturedModalRect(this.guiLeft + 97, this.guiTop + 18, 176, 61, 18, 18);
        
        
        // Progress bars
        
        this.drawTexturedModalRect(this.guiLeft + 27, this.guiTop + 40, 176, 0, 15, 15);
        
        this.drawTexturedModalRect(this.guiLeft + 59, this.guiTop + 20, 177, 15, 30, 14);
        
        this.drawTexturedModalRect(this.guiLeft + 117, this.guiTop + 20, 177, 29, 23, 14);
        
    }
    
    @Override
    protected void drawTitle(int x, int y)
    {
        // no needs to display the title
    }
}
