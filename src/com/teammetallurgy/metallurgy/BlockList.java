package com.teammetallurgy.metallurgy;

import com.teammetallurgy.metallurgy.handlers.ConfigHandler;
import com.teammetallurgy.metallurgy.machines.crusher.BlockCrusher;
import com.teammetallurgy.metallurgy.machines.crusher.TileEntityCrusher;
import com.teammetallurgy.metallurgy.metals.MetalSet;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class BlockList
{
    private static Block crusher;

    public static void init()
    {
        String blockName = "crusher";
        int id = ConfigHandler.getBlock(blockName,450);

        crusher = new BlockCrusher(id).setUnlocalizedName(blockName);

        registerBlock(crusher, blockName);
        registerTileEntity(TileEntityCrusher.class, blockName);
        
        MetalSet base = new MetalSet("base");
        base.load();
    }

    private static void registerTileEntity(Class<TileEntityCrusher> clazz, String blockName)
    {
        GameRegistry.registerTileEntity(clazz, Metallurgy.MODID + ":" + blockName);
    }

    private static void registerBlock(Block block, String name)
    {
        GameRegistry.registerBlock(block, Metallurgy.MODID + ":" + name);
    }

}
