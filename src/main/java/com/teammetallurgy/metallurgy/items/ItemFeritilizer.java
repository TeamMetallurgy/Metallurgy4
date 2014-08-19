package com.teammetallurgy.metallurgy.items;

import com.teammetallurgy.metallurgy.Metallurgy;

import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class ItemFeritilizer extends Item
{
    public ItemFeritilizer()
    {
        this.setTextureName("metallurgy:misc/fertilizer");
        this.setUnlocalizedName("metallurgy.fertilizer");
        this.setMaxStackSize(64);
        this.setCreativeTab(Metallurgy.instance.creativeTabItems);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int xPos, int yPos, int zPos, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        Block block = world.getBlock(xPos, yPos, zPos);

        BonemealEvent event = new BonemealEvent(player, world, block, xPos, yPos, zPos);
        if (MinecraftForge.EVENT_BUS.post(event)) { return false; }

        if (event.getResult() == Result.ALLOW)
        {
            if (!world.isRemote)
            {
                stack.stackSize--;
            }
            return true;
        }

        if (block instanceof IGrowable)
        {
            IGrowable igrowable = (IGrowable) block;

            if (igrowable.func_149851_a(world, xPos, yPos, zPos, world.isRemote))
            {
                if (!world.isRemote)
                {
                    if (igrowable.func_149852_a(world, world.rand, xPos, yPos, zPos))
                    {
                        igrowable.func_149853_b(world, world.rand, xPos, yPos, zPos);
                    }

                    --stack.stackSize;
                    world.playAuxSFX(2005, xPos, yPos, zPos, 0);
                }

                return true;
            }
        }

        return false;

    }

}
