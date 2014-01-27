package com.teammetallurgy.metallurgy.machines;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public abstract class GUIMetallurgy extends GuiContainer
{

    protected ResourceLocation texture;
    protected TileEntityMetallurgy tileEntity;

    public GUIMetallurgy(ContainerMetallurgy container, String texture)
    {
        super(container);
        this.tileEntity = container.tileEntity;
        this.texture = new ResourceLocation(texture);
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
        this.drawTexturedModalRect(this.guiLeft + 82, this.guiTop + 33, 176, 14, 12, i1);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        drawTitle(8, 6);
    }

    protected void drawTitle(int x, int y)
    {
        this.fontRenderer.drawString(I18n.getString(this.tileEntity.getInvName()), x, y, 4210752);
    }

    public static void bindTexture(ResourceLocation resourceLocation)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(resourceLocation);
    }

}