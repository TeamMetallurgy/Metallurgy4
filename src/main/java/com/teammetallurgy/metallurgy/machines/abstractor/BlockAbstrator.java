package com.teammetallurgy.metallurgy.machines.abstractor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgy.machines.BlockMetallurgy;

public class BlockAbstrator extends BlockMetallurgy
{
    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TileEntityAbstractor();
    }

    @Override
    protected void doOnActivate(World arg0, int arg1, int arg2, int arg3, EntityPlayer arg4, int arg5, float arg6, float arg7, float arg8)
    {
        // TODO Auto-generated method stub

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
