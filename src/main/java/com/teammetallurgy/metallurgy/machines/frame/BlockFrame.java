package com.teammetallurgy.metallurgy.machines.frame;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgy.machines.BlockMetallurgy;

public class BlockFrame extends BlockMetallurgy
{
    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return null;
    }
    
    @Override
    protected void doOnActivate(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset)
    {
        
    }
}
