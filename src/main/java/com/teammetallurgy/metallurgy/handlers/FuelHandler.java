package com.teammetallurgy.metallurgy.handlers;

import com.teammetallurgy.metallurgy.BlockList;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler
{

    @Override
    public int getBurnTime(ItemStack fuel)
    {

        ItemStack charcoalBlock = new ItemStack(BlockList.getExtraStorageBlock(), 1, 0);
        if (fuel.isItemEqual(charcoalBlock)) 
        {
            return 16000; 
        }

        return 0;
    }

}
