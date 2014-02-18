package com.teammetallurgy.metallurgy.machines.crusher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgy.Metallurgy;
import com.teammetallurgy.metallurgy.lib.GUIIds;
import com.teammetallurgy.metallurgy.machines.BlockMetallurgy;

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

    @Override
    protected void doOnActivate(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset)
    {
        player.openGui(Metallurgy.instance, GUIIds.CRUSHER, world, x, y, z);
    }
}
