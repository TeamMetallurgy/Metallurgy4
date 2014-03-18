package com.teammetallurgy.metallurgy;

import java.io.File;
import java.io.IOException;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;

import com.teammetallurgy.metallurgy.handlers.EventHandler;
import com.teammetallurgy.metallurgy.handlers.GUIHandlerMetallurgy;
import com.teammetallurgy.metallurgy.networking.CommonProxy;
import com.teammetallurgy.metallurgycore.CreativeTab;
import com.teammetallurgy.metallurgycore.handlers.ConfigHandler;
import com.teammetallurgy.metallurgycore.handlers.LogHandler;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(name = Metallurgy.MODNAME, modid = Metallurgy.MODID)
public class Metallurgy
{
    public static final String MODNAME = "Metallurgy";
    public static final String MODID = "Metallurgy";

    @Mod.Instance(Metallurgy.MODID)
    public static Metallurgy instance;

    @SidedProxy(clientSide = "com.teammetallurgy.metallurgy.networking.ClientProxy", serverSide = "com.teammetallurgy.metallurgy.networking.CommonProxy")
    public static CommonProxy proxy;

    public CreativeTabs creativeTabMachines = new CreativeTab(Metallurgy.MODID + ".Machines");
    public CreativeTabs creativeTabBlocks = new CreativeTab(Metallurgy.MODID + ".Blocks");
    public CreativeTabs creativeTabItems = new CreativeTab(Metallurgy.MODID + ".Items");
    public CreativeTabs creativeTabTools = new CreativeTab(Metallurgy.MODID + ".Tools");
    public CreativeTabs creativeTabArmor = new CreativeTab(Metallurgy.MODID + ".Armor");

    private File modsFolder;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(Metallurgy.instance, new GUIHandlerMetallurgy());
        Metallurgy.proxy.registerTickHandlers();
        MinecraftForge.EVENT_BUS.register(new EventHandler());

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

        BlockList.init();
        ItemList.init();
    }
}
