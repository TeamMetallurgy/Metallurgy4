package com.teammetallurgy.metallurgy.metals;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemMetalBlock extends ItemBlock
{

    @Deprecated
    public ItemMetalBlock(int id)
    {
        this(Block.getBlockById(id));
    }

    public ItemMetalBlock(Block block)
    {
        super(block);
        this.setHasSubtypes(true);
        this.setUnlocalizedName("metallurgy.metal.block." + Block.getIdFromBlock(block));
    }

    @Override
    public int getMetadata(int meta)
    {
        return meta;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        Block block = this.field_150939_a;

        if (block != null)
        {
            return ((MetalBlock) block).getUnlocalizedName(itemStack.getItemDamage());
        }
        else
        {
            return this.getUnlocalizedName();
        }

    }

}
