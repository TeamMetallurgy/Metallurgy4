package com.teammetallurgy.metallurgy.tnt;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTNT;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgy.Metallurgy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockExplosive extends BlockTNT
{
    private IIcon topIcon;
    private IIcon bottomIcon;

    private String[] types = { "he", "le", "m", "de", "pe" };
    private IIcon[] topIcons = new IIcon[types.length];
    private IIcon[] bottomIcons = new IIcon[types.length];
    private IIcon[] sideIcons = new IIcon[types.length];

    public BlockExplosive()
    {
        this.setCreativeTab(Metallurgy.instance.creativeTabBlocks);
        this.setBlockName("metallurgy.explosive");
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        for (int i = 0; i < types.length; i++)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon("metallurgy:misc/he_tnt_side");
        topIcon = register.registerIcon("metallurgy:misc/he_tnt_top");
        bottomIcon = register.registerIcon("metallurgy:misc/he_tnt_bottom");

        for (int i = 0; i < types.length; i++)
        {
            topIcons[i] = register.registerIcon("metallurgy:misc/" + types[i] + "_tnt_top");
            bottomIcons[i] = register.registerIcon("metallurgy:misc/" + types[i] + "_tnt_bottom");
            sideIcons[i] = register.registerIcon("metallurgy:misc/" + types[i] + "_tnt_side");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        if (meta >= types.length || meta < 0)
        {
            switch (side)
            {
                case 0:
                    return bottomIcon;
                case 1:
                    return topIcon;
                default:
                    return blockIcon;
            }
        }
        else
        {
            switch (side)
            {
                case 0:
                    return bottomIcons[meta];
                case 1:
                    return topIcons[meta];
                default:
                    return sideIcons[meta];
            }
        }
    }

    public String getUnlocalizedName(int meta)
    {
        if (meta >= types.length || meta < 0)
        {
            return this.getUnlocalizedName();
        }
        else
        {
            return "tile.metallurgy.explosives." + this.types[meta];
        }
    }

    @Override
    public int damageDropped(int metaId)
    {
        return metaId;
    }

    @Override
    public void onBlockAdded(World world, int xPos, int yPos, int zPos)
    {
        int metaId = world.getBlockMetadata(xPos, yPos, zPos);
        if (world.isBlockIndirectlyGettingPowered(xPos, yPos, zPos))
        {
            this.ignite(world, xPos, yPos, zPos, metaId, null);
            world.setBlockToAir(xPos, yPos, zPos);
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int xPos, int yPos, int zPos, Block block)
    {
        int metaId = world.getBlockMetadata(xPos, yPos, zPos);
        if (world.isBlockIndirectlyGettingPowered(xPos, yPos, zPos))
        {
            this.ignite(world, xPos, yPos, zPos, metaId, null);
            world.setBlockToAir(xPos, yPos, zPos);
        }
    }

    // Overridden instead of onBlockDestroyedByExplosion to get the meta before the block gets destroyed
    @Override
    public void onBlockExploded(World world, int xPos, int yPos, int zPos, Explosion explosion)
    {
        if (!world.isRemote)
        {
            int meta = world.getBlockMetadata(xPos, yPos, zPos);
            EntityExplosive entityExplosive = new EntityExplosive(world, (double) ((float) xPos + 0.5F), (double) ((float) yPos + 0.5F), (double) ((float) zPos + 0.5F), (byte) meta,
                    explosion.getExplosivePlacedBy());
            byte fuseLength = (byte) (world.rand.nextInt(20) + 10);
            entityExplosive.setFuse(fuseLength);
            world.spawnEntityInWorld(entityExplosive);
        }
        super.onBlockExploded(world, xPos, yPos, zPos, explosion);
    }
    
    @Override
    public void onBlockDestroyedByExplosion(World world, int xPos, int yPos, int zPos, Explosion explosion)
    {
        // Overriding to disable
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, int xPos, int yPos, int zPos, int metaId)
    {
        // Overriding to disable
    }

    @Override
    public void func_150114_a(World world, int xPos, int yPos, int zPos, int metaId, EntityLivingBase livingEntity)
    {
        this.ignite(world, xPos, yPos, zPos, metaId, livingEntity);
    }

    public void ignite(World world, int xPos, int yPos, int zPos, int metaId, EntityLivingBase livingEntity)
    {
        if (!world.isRemote)
        {

            EntityExplosive entitytExplosive = new EntityExplosive(world, (double) (xPos + 0.5D), (double) (yPos + 0.5D), (double) (zPos + 0.5D), (byte) metaId, livingEntity);
            world.spawnEntityInWorld(entitytExplosive);
            world.playSoundAtEntity(entitytExplosive, "game.tnt.primed", 1.0F, 1.0F);

        }
    }

    @Override
    public boolean onBlockActivated(World world, int xPos, int yPos, int zPos, EntityPlayer player, int side, float xDistance, float yDistance, float zDistance)
    {
        if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.flint_and_steel)
        {
            int metaId = world.getBlockMetadata(xPos, yPos, zPos);
            this.ignite(world, xPos, yPos, zPos, metaId, player);
            world.setBlockToAir(xPos, yPos, zPos);
            player.getCurrentEquippedItem().damageItem(1, player);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int xPos, int yPos, int zPos, Entity entity)
    {
        if (entity instanceof EntityArrow && !world.isRemote)
        {
            EntityArrow entityarrow = (EntityArrow) entity;

            if (entityarrow.isBurning())
            {
                int metaId = world.getBlockMetadata(xPos, yPos, zPos);
                this.ignite(world, xPos, yPos, zPos, metaId, entityarrow.shootingEntity instanceof EntityLivingBase ? (EntityLivingBase) entityarrow.shootingEntity : null);
                world.setBlockToAir(xPos, yPos, zPos);
            }
        }
    }
}
