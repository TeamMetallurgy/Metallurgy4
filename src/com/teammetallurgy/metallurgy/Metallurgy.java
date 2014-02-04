package com.teammetallurgy.metallurgy;

import java.io.File;
import java.io.IOException;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;

import com.teammetallurgy.metallurgy.handlers.ConfigHandler;
import com.teammetallurgy.metallurgy.handlers.EventHandler;
import com.teammetallurgy.metallurgy.handlers.GUIHandler;
import com.teammetallurgy.metallurgy.handlers.LogHandler;
import com.teammetallurgy.metallurgy.handlers.PacketHandler;
import com.teammetallurgy.metallurgy.networking.CommonProxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(name = Metallurgy.MODNAME, modid = Metallurgy.MODID)
@NetworkMod(channels = { Metallurgy.MODID }, packetHandler = PacketHandler.class)
public class Metallurgy
{
    public static final String MODNAME = "Metallurgy";
    public static final String MODID = "Metallurgy";

    @Mod.Instance(MODID)
    public static Metallurgy instance;

    @SidedProxy(clientSide = "com.teammetallurgy.metallurgy.networking.ClientProxy", serverSide = "com.teammetallurgy.metallurgy.networking.CommonProxy")
    public static CommonProxy proxy;

    public CreativeTabs creativeTabMachines = new CreativeTabs(MODID + ".Machines");
    public CreativeTabs creativeTabBlocks = new CreativeTabs(MODID + ".Blocks");
    public CreativeTabs creativeTabItems = new CreativeTabs(MODID + ".Items");

    private File modsFolder;

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

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        NetworkRegistry.instance().registerGuiHandler(instance, new GUIHandler());
        proxy.registerTickHandlers();
        MinecraftForge.EVENT_BUS.register(new EventHandler());

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        Utils.injectOreDictionaryRecipes();
    }
}
