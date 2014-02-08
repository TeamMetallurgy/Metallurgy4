package com.teammetallurgy.metallurgy.machines.alloyer;

import org.lwjgl.opengl.GL11;

import com.teammetallurgy.metallurgycore.machines.ContainerMetallurgy;
import com.teammetallurgy.metallurgycore.machines.GUIMetallurgy;

public class GUIAlloyer extends GUIMetallurgy
{

    public GUIAlloyer(ContainerMetallurgy container)
    {
        super(container, "metallurgy:textures/gui/alloyer.png");
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(this.texture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

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
