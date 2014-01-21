package com.teammetallurgy.metallurgy;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

import com.teammetallurgy.metallurgy.handlers.ConfigHandler;
import com.teammetallurgy.metallurgy.machines.alloyer.BlockAlloyer;
import com.teammetallurgy.metallurgy.machines.alloyer.TileEntityAlloyer;
import com.teammetallurgy.metallurgy.machines.crusher.BlockCrusher;
import com.teammetallurgy.metallurgy.machines.crusher.TileEntityCrusher;
import com.teammetallurgy.metallurgy.metals.MetalSet;

import cpw.mods.fml.common.registry.GameRegistry;

public class BlockList
{
    private static Block crusher;
    private static Block alloyer;

    private static Map<String, MetalSet> setList = new HashMap<String, MetalSet>();

    public static void init()
    {
        String blockName = "crusher";
        int defaultId = 450;
        int id = ConfigHandler.getBlock(blockName, defaultId++);

        crusher = new BlockCrusher(id).setUnlocalizedName(blockName);

        registerBlock(crusher, blockName);
        registerTileEntity(TileEntityCrusher.class, blockName);

        blockName = "alloyer";
        id = ConfigHandler.getBlock(blockName, defaultId++);

        alloyer = new BlockAlloyer(id).setUnlocalizedName(blockName);

        registerBlock(alloyer, blockName);
        registerTileEntity(TileEntityAlloyer.class, blockName);

        String[] sets = { "base", "ender", "fantasy", "nether", "precious", "utility" };

        for (int i = 0; i < sets.length; i++)
        {
            if (ConfigHandler.setEnabled(sets[i]))
            {
                MetalSet base = new MetalSet(sets[i]);
                base.load();
                setList.put(sets[i], base);
            }
        }
    }

    public static MetalSet getSet(String name)
    {
        return setList.get(name);
    }

    private static void registerTileEntity(Class<? extends TileEntity> clazz, String blockName)
    {
        GameRegistry.registerTileEntity(clazz, Metallurgy.MODID + ":" + blockName);
    }

    private static void registerBlock(Block block, String name)
    {
        GameRegistry.registerBlock(block, Metallurgy.MODID + ":" + name);
    }

}
