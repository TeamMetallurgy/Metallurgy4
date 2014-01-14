package com.teammetallurgy.metallurgy.machines.alloyer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgy.Metallurgy;
import com.teammetallurgy.metallurgy.machines.BlockMetallurgy;

public class BlockAlloyer extends BlockMetallurgy
{

    public BlockAlloyer(int id)
    {
        super(id);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityAlloyer();
    }

    @Override
    protected void doOnActivate(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset)
    {
        player.openGui(Metallurgy.instance, 1, world, x, y, z);
    }

}
