package com.teammetallurgy.metallurgy.machines;

import net.minecraft.creativetab.CreativeTabs;

import com.teammetallurgy.metallurgy.Metallurgy;
import com.teammetallurgy.metallurgycore.machines.BlockMetallurgyCore;

public abstract class BlockMetallurgy extends BlockMetallurgyCore
{
    
    public BlockMetallurgy()
    {
        this.textureName = "metallurgy:metal_block_default";
        
    }

    @Override
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return Metallurgy.instance.creativeTabMachines;
    }
    
    @Override
    public int getRenderType()
    {
        return RenderBlockMachine.renderId;
    }
}
