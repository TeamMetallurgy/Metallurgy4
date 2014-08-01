package com.teammetallurgy.metallurgy.metals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.teammetallurgy.metallurgy.Metallurgy;
import com.teammetallurgy.metallurgy.handlers.BucketsHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemMoltenMetalBucket extends ItemBucket
{
    private HashMap<Integer, String> textures = new HashMap<Integer, String>();
    private HashMap<Integer, String> unlocalizedNames = new HashMap<Integer, String>();
    private HashMap<Integer, IIcon> icons = new HashMap<Integer, IIcon>();
    private String defaultFillingTexture = Metallurgy.MODID.toLowerCase() + ":fantasy/adamantine_bucket_fill";
    private IIcon defaultFilling;

    public ItemMoltenMetalBucket()
    {
        super(Blocks.air);
        this.setContainerItem(Items.bucket);
        this.setUnlocalizedName("metallurgy.empty.bucket");
        this.setCreativeTab(Metallurgy.instance.creativeTabItems);
        this.setTextureName("minecraft:bucket_empty");
        this.setMaxDamage(0);
        this.setHasSubtypes(true);

    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        Block fluidBlock = BucketsHandler.instance.getFilledBlock(stack);

        if (fluidBlock != Blocks.air)
        {
            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, false);

            if (movingobjectposition == null) { return stack; }

            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                int xPos = movingobjectposition.blockX;
                int yPos = movingobjectposition.blockY;
                int zPos = movingobjectposition.blockZ;

                if (!world.canMineBlock(player, xPos, yPos, zPos)) { return stack; }

                if (movingobjectposition.sideHit == 0)
                {
                    --yPos;
                }

                if (movingobjectposition.sideHit == 1)
                {
                    ++yPos;
                }

                if (movingobjectposition.sideHit == 2)
                {
                    --zPos;
                }

                if (movingobjectposition.sideHit == 3)
                {
                    ++zPos;
                }

                if (movingobjectposition.sideHit == 4)
                {
                    --xPos;
                }

                if (movingobjectposition.sideHit == 5)
                {
                    ++xPos;
                }

                if (!player.canPlayerEdit(xPos, yPos, zPos, movingobjectposition.sideHit, stack)) { return stack; }

               
                Material material = world.getBlock(xPos, yPos, zPos).getMaterial();
                boolean flag = !material.isSolid();

                if (world.isAirBlock(xPos, yPos, zPos) || flag)
                {

                    if (!world.isRemote && flag && !material.isLiquid())
                    {
                        world.func_147480_a(xPos, yPos, zPos, true);
                    }
                    world.setBlock(xPos, yPos, zPos, fluidBlock, 0, 3);
                    
                    if (!player.capabilities.isCreativeMode)
                    {
                        return (new ItemStack(Items.bucket));
                    }
                }
            }
        }
        return stack;
    }

    @Override
    public boolean tryPlaceContainedLiquid(World world, int xCoord, int yCoord, int zCoord)
    {
        return false;
    }
    
    // Rendering
    
    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public int getRenderPasses(int damage)
    {
        return 2;
    }

    public void addMaping(int id, String unlocalizedName, String texture)
    {

        unlocalizedNames.put(id, unlocalizedName);
        textures.put(id, texture);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        this.itemIcon = register.registerIcon(this.getIconString());

        // default filling texture
        this.defaultFilling = register.registerIcon(defaultFillingTexture);

        for (Entry<Integer, String> entry : textures.entrySet())
        {

            IIcon filling = register.registerIcon(entry.getValue());

            icons.put(entry.getKey(), filling);
        }
    }

    @Override
    public IIcon getIcon(ItemStack itemStack, int pass)
    {

        if ((pass == 0) || itemStack.getItemDamage() == 0)
        {

            return this.itemIcon;

        }
        else
        {

            int index = itemStack.getItemDamage();

            if (index <= 0) { return this.defaultFilling; }

            IIcon filling = icons.get(index);

            if (filling != null)
            {
                return filling;
            }
            else
            {
                return this.defaultFilling;
            }
        }
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubItems(Item item, CreativeTabs tab, List itemStackList)
    {
        if (!unlocalizedNames.isEmpty())
        {

            for (Map.Entry<Integer, String> subItem : unlocalizedNames.entrySet())
            {
                int entryId = subItem.getKey();

                itemStackList.add(new ItemStack(this, 1, entryId));
            }

        }
        else
        {

            itemStackList.add(new ItemStack(this, 1, 0));

        }
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        int index = stack.getItemDamage();
        String unlocalizedName = unlocalizedNames.get(index);
        if (unlocalizedName != null)
        {
            return "item." + unlocalizedName;
        }
        else
        {
            return getUnlocalizedName();
        }
    }
}
