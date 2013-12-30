package com.teammetallurgy.metallurgy.machines.crusher;

import com.teammetallurgy.metallurgy.Metallurgy;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCrusher extends BlockContainer
{

    public BlockCrusher(int id)
    {
        super(id, Material.rock);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityCrusher();
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
            player.openGui(Metallurgy.instance, 0, world, x, y, z);
        }

        return true;
    }
}
