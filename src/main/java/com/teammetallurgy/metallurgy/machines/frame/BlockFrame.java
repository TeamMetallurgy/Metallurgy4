package com.teammetallurgy.metallurgy.machines.frame;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgy.machines.BlockMetallurgy;

public class BlockFrame extends BlockMetallurgy
{

    private String botTexture = "metallurgy:machines/mframe_bottom";
    private String topTexture = "metallurgy:machines/mframe_top";
    private String sideTexture = "metallurgy:machines/mframe_side";

    private IIcon botIcon;
    private IIcon topIcon;
    private IIcon sideIcon;

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return null;
    }

    @Override
    protected void doOnActivate(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset)
    {

    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        switch (side)
        {
            case 0:
                return this.botIcon;
            case 1:
                return this.topIcon;
            default:
                return this.sideIcon;
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister register)
    {
        this.botIcon = register.registerIcon(this.botTexture);
        this.topIcon = register.registerIcon(this.topTexture);
        this.sideIcon = register.registerIcon(this.sideTexture);
    }
}
