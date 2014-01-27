package com.teammetallurgy.metallurgy.utils;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;

import org.lwjgl.opengl.GL11;

public class UtilRender
{

    public static void renderAllSides(RenderBlocks renderblocks, Block block, int meta, Icon icon)
    {

        Tessellator tessellator = Tessellator.instance;
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderblocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, icon != null ? icon : block.getIcon(1, meta));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderblocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, icon != null ? icon : block.getIcon(0, meta));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderblocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, icon != null ? icon : block.getIcon(5, meta));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderblocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, icon != null ? icon : block.getIcon(4, meta));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderblocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, icon != null ? icon : block.getIcon(3, meta));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderblocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, icon != null ? icon : block.getIcon(2, meta));
        tessellator.draw();

        GL11.glTranslatef(0.5F, 0.5F, 0.5F);

    }

}
