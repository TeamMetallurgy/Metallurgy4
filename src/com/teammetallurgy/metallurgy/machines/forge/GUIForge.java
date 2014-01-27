package com.teammetallurgy.metallurgy.machines.forge;

import org.lwjgl.opengl.GL11;

import net.minecraftforge.fluids.FluidTank;

import com.teammetallurgy.metallurgy.guiwidgets.FluidWidget;
import com.teammetallurgy.metallurgy.machines.ContainerMetallurgy;
import com.teammetallurgy.metallurgy.machines.GUIMetallurgy;

public class GUIForge extends GUIMetallurgy
{

    private FluidWidget fluidWidget;
    private FluidTank tank;

    public GUIForge(ContainerMetallurgy container)
    {
        super(container, "metallurgy:textures/gui/forge.png");
        this.tank = ((TileEntityForge) this.tileEntity).getTank();
        this.fluidWidget = new FluidWidget(this.tank, 8, 14, 179, 21, 16, 75);
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.xSize = 176;
        this.ySize = 165;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(this.texture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        int i1;

        i1 = this.tileEntity.getCookProgressScaled(21);
        this.drawTexturedModalRect(this.guiLeft + 82, this.guiTop + 35, 176, 0, 12, i1);

        this.fluidWidget.drawLiquid(this, this.guiLeft, this.guiTop, this.texture);

        this.fontRenderer.drawString("Tank Level: " + this.tank.getFluidAmount(), 3, 3, 0xffffff);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        drawTitle(30, 8);
    }
}
