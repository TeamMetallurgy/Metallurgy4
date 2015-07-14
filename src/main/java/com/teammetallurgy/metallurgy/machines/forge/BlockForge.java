package com.teammetallurgy.metallurgy.machines.forge;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.teammetallurgy.metallurgy.Metallurgy;
import com.teammetallurgy.metallurgy.lib.GUIIds;
import com.teammetallurgy.metallurgy.machines.BlockMetallurgy;
import com.teammetallurgy.metallurgycore.machines.TileEntityMetallurgy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockForge extends BlockMetallurgy
{
    private String topTexture = "metallurgy:machines/smelter_top";
    private String sideTexture = "metallurgy:machines/smelter_side";
    private String sideConnectedTexture = "metallurgy:machines/smelter_side_connected";
    private String frontTexture = "metallurgy:machines/smelter_front";
    private String bottomTexture = "metallurgy:machines/smelter_bottom";
    private String frontOnTexture = "metallurgy:machines/smelter_front_on";

    private IIcon topIcon;
    private IIcon sideIcon;
    private IIcon sideConnectedIcon;
    private IIcon frontIcon;
    private IIcon bottomIcon;
    private IIcon frontOnIcon;

    public BlockForge()
    {
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityForge();
    }

    @Override
    protected void doOnActivate(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset)
    {
        player.openGui(Metallurgy.instance, GUIIds.FORGE, world, x, y, z);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset)
    {

        TileEntityForge blockTileEntity = (TileEntityForge) world.getTileEntity(x, y, z);

        ItemStack equippedItem = player.getCurrentEquippedItem();

        if (equippedItem != null && equippedItem.getItem() == Items.lava_bucket)
        {
            FluidStack stack = new FluidStack(FluidRegistry.LAVA, FluidContainerRegistry.BUCKET_VOLUME);
            int fill = blockTileEntity.fill(ForgeDirection.UNKNOWN, stack, false);

            if (fill == FluidContainerRegistry.BUCKET_VOLUME)
            {
                blockTileEntity.fill(ForgeDirection.UNKNOWN, stack, true);
                equippedItem.stackSize--;

                if (equippedItem.stackSize <= 0)
                {
                    ItemStack stack2 = equippedItem.getItem().getContainerItem(equippedItem);
                    player.inventory.addItemStackToInventory(stack2);
                }

                world.markBlockForUpdate(x, y, z);
                return true;
            }
        }

        return super.onBlockActivated(world, x, y, z, player, side, xOffset, yOffset, zOffset);
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
        this.sideConnectedIcon = register.registerIcon(this.sideConnectedTexture);
        this.frontOnIcon = register.registerIcon(this.frontOnTexture);
    }

}
