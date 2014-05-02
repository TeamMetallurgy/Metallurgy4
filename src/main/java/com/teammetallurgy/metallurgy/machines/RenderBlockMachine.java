package com.teammetallurgy.metallurgy.machines;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class RenderBlockMachine implements ISimpleBlockRenderingHandler
{
    
    public static int renderId = 0;
    
    public RenderBlockMachine ()
    {
        RenderBlockMachine.renderId = RenderingRegistry.getNextAvailableRenderId();
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
    {
        Tessellator tess = Tessellator.instance;
        
        block.setBlockBoundsForItemRender();
        renderer.setRenderBoundsFromBlock(block);
        
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        
        tess.startDrawingQuads();
        
        drawMachine(block,3,tess,renderer);
        
        tess.draw();
        
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        
        renderer.renderStandardBlock(block, x,y, z);
        
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId)
    {
        return true;
    }

    @Override
    public int getRenderId()
    {
        return RenderBlockMachine.renderId;
    }
    
    private void drawMachine (Block block, int meta, Tessellator tess, RenderBlocks renderer)
    {
        tess.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D,0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
        tess.draw();
        tess.startDrawingQuads();
        tess.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D,0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
        tess.draw();
        tess.startDrawingQuads();
        tess.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D,0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
        tess.draw();
        tess.startDrawingQuads();
        tess.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D,0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
        tess.draw();
        tess.startDrawingQuads();
        tess.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D,0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
    }

}
