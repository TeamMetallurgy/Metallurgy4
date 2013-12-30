package com.teammetallurgy.metallurgy.machines.crusher;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class GUICrusher extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation("textures/gui/container/generic_54.png");

    public GUICrusher(ContainerCrusher crusher)
    {
        super(crusher);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, width, height);
    }

}
