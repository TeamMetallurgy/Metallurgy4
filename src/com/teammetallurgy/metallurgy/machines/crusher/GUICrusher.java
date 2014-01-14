package com.teammetallurgy.metallurgy.machines.crusher;

import org.lwjgl.opengl.GL11;

import com.teammetallurgy.metallurgy.machines.ContainerMetallurgy;
import com.teammetallurgy.metallurgy.machines.TileEntityMetallurgySided;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GUICrusher extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation("metallurgy:textures/gui/crusher.png");
    private TileEntityMetallurgySided tileEntity;

    public GUICrusher(ContainerMetallurgy container)
    {
        super(container);
        this.tileEntity = container.tileEntity;
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
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int i1;
        if (this.tileEntity.isBurning())
        {
            i1 = this.tileEntity.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(this.guiLeft + 9, this.guiTop + 27 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }

        i1 = this.tileEntity.getCookProgressScaled(21);
        this.drawTexturedModalRect(this.guiLeft + 82, this.guiTop + 33, 176, 14, 12, i1);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(I18n.getString(tileEntity.getInvName()), 8, 6, 4210752);

    }
}
