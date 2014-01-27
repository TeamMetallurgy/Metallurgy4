package com.teammetallurgy.metallurgy.guiwidgets;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import com.teammetallurgy.metallurgy.guiwidgets.RenderEntityBlock.RenderInfo;

public class FluidRender
{

    private static final ResourceLocation BLOCK_TEXTURE = TextureMap.locationBlocksTexture;
    public static final int DISPLAY_STAGES = 73;
    private static Map<Fluid, int[]> flowingRenderCache = new HashMap<Fluid, int[]>();
    private static final RenderInfo liquidBlock = new RenderInfo();
    private static Map<Fluid, int[]> stillRenderCache = new HashMap<Fluid, int[]>();

    public static int[] getFluidDisplayLists(final FluidStack fluidStack, final World world, final boolean flowing)
    {
        if (fluidStack == null) { return null; }
        final Fluid fluid = fluidStack.getFluid();
        if (fluid == null) { return null; }
        final Map<Fluid, int[]> cache = flowing ? FluidRender.flowingRenderCache : FluidRender.stillRenderCache;
        int[] diplayLists = cache.get(fluid);
        if (diplayLists != null) { return diplayLists; }

        diplayLists = new int[FluidRender.DISPLAY_STAGES];

        if (fluid.getBlockID() > 0)
        {
            FluidRender.liquidBlock.baseBlock = Block.blocksList[fluid.getBlockID()];
            FluidRender.liquidBlock.texture = FluidRender.getFluidTexture(fluidStack, flowing);
        }
        else
        {
            FluidRender.liquidBlock.baseBlock = Block.waterStill;
            FluidRender.liquidBlock.texture = FluidRender.getFluidTexture(fluidStack, flowing);
        }

        cache.put(fluid, diplayLists);

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);

        for (int s = 0; s < FluidRender.DISPLAY_STAGES; ++s)
        {
            diplayLists[s] = GLAllocation.generateDisplayLists(1);
            GL11.glNewList(diplayLists[s], 4864 /* GL_COMPILE */);

            FluidRender.liquidBlock.minX = 0.01f;
            FluidRender.liquidBlock.minY = 0;
            FluidRender.liquidBlock.minZ = 0.01f;

            FluidRender.liquidBlock.maxX = 0.99f;
            FluidRender.liquidBlock.maxY = (float) s / (float) FluidRender.DISPLAY_STAGES;
            FluidRender.liquidBlock.maxZ = 0.99f;

            RenderEntityBlock.INSTANCE.renderBlock(FluidRender.liquidBlock, world, 0, 0, 0, false, true);

            GL11.glEndList();
        }

        GL11.glColor4f(1, 1, 1, 1);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);

        return diplayLists;
    }

    public static ResourceLocation getFluidSheet(final Fluid liquid)
    {
        return FluidRender.BLOCK_TEXTURE;
    }

    public static ResourceLocation getFluidSheet(final FluidStack liquid)
    {
        if (liquid == null) { return FluidRender.BLOCK_TEXTURE; }
        return FluidRender.getFluidSheet(liquid.getFluid());
    }

    public static Icon getFluidTexture(final Fluid fluid, final boolean flowing)
    {
        if (fluid == null) { return null; }
        Icon icon = flowing ? fluid.getFlowingIcon() : fluid.getStillIcon();
        if (icon == null)
        {
            icon = ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
        }
        return icon;
    }

    public static Icon getFluidTexture(final FluidStack fluidStack, final boolean flowing)
    {
        if (fluidStack == null) { return null; }
        return FluidRender.getFluidTexture(fluidStack.getFluid(), flowing);
    }

    public static void setColorForFluidStack(final FluidStack fluidstack)
    {
        if (fluidstack == null) { return; }

        final int color = fluidstack.getFluid().getColor(fluidstack);
        final float red = (color >> 16 & 255) / 255.0F;
        final float green = (color >> 8 & 255) / 255.0F;
        final float blue = (color & 255) / 255.0F;
        GL11.glColor4f(red, green, blue, 1);
    }

}
