package com.teammetallurgy.metallurgy.tnt;

import org.lwjgl.opengl.GL11;

import com.teammetallurgy.metallurgy.BlockList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderExplosive extends Render
{
    private RenderBlocks blockRenderer = new RenderBlocks();

    public RenderExplosive()
    {
        this.shadowSize = 0.5F;
    }

    public void doRender(EntityExplosive entity, double xPos, double yPos, double zPos, float rotationYaw, float brightness)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) xPos, (float) yPos, (float) zPos);

        float whiteTransparancy;
        if (entity.getFuse() - brightness + 1.0F < 10.F)
        {
            whiteTransparancy = 1.0F - (entity.getFuse() - brightness + 1.0F) / 10.0F;
            if (whiteTransparancy < 0.0F)
            {
                whiteTransparancy = 0.0F;
            }

            if (whiteTransparancy > 1.0F)
            {
                whiteTransparancy = 1.0F;
            }

            whiteTransparancy = whiteTransparancy * whiteTransparancy * whiteTransparancy;
            float lightPos = 1.0F + whiteTransparancy * 0.3F;
            GL11.glScalef(lightPos, lightPos, lightPos);
        }

        whiteTransparancy = ((entity.getFuse() - brightness + 1.0F) / 100.0F) * 0.8F;

        this.bindEntityTexture(entity);
        this.blockRenderer.renderBlockAsItem(BlockList.getExplosive(), entity.getType(), entity.getBrightness(brightness));

        // Flickering
        if (entity.getFuse() / 5 % 2 == 0)
        {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, whiteTransparancy);
            this.blockRenderer.renderBlockAsItem(BlockList.getExplosive(), entity.getType(), 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }

        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity, double xPos, double yPos, double zPos, float rotationYaw, float brightness)
    {
        this.doRender((EntityExplosive) entity, xPos, yPos, zPos, rotationYaw, brightness);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return TextureMap.locationBlocksTexture;
    }

}
