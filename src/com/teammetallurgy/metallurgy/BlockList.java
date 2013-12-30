package com.teammetallurgy.metallurgy;

import com.teammetallurgy.metallurgy.handlers.ConfigHandler;
import com.teammetallurgy.metallurgy.machines.crusher.BlockCrusher;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class BlockList
{
    private static Block crusher;

    public static void init()
    {
        String blockName = "crusher";
        int id = ConfigHandler.getBlock(blockName);

        crusher = new BlockCrusher(id).setUnlocalizedName(blockName);

        registerBlock(crusher, blockName);
    }

    private static void registerBlock(Block block, String name)
    {
        GameRegistry.registerBlock(block, Metallurgy.MODID + ":" + name);
    }

}
