package com.teammetallurgy.metallurgy;

import java.io.File;
import java.io.IOException;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import com.teammetallurgy.metallurgy.handlers.BucketsHandler;
import com.teammetallurgy.metallurgy.handlers.ConfigHandler;
import com.teammetallurgy.metallurgy.handlers.EventHandlerMetallurgy;
import com.teammetallurgy.metallurgy.handlers.GUIHandlerMetallurgy;
import com.teammetallurgy.metallurgy.integrations.Integration;
import com.teammetallurgy.metallurgy.lib.Configs;
import com.teammetallurgy.metallurgy.metals.MetalMaterials;
import com.teammetallurgy.metallurgy.metals.VanillaMetals;
import com.teammetallurgy.metallurgy.networking.CommonProxy;
import com.teammetallurgy.metallurgycore.CreativeTab;
import com.teammetallurgy.metallurgycore.handlers.LogHandler;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(name = Metallurgy.MODNAME, modid = Metallurgy.MODID, version = Metallurgy.VERSION, dependencies = Metallurgy.DEPS)
public class Metallurgy
{
    public static final String MODNAME = "Metallurgy";
    public static final String MODID = "Metallurgy";
    public static final String VERSION = "4.0.6";
    public static final String DEPS = "required-after:MetallurgyCore@[4.0.4,];before:UndergroundBiomes;after:Botania;after:TConstruct";

    @Mod.Instance(Metallurgy.MODID)
    public static Metallurgy instance;

    @SidedProxy(clientSide = "com.teammetallurgy.metallurgy.networking.ClientProxy", serverSide = "com.teammetallurgy.metallurgy.networking.CommonProxy")
    public static CommonProxy proxy;

    public CreativeTab creativeTabMachines = new CreativeTab(Metallurgy.MODID + ".Machines");
    public CreativeTab creativeTabBlocks = new CreativeTab(Metallurgy.MODID + ".Blocks");
    public CreativeTab creativeTabItems = new CreativeTab(Metallurgy.MODID + ".Items");
    public CreativeTab creativeTabTools = new CreativeTab(Metallurgy.MODID + ".Tools");
    public CreativeTab creativeTabArmor = new CreativeTab(Metallurgy.MODID + ".Armor");

    private File modsFolder;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        VanillaMetals.initRecipes();
        ItemList.addRecipes();
        BlockList.initRecipies();
        MetalMaterials.Instance.addRecipes();

        NetworkRegistry.INSTANCE.registerGuiHandler(Metallurgy.instance, new GUIHandlerMetallurgy());
        Metallurgy.proxy.registerTickHandlers();
        Metallurgy.proxy.registerBlockRenderers();
        Metallurgy.proxy.registerEntityRenderers();
        Metallurgy.proxy.registerFuelHandlers();
        MinecraftForge.EVENT_BUS.register(new EventHandlerMetallurgy());

        Integration.init(event);
    }

    private void initTabs()
    {
        creativeTabMachines.setItem(BlockList.getAlloyer());
        creativeTabBlocks.setItemStack(new ItemStack(BlockList.tabBlock));
        creativeTabItems.setItemStack(new ItemStack(ItemList.tabItem));
        creativeTabTools.setItemStack(new ItemStack(ItemList.tabItem,1,1));
        creativeTabArmor.setItemStack(new ItemStack(ItemList.tabItem,1,2));
    }

    public String modsPath()
    {
        try
        {
            return this.modsFolder.getCanonicalPath();
        }
        catch (IOException e)
        {
            return "";
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        Utils.injectOreDictionaryRecipes();
        Integration.postinit(event);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LogHandler.setLog(event.getModLog());
        ConfigHandler.setFile(event.getSuggestedConfigurationFile());

        Object value = ObfuscationReflectionHelper.getPrivateValue(Loader.class, Loader.instance(), "canonicalModsDir");

        if (value instanceof File)
        {
            this.modsFolder = (File) value;
        }

        Configs.init();
        BlockList.init();
        ItemList.init();
        BucketsHandler.instance.init();

        initTabs();

        Integration.preinit(event);
    }
}
