package com.teammetallurgy.metallurgy.machines.abstractor;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgy.machines.BlockMetallurgy;

public class BlockAbstrator extends BlockMetallurgy
{

    public BlockAbstrator()
    {
        super();
        this.textureName = "metallurgy:machines/abstractor";
    }

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
    public void onBlockPlacedBy(World world, int coordX, int coordY, int coordZ, EntityLivingBase livingEntity, ItemStack blockItemStack)
    {
        int l = MathHelper.floor_double((double) (livingEntity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0)
        {
            world.setBlockMetadataWithNotify(coordX, coordY, coordZ, 2, 2);
        }

        if (l == 1)
        {
            world.setBlockMetadataWithNotify(coordX, coordY, coordZ, 5, 2);
        }

        if (l == 2)
        {
            world.setBlockMetadataWithNotify(coordX, coordY, coordZ, 3, 2);
        }

        if (l == 3)
        {
            world.setBlockMetadataWithNotify(coordX, coordY, coordZ, 4, 2);
        }

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
