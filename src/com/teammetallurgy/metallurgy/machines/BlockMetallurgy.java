package com.teammetallurgy.metallurgy.machines;

import com.teammetallurgy.metallurgy.Metallurgy;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class BlockMetallurgy extends BlockContainer
{

    public BlockMetallurgy(int id)
    {
        super(id, Material.rock);
    }

    @Override
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return Metallurgy.instance.creativeTabs;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset)
    {
        if (player.isSneaking()) { return false; }

        if (!world.isRemote)
        {
            doOnActivate(world, x, y, z, player);
        }

        return true;
    }

    /**
     * Determines what happens after block as been clicked. Only runs on server
     * 
     * @param world
     * @param x
     * @param y
     * @param z
     * @param player
     */
    abstract protected void doOnActivate(World world, int x, int y, int z, EntityPlayer player);
}