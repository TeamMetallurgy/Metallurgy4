package com.teammetallurgy.metallurgy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

import com.google.common.io.Resources;
import com.teammetallurgy.metallurgy.machines.alloyer.BlockAlloyer;
import com.teammetallurgy.metallurgy.machines.alloyer.TileEntityAlloyer;
import com.teammetallurgy.metallurgy.machines.crusher.BlockCrusher;
import com.teammetallurgy.metallurgy.machines.crusher.TileEntityCrusher;
import com.teammetallurgy.metallurgy.machines.forge.BlockForge;
import com.teammetallurgy.metallurgy.machines.forge.TileEntityForge;
import com.teammetallurgy.metallurgy.metals.MetalSet;
import com.teammetallurgy.metallurgycore.handlers.ConfigHandler;

import cpw.mods.fml.common.registry.GameRegistry;

public class BlockList
{
    private static Block crusher;
    private static Block alloyer;
    private static Block forge;

    private static Map<String, MetalSet> setList = new HashMap<String, MetalSet>();

    public static void init()
    {
        String blockName = "crusher";

        crusher = new BlockCrusher(getId(blockName)).setUnlocalizedName(blockName);

        registerBlockWithTileEntity(crusher, TileEntityCrusher.class, blockName);

        blockName = "alloyer";

        alloyer = new BlockAlloyer(getId(blockName)).setUnlocalizedName(blockName);

        registerBlockWithTileEntity(alloyer, TileEntityAlloyer.class, blockName);

        blockName = "forge";

        forge = new BlockForge(getId(blockName)).setUnlocalizedName(blockName);

        registerBlockWithTileEntity(forge, TileEntityForge.class, blockName);

        File directory = new File(Metallurgy.instance.modsPath());

        File[] listFiles = directory.listFiles();

        ArrayList<String> zipDirs = new ArrayList<String>();

        for (File file : listFiles)
        {
            if (file.getName().contains(".zip") && file.getName().startsWith("Metallurgy-Addon-"))
            {
                try
                {
                    zipDirs.add(file.getCanonicalPath());
                }
                catch (IOException e)
                {
                    // Don't add errored file
                }
                break;
            }
        }

        for (String zipDir : zipDirs)
        {
            try
            {
                readPackZip(zipDir);
            }
            catch (IOException e)
            {
                e.printStackTrace();

            }
        }

        String[] sets = { "base", "ender", "fantasy", "nether", "precious", "utility" };

        for (String set : sets)
        {

            String path = "assets/metallurgy/data/";

            URL resource = Resources.getResource(path + set + ".json");

            try
            {
                injectMetalSet(set, resource.openStream());
            }
            catch (IOException e)
            {
            }

        }

    }

    private static void readPackZip(String zipDir) throws IOException
    {
        ZipFile zipFile = new ZipFile(zipDir);

        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        while (entries.hasMoreElements())
        {
            ZipEntry nextElement = entries.nextElement();

            String name = nextElement.getName();
            if (!nextElement.isDirectory() && name.contains(".json"))
            {
                Metallurgy.proxy.injectZipAsResource(zipDir);
                InputStream stream = zipFile.getInputStream(nextElement);

                injectMetalSet(name.substring(name.lastIndexOf("/") + 1, name.lastIndexOf(".")), stream);

            }
        }

        zipFile.close();
    }

    private static void injectMetalSet(String name, InputStream stream) throws IOException
    {
        MetalSet metalSet = new MetalSet(name);

        metalSet.load(stream);

        setList.put(name, metalSet);

    }

    private static int getId(String blockName)
    {
        int defaultId = 450;
        return ConfigHandler.getBlock("Machines", blockName, defaultId++);
    }

    private static void registerBlockWithTileEntity(Block block, Class<? extends TileEntity> teClass, String blockName)
    {
        registerBlock(block, blockName);
        registerTileEntity(teClass, blockName);
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
