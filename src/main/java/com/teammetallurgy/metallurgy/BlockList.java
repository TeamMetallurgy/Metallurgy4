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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.teammetallurgy.metallurgy.api.IMetalSet;
import com.teammetallurgy.metallurgy.handlers.ConfigHandler;
import com.teammetallurgy.metallurgy.machines.abstractor.BlockAbstrator;
import com.teammetallurgy.metallurgy.machines.abstractor.TileEntityAbstractor;
import com.teammetallurgy.metallurgy.machines.alloyer.BlockAlloyer;
import com.teammetallurgy.metallurgy.machines.alloyer.TileEntityAlloyer;
import com.teammetallurgy.metallurgy.machines.closet.BlockCloset;
import com.teammetallurgy.metallurgy.machines.closet.TileEntityCloset;
import com.teammetallurgy.metallurgy.machines.crusher.BlockCrusher;
import com.teammetallurgy.metallurgy.machines.crusher.TileEntityCrusher;
import com.teammetallurgy.metallurgy.machines.forge.BlockForge;
import com.teammetallurgy.metallurgy.machines.forge.TileEntityForge;
import com.teammetallurgy.metallurgy.machines.frame.BlockFrame;
import com.teammetallurgy.metallurgy.metals.ItemMetalBlock;
import com.teammetallurgy.metallurgy.metals.MetalBlock;
import com.teammetallurgy.metallurgy.metals.MetalSet;
import com.teammetallurgy.metallurgy.metals.VanillaMetals;
import com.teammetallurgy.metallurgy.tnt.BlockExplosive;
import com.teammetallurgy.metallurgy.tnt.BlockExplosiveItem;
import com.teammetallurgy.metallurgy.tnt.EntityExplosive;
import com.teammetallurgy.metallurgycore.handlers.LogHandler;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockList
{
    private static Block machineFrame;
    private static Block crusher;
    private static Block alloyer;
    private static Block forge;
    private static MetalBlock extraSorageBlock;
    private static BlockExplosive explosive;
    private static Block abstractor;
    private static Block closet; 
    public static MetalBlock tabBlock;

    private static Map<String, MetalSet> setList = new HashMap<String, MetalSet>();
    private static String[] setNames = { "base", "ender", "fantasy", "nether", "precious", "utility" };

    public static String[] getDefaultSetNames()
    {
        return setNames;
    }

    public static String[] getLoadedSetNames()
    {
        String[] setNames = new String[setList.size()];
        setNames = setList.keySet().toArray(setNames);
        return setNames;
    }

    public static IMetalSet getSet(String name)
    {
        return (IMetalSet) BlockList.setList.get(name);
    }

    public static void init()
    {
        String blockName = "machine.frame";
        BlockList.machineFrame = new BlockFrame().setBlockName(blockName);
        BlockList.registerBlock(BlockList.machineFrame, blockName);

        blockName = "crusher";
        BlockList.crusher = new BlockCrusher().setBlockName(blockName);
        BlockList.registerBlockWithTileEntity(BlockList.crusher, TileEntityCrusher.class, blockName);

        blockName = "alloyer";
        BlockList.alloyer = new BlockAlloyer().setBlockName(blockName);
        BlockList.registerBlockWithTileEntity(BlockList.alloyer, TileEntityAlloyer.class, blockName);

        blockName = "forge";
        BlockList.forge = new BlockForge().setBlockName(blockName);
        BlockList.registerBlockWithTileEntity(BlockList.forge, TileEntityForge.class, blockName);

        blockName = "abstractor";
        BlockList.abstractor = new BlockAbstrator().setBlockName(blockName);
        BlockList.registerBlockWithTileEntity(BlockList.abstractor, TileEntityAbstractor.class, blockName);

        blockName = "closet";
        BlockList.closet = new BlockCloset().setBlockName(blockName);
        BlockList.registerBlockWithTileEntity(BlockList.closet, TileEntityCloset.class, blockName);
        
        initMetalSets();

        VanillaMetals.initBlocks();

        BlockList.extraSorageBlock = new MetalBlock("extra.storage.block");
        BlockList.extraSorageBlock.addSubBlock(0, "charcoal", 1, "metallurgy:misc/charcoal_block");
        BlockList.extraSorageBlock.setHarvestLevel("pickaxe", 0, 0);
        GameRegistry.registerBlock(BlockList.extraSorageBlock, ItemMetalBlock.class, "extra.storage.block");

        BlockList.explosive = new BlockExplosive();
        GameRegistry.registerBlock(BlockList.explosive, BlockExplosiveItem.class, "explosive");
        // Explosive entity
        EntityRegistry.registerModEntity(EntityExplosive.class, "explosiveEntity", 0, Metallurgy.instance, 64, 10, true);
        
        BlockList.tabBlock = new MetalBlock("tab.block");
        BlockList.tabBlock.addSubBlock(0, "tab2", 1, "metallurgy:ender/eximite_block");
        BlockList.tabBlock.setCreativeTab(null);
        GameRegistry.registerBlock(BlockList.tabBlock, ItemMetalBlock.class, "tab.block");

    }

    private static void initMetalSets()
    {
        File directory = new File(Metallurgy.instance.modsPath());

        File[] listFiles = directory.listFiles();

        ArrayList<String> zipDirs = new ArrayList<String>();

        if (listFiles != null)
        {
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
        }

        for (String zipDir : zipDirs)
        {
            try
            {
                BlockList.readPackZip(zipDir);
            }
            catch (IOException e)
            {
                e.printStackTrace();

            }
        }

        for (String set : setNames)
        {

            String path = "assets/metallurgy/data/";

            URL resource = Block.class.getClassLoader().getResource(path + set + ".json");
            boolean enabledSet = ConfigHandler.setEnabled(set);

            try
            {
                if (resource != null && enabledSet)
                {
                    BlockList.injectMetalSet(set, resource.openStream());
                }
                else
                {
                    LogHandler.log("Could not load '" + path + "'");
                }
            }
            catch (IOException e)
            {
            }
        }
    }

    public static void initRecipies()
    {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockList.machineFrame), "ici", "cic", "ici", 'c', "ingotCopper", 'i', Items.iron_ingot));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockList.crusher), "ccc", "imi", "ifi", 'c', "ingotCopper", 'i', Items.iron_ingot, 'm', new ItemStack(BlockList.machineFrame), 'f',
                Blocks.furnace));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockList.alloyer), "aaa", "dmd", "dfd", 'a', "ingotAngmallen", 'd', "ingotDamascusSteel", 'm', new ItemStack(BlockList.machineFrame),
                'f', Blocks.furnace));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockList.forge), "iii", "sms", "sfs", 'i', "ingotIgnatius", 's', "ingotShadowSteel", 'm', new ItemStack(BlockList.machineFrame), 'f',
                Blocks.furnace));

        ItemStack charcoalBlock = new ItemStack(BlockList.extraSorageBlock, 1, 0);
        ItemStack charcoalStack = new ItemStack(Items.coal, 1, 1);
        ItemStack charcoalResult = new ItemStack(Items.coal, 9, 1);
        GameRegistry.addRecipe(charcoalBlock.copy(), "ccc", "ccc", "ccc", 'c', charcoalStack);
        GameRegistry.addShapelessRecipe(charcoalResult, charcoalBlock.copy());
        OreDictionary.registerOre("blockCharcoal", charcoalBlock.copy());
    }

    private static void injectMetalSet(String name, InputStream stream) throws IOException
    {
        MetalSet metalSet = new MetalSet(name);

        metalSet.load(stream);

        BlockList.setList.put(name, metalSet);

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

                BlockList.injectMetalSet(name.substring(name.lastIndexOf("/") + 1, name.lastIndexOf(".")), stream);

            }
        }

        zipFile.close();
    }

    private static void registerBlock(Block block, String name)
    {
        GameRegistry.registerBlock(block, name);
    }

    private static void registerBlockWithTileEntity(Block block, Class<? extends TileEntity> teClass, String blockName)
    {
        BlockList.registerBlock(block, blockName);
        BlockList.registerTileEntity(teClass, blockName);
    }

    private static void registerTileEntity(Class<? extends TileEntity> clazz, String blockName)
    {
        GameRegistry.registerTileEntity(clazz, Metallurgy.MODID + ":" + blockName);
    }

    public static Block getAlloyer()
    {
        return alloyer;
    }

    public static Block getMachineFrame()
    {
        return machineFrame;
    }

    public static Block getCrusher()
    {
        return crusher;
    }

    public static Block getForge()
    {
        return forge;
    }

    public static Block getAbstractor()
    {
        return abstractor;
    }
    
    public static Block getCloset()
    {
        return closet;
    }

    public static Block getExtraStorageBlock()
    {
        return extraSorageBlock;
    }

    public static Block getExplosive()
    {
        return explosive;
    }

}
