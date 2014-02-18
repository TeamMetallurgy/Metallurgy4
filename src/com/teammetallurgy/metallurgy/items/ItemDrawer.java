package com.teammetallurgy.metallurgy.items;

import com.teammetallurgy.metallurgy.Metallurgy;
import com.teammetallurgy.metallurgy.lib.GUIIds;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDrawer extends Item
{

    public ItemDrawer(int id)
    {
        super(id);
        this.setCreativeTab(Metallurgy.instance.creativeTabItems);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            player.openGui(Metallurgy.instance, GUIIds.DRAWER, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }

        return itemStack;
    }
}
