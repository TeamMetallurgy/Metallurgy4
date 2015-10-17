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
        
        // Fuel
        if (this.tileEntity.getStackInSlot(0) == null) 
            this.drawTexturedModalRect(this.guiLeft + 25, this.guiTop + 55, 194, 47, 18, 18);
        
        // Catalyst
        if (this.tileEntity.getStackInSlot(1) == null)
        this.drawTexturedModalRect(this.guiLeft + 16, this.guiTop + 18, 176, 29, 18, 18);
        
        // Ingot
        if (this.tileEntity.getStackInSlot(2) == null)
            this.drawTexturedModalRect(this.guiLeft + 35, this.guiTop + 18, 194, 29, 18, 18);
        
        // Bottle
        if (this.tileEntity.getStackInSlot(3) == null)
            this.drawTexturedModalRect(this.guiLeft + 61, this.guiTop + 55, 176, 47, 18, 18);
        
        
        // Progress bars
        
        // Burning
        int burning = 0;
        int maxBurning = 15;

        if (burning < 0)
            burning = 0;
        if (burning > maxBurning)
            burning = maxBurning;

        this.drawTexturedModalRect(this.guiLeft + 27, this.guiTop + 40 + maxBurning - burning, 176, 0, 15, burning);

        // Processing
        int processing = 0;
        int maxProcessing = 30;

        if (processing < 0)
            processing = 0;
        if (processing > maxProcessing)
            processing = maxProcessing;

        this.drawTexturedModalRect(this.guiLeft + 59, this.guiTop + 20, 177, 15, processing, 14);

        // Essence Tank
        int essenceLevel = 0;
        int maxEssenceLevel = 53;

        if (essenceLevel < 0)
            essenceLevel = 0;
        if (essenceLevel > maxEssenceLevel)
            essenceLevel = maxEssenceLevel;

        this.drawTexturedModalRect(this.guiLeft + 98, this.guiTop + 19 + maxEssenceLevel - essenceLevel, 176, 65, 16, essenceLevel);

    }
    
    @Override
    protected void drawTitle(int x, int y)
    {
        // no needs to display the title
    }
}
