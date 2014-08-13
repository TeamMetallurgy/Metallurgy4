package com.teammetallurgy.metallurgy.metals;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MoltenMetalBlock extends BlockFluidClassic
{
    private IIcon stillIcon;
    private IIcon flowingIcon;
    
    private String stillIconTexture;
    private String flowIconTexture;

    private boolean writeFluidIcons = true;

    public MoltenMetalBlock(Fluid fluid, String unloclizedName, String texture)
    {
        super(fluid, Material.lava);
        this.setHardness(100.0F);
        this.setLightLevel(1.0F);
        this.setBlockName(unloclizedName);

        this.stillIconTexture = texture + "_molten_still";
        this.flowIconTexture = texture + "_molten_flow";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister icon)
    {
        this.stillIcon = icon.registerIcon(stillIconTexture);
        this.flowingIcon = icon.registerIcon(flowIconTexture);

        if(writeFluidIcons)
        {
            this.getFluid().setIcons(this.stillIcon, this.flowingIcon);
        }
    }

    public IIcon getStillIcon()
    {
        return stillIcon;
    }

    public IIcon getFlowingIcon()
    {
        return flowingIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        if (side <= 1)
        {
            return this.stillIcon;
        }
        else
        {
            return this.flowingIcon;
        }
    }

    public void disableWritingFluidIcons()
    {
        this.writeFluidIcons = false;
    }
}
