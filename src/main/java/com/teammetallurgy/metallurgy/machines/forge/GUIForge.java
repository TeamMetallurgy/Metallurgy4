package com.teammetallurgy.metallurgy.machines.forge;

import java.util.Arrays;
import java.util.List;

import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import com.teammetallurgy.metallurgycore.guiwidgets.FluidWidget;
import com.teammetallurgy.metallurgycore.machines.ContainerMetallurgyMachine;
import com.teammetallurgy.metallurgycore.machines.GUIMetallurgyMachine;

public class GUIForge extends GUIMetallurgyMachine
{

    public static FluidStack getFluidInfo(final FluidTank localTank)
    {
        if (localTank == null || localTank.getFluid() == null) { return null; }

        return localTank.getFluid().copy();
    }

    private FluidWidget fluidWidget;

    private FluidTank tank;

    public GUIForge(ContainerMetallurgyMachine container)
    {
        super(container, "metallurgy:textures/gui/forge.png");
        this.tank = ((TileEntityForge) this.tileEntity).getTank();
        this.fluidWidget = new FluidWidget(this.tank, 8, 14, 179, 21, 16, 65);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        super.drawGuiContainerBackgroundLayer(f, i, j);

        int i1 = this.tileEntity.getCookProgressScaled(21);
        this.drawTexturedModalRect(this.guiLeft + 82, this.guiTop + 35, 176, 0, 12, i1);

        this.fluidWidget.drawLiquid(this, this.guiLeft, this.guiTop, this.texture);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseZ)
    {
        this.drawTitle(30, 8);

        boolean isMouseInTank = mouseX >= this.guiLeft + 8 && mouseZ >= this.guiTop + 14 && mouseX <= this.guiLeft + 24 && mouseZ <= this.guiTop + 78;

        if (isMouseInTank)
        {
            this.drawTankInfo(mouseX - this.guiLeft, mouseZ - this.guiTop);
        }
    }

    public void drawTankInfo(int x, final int y)
    {
        if (this.tank == null) { return; }

        final FluidStack fluidInfo = GUIForge.getFluidInfo(this.tank);

        if (fluidInfo == null) { return; }

        final String fluidName = fluidInfo.getLocalizedName();
        final int amount = fluidInfo.amount;

        String localizedTankInfo = StatCollector.translateToLocal("tooltip.metallurgy.tankinfo");
        localizedTankInfo = String.format(localizedTankInfo, fluidName, amount);

        int lineSeprator = localizedTankInfo.indexOf("||");

        final List<String> ret = Arrays.asList(new String[] { localizedTankInfo.substring(0, lineSeprator), localizedTankInfo.substring(lineSeprator + 3) });

        this.drawHoveringText(ret, x, y, this.fontRendererObj);

    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.xSize = 176;
        this.ySize = 165;
    }
}
