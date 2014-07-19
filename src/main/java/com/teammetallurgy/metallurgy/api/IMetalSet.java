package com.teammetallurgy.metallurgy.api;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public interface IMetalSet
{
    public ItemStack getAxe(String metal);

    public ItemStack getBlock(String metal);

    public ItemStack getBoots(String metal);

    public ItemStack getBrick(String metal);

    public ItemStack getChestplate(String metal);

    public Block getDefaultOre();

    public Block getDefaultBlock();

    public Block getDefaultBricks();

    public ItemStack getDrop(String metal);

    public ItemStack getDust(String metal);

    public ItemStack getHelmet(String metal);

    public ItemStack getHoe(String metal);

    public ItemStack getIngot(String metal);

    public ItemStack getLeggings(String metal);

    public IMetalInfo getMetal(String metal);

    public String[] getMetalNames();

    public ItemStack getOre(String metal);

    public ItemStack getPickaxe(String metal);

    public ItemStack getShovel(String metal);

    public ItemStack getSword(String metal);

}
