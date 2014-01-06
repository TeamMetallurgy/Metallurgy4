package com.teammetallurgy.metallurgy.machines.crusher;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GUICrusher extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation("metallurgy:textures/gui/crusher.png");

    public GUICrusher(ContainerCrusher crusher)
    {
        super(crusher);
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
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(I18n.getString("container.crusher"), 8, 6, 4210752);

    }
}
