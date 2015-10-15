package com.teammetallurgy.metallurgy.machines.crusher;

import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgy.Metallurgy;
import com.teammetallurgy.metallurgy.lib.GUIIds;
import com.teammetallurgy.metallurgy.machines.BlockMetallurgy;
import com.teammetallurgy.metallurgycore.machines.TileEntityMetallurgy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCrusher extends BlockMetallurgy
{

    private String topTexture = "metallurgy:machines/mframe_top";
    private String sideTexture = "metallurgy:machines/mframe_side";
    private String frontTexture = "metallurgy:machines/crusher_front";
    private String bottomTexture = "metallurgy:machines/mframe_bottom";
    private String frontOnTexture = "metallurgy:machines/crusher_front_on";

    private IIcon topIcon;
    private IIcon sideIcon;
    private IIcon frontIcon;
    private IIcon bottomIcon;
    private IIcon frontOnIcon;
    
    public BlockCrusher()
    {
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityCrusher();
    }

    @Override
    protected void doOnActivate(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset)
    {
        player.openGui(Metallurgy.instance, GUIIds.CRUSHER, world, x, y, z);
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
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        if (side == meta) { return this.frontIcon; }

        switch (side)
        {
            case 0:
                return this.bottomIcon;
            case 1:
                return this.topIcon;
            default:
                return this.sideIcon;
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {

        int meta = world.getBlockMetadata(x, y, z);
        TileEntity tileEntiy = world.getTileEntity(x, y, z);
        
        if (!(tileEntiy instanceof TileEntityMetallurgy))
        return this.sideIcon;
        
        boolean running = ((TileEntityMetallurgy)tileEntiy).isBurning();  
        
        if (side == meta){
            if (running)
                return frontOnIcon;
            else
                return frontIcon;
        }
        
        switch (side){
            case 0:
                return this.bottomIcon;
            case 1:
                return this.topIcon;
            default:
                    return this.sideIcon;
        }

    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        this.bottomIcon = register.registerIcon(this.bottomTexture);
        this.topIcon = register.registerIcon(this.topTexture);
        this.sideIcon = register.registerIcon(this.sideTexture);
        this.frontIcon = register.registerIcon(this.frontTexture);
        this.frontOnIcon = register.registerIcon(this.frontOnTexture);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        super.randomDisplayTick(world, x, y, z, random);

        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (!(tileEntity instanceof TileEntityCrusher)) return;

        if (!((TileEntityCrusher) tileEntity).isBurning()) return;

        int facing = world.getBlockMetadata(x, y, z);
        double centerX = x + 0.5D;
        double randomY = y + 0.0D + random.nextFloat() * 6.0D / 16.0D;
        double centerZ = z + 0.5D;
        double faceOffset = 0.6D;
        double randomOffset = random.nextFloat() * 0.6D - 0.3D;

        switch (facing)
        {
            case 2:
                world.spawnParticle("smoke", centerX + randomOffset, randomY, centerZ - faceOffset, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", centerX + randomOffset, randomY, centerZ - faceOffset, 0.0D, 0.0D, 0.0D);
                break;
            case 3:
                world.spawnParticle("smoke", centerX + randomOffset, randomY, centerZ + faceOffset, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", centerX + randomOffset, randomY, centerZ + faceOffset, 0.0D, 0.0D, 0.0D);
                break;
            case 4:
                world.spawnParticle("smoke", centerX - faceOffset, randomY, centerZ + randomOffset, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", centerX - faceOffset, randomY, centerZ + randomOffset, 0.0D, 0.0D, 0.0D);
                break;
            case 5:
                world.spawnParticle("smoke", centerX + faceOffset, randomY, centerZ + randomOffset, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", centerX + faceOffset, randomY, centerZ + randomOffset, 0.0D, 0.0D, 0.0D);
        }
    }
}
