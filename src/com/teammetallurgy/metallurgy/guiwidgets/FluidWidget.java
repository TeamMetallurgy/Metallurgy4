package com.teammetallurgy.metallurgy.guiwidgets;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import org.lwjgl.input.Mouse;

import com.teammetallurgy.metallurgy.machines.GUIMetallurgy;

public class FluidWidget
{
    private final FluidTank tank;
    private final int x, y, u, v, w, h;

    public FluidWidget(final FluidTank tank, final int x, final int y, final int u, final int v, final int w, final int h)
    {
        this.tank = tank;
        this.x = x;
        this.y = y;
        this.u = u;
        this.v = v;
        this.w = w;
        this.h = h;
    }

    public void drawLiquid(final GUIMetallurgy gui, final int guiX, final int guiY, final ResourceLocation texture)
    {
        if (this.tank == null) { return; }
        final FluidStack fluidStack = this.tank.getFluid();
        if (fluidStack == null || fluidStack.amount <= 0 || fluidStack.getFluid() == null) { return; }

        final Icon liquidIcon = FluidRender.getFluidTexture(fluidStack, false);

        if (liquidIcon == null) { return; }

        final float scale = Math.min(fluidStack.amount, this.tank.getCapacity()) / (float) this.tank.getCapacity();

        GUIMetallurgy.bindTexture(FluidRender.getFluidSheet(fluidStack));

        for (int col = 0; col < this.w / 16; col++)
        {
            for (int row = 0; row <= this.h / 16; row++)
            {
                gui.drawTexturedModelRectFromIcon(guiX + this.x + col * 16, guiY + this.y + row * 16, liquidIcon, 16, 16);
            }
        }

        GUIMetallurgy.bindTexture(texture);

        gui.drawTexturedModalRect(guiX + this.x, guiY + this.y - 1, this.x, this.y - 1, this.w, this.h - (int) Math.floor(this.h * scale) + 1);
        gui.drawTexturedModalRect(guiX + this.x, guiY + this.y, this.u, this.v, this.w, this.h);

    }

    public static void bindTexture(final ResourceLocation texture)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
    }

}
