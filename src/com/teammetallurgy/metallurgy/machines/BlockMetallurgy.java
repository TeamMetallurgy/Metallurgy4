package com.teammetallurgy.metallurgy.machines;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgy.Metallurgy;

public abstract class BlockMetallurgy extends BlockContainer
{

    public BlockMetallurgy(int id)
    {
        super(id, Material.rock);
    }

    @Override
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return Metallurgy.instance.creativeTabMachines;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset)
    {
        if (player.isSneaking()) { return false; }

        if (!world.isRemote)
        {
            doOnActivate(world, x, y, z, player, side, xOffset, yOffset, zOffset);
        }

        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int oldID, int oldMeta)
    {
        TileEntityMetallurgy blockTileEntity = (TileEntityMetallurgy) world.getBlockTileEntity(x, y, z);
        if (blockTileEntity != null)
        {
            blockTileEntity.dropContents();
        }
    }

    /**
     * Determines what happens after block as been clicked. Only runs on server
     * 
     * @param world
     * @param x
     * @param y
     * @param z
     * @param player
     * @param side
     * @param xOffset
     * @param yOffset
     * @param zOffset
     */
    abstract protected void doOnActivate(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset);
}