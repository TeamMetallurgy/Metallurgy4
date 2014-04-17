package com.teammetallurgy.metallurgy.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import com.teammetallurgy.metallurgy.Metallurgy;
import com.teammetallurgy.metallurgy.lib.GUIIds;

public class ItemDrawer extends Item
{

    public ItemDrawer()
    {
        this.setCreativeTab(Metallurgy.instance.creativeTabItems);
        //TODO Change to actual texture when ready
        this.iconString = "metallurgy:metal_item_default";
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

    public static ItemStack getFirstStack(ItemStack itemStack)
    {
        NBTTagCompound compound = itemStack.getTagCompound();

        if (compound == null) { return null; }
        NBTTagList tagList = compound.getTagList("items", 10);

        if (tagList.tagCount() != 0) { return ItemStack.loadItemStackFromNBT((NBTTagCompound) tagList.getCompoundTagAt(0)); }
        return null;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean advanced)
    {
        NBTTagCompound compound = itemStack.getTagCompound();

        if (compound == null)
        {
            list.add("Items: 0 / 9");
            return;
        }

        NBTTagList tagList = compound.getTagList("items", 10);

        list.add(String.format("Stacks: %d / 9", tagList.tagCount()));

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
        {
            for (int i = 0; i < tagList.tagCount(); i++)
            {
                ItemStack stack = ItemStack.loadItemStackFromNBT((NBTTagCompound) tagList.getCompoundTagAt(i));
                if (stack != null)
                {
                    list.add(String.format("- %d %s", stack.stackSize, stack.getDisplayName()));
                }
            }
        }
    }

    public static ItemStack writeFirstStack(ItemStack itemStack, ItemStack firstStack)
    {

        NBTTagCompound compound = itemStack.getTagCompound();

        if (compound == null)
        {
            compound = new NBTTagCompound();
        }

        NBTTagList tagList = compound.getTagList("items", 10);

        NBTTagCompound firstItemTag = (NBTTagCompound) tagList.getCompoundTagAt(0);

        NBTTagCompound tagCompound = new NBTTagCompound();
        if (firstStack != null)
        {
            tagCompound.setByte("Slot", firstItemTag.getByte("Slot"));
            firstStack.writeToNBT(tagCompound);
        }

        NBTTagList list = new NBTTagList();

        if (!tagCompound.hasNoTags())
        {
            list.appendTag(tagCompound);
        }

        for (int i = 1; i < tagList.tagCount(); i++)
        {
            list.appendTag(tagList.getCompoundTagAt(i));
        }

        compound.setTag("items", list);

        itemStack.setTagCompound(compound);

        return itemStack;
    }
}
