package com.teammetallurgy.metallurgy.tnt;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockExplosiveItem extends ItemBlock
{

    public BlockExplosiveItem(Block block)
    {
        super(block);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setUnlocalizedName("metallurgy.explosive");
    }

    @Override
    public int getMetadata(int meta)
    {
        return meta;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        Block block = this.field_150939_a;

        if (block != null)
        {
            return ((BlockExplosive) block).getUnlocalizedName(stack.getItemDamage());
        }
        else
        {
            return this.getUnlocalizedName();
        }
    }
}
