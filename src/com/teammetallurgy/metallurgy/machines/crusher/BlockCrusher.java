package com.teammetallurgy.metallurgy.machines.crusher;

import com.teammetallurgy.metallurgy.Metallurgy;
import com.teammetallurgy.metallurgy.machines.BlockMetallurgy;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCrusher extends BlockMetallurgy
{

    public BlockCrusher(int id)
    {
        super(id);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityCrusher();
    }

    protected void doOnActivate(World world, int x, int y, int z, EntityPlayer player)
    {
        player.openGui(Metallurgy.instance, 0, world, x, y, z);
    }
}
