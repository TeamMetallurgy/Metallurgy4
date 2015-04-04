package com.teammetallurgy.metallurgy.machines.closet;

import com.teammetallurgy.metallurgy.Metallurgy;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;


public class BlockCloset extends BlockContainer
{

    public BlockCloset()
    {
        super(Material.iron);
        this.textureName = "metallurgy:metal_block_default";
        this.setHardness(3.5F);
        this.setCreativeTab(Metallurgy.instance.creativeTabMachines);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TileEntityCloset();
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
}
