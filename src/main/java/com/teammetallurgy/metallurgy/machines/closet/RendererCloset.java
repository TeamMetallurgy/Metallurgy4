package com.teammetallurgy.metallurgy.machines.closet;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RendererCloset extends TileEntitySpecialRenderer
{
    private ModelCloset model = new ModelCloset(); 
    private ResourceLocation texture = new ResourceLocation("metallurgy:textures/models/machines/cabinet_platinum.png");

    public void renderClosetAt(TileEntityCloset teCloset, double xPos, double yPos, double zPos, float delta)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) xPos + 0.5F, (float) yPos + 1.5F, (float) zPos + 0.5F);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        this.bindTexture(texture);
        this.model.renderAllModels();
        GL11.glPopMatrix();
    }
    
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double xPos, double yPos, double zPos, float delta)
    {
        renderClosetAt((TileEntityCloset)tileEntity, xPos, yPos, zPos, delta);
    }

}
