package com.teammetallurgy.metallurgy.machines.abstractor;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RendererAbstractor extends TileEntitySpecialRenderer
{
    private ModelAbstractorInActive modelOff = new ModelAbstractorInActive();
    private ModelAbstractorActive modelOn = new ModelAbstractorActive();
    private ResourceLocation textureOff = new ResourceLocation("metallurgy:textures/models/machines/abstractor_off.png");
    private ResourceLocation textureOn = new ResourceLocation("metallurgy:textures/models/machines/abstractor_on.png");

    public void renderAbstractorAt(TileEntityAbstractor teAbstractor, double xPos, double yPos, double zPos, float delta)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) xPos + 0.5F, (float) yPos + 1.5F, (float) zPos + 0.5F);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        if (teAbstractor.isBurning())
        {
            this.bindTexture(textureOn);
            this.modelOn.renderAllModels();
        }
        else
        {
            this.bindTexture(textureOff);
            this.modelOff.renderAllModels();
        }
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double xPos, double yPos, double zPos, float delta)
    {
        renderAbstractorAt((TileEntityAbstractor) tileEntity, xPos, yPos, zPos, delta);
    }

}
