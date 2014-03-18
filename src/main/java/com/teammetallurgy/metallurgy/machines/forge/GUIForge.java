package com.teammetallurgy.metallurgy.machines.forge;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import org.lwjgl.input.Mouse;

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
        this.fluidWidget = new FluidWidget(this.tank, 8, 14, 179, 21, 16, 50);
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
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.drawTitle(30, 8);
    }

    @Override
    public void drawScreen(int par1, int par2, float par3)
    {
        super.drawScreen(par1, par2, par3);

        if (GuiScreen.isShiftKeyDown())
        {
            this.drawTankInfo(Mouse.getX() * this.width / this.mc.displayWidth - this.guiLeft, this.height - Mouse.getY() * this.height / this.mc.displayHeight - 1 - this.guiTop);
        }
    }

    public void drawTankInfo(int x, final int y)
    {
        final FluidTank localTank = this.getTankAtCoord(x, y);

        if (localTank == null) { return; }

        final FluidStack fluidInfo = GUIForge.getFluidInfo(localTank);

        if (fluidInfo == null) { return; }

        final String fluidName = FluidRegistry.getFluidName(fluidInfo);
        final int amount = fluidInfo.amount;

        final List<String> ret = Arrays.asList(new String[] { "Name: " + fluidName, "Amount: " + amount + "mB" });

        this.drawHoveringText(ret, x - 5 + this.guiLeft / 4, y + this.guiTop, this.fontRendererObj);

    }

    public FluidTank getTankAtCoord(int x, int y)
    {
        if (x >= 8 && x <= 16 + 8 && y >= 14 && y <= 14 + 65) { return this.tank; }

        return null;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.xSize = 176;
        this.ySize = 165;
    }
}
