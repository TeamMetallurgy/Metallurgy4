package com.teammetallurgy.metallurgy;

import net.minecraft.creativetab.CreativeTabs;

import com.teammetallurgy.metallurgy.handlers.ConfigHandler;
import com.teammetallurgy.metallurgy.handlers.GUIHandler;
import com.teammetallurgy.metallurgy.handlers.LogHandler;
import com.teammetallurgy.metallurgy.handlers.PacketHandler;

import cpw.mods.fml.common.Mod;
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

    public CreativeTabs creativeTabMachines = new CreativeTabs(MODID + ".Machines");
    public CreativeTabs creativeTabBlocks = new CreativeTabs(MODID + ".Blocks");
    public CreativeTabs creativeTabItems = new CreativeTabs(MODID + ".Items");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LogHandler.setLog(event.getModLog());
        ConfigHandler.setFile(event.getSuggestedConfigurationFile());

        BlockList.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        NetworkRegistry.instance().registerGuiHandler(instance, new GUIHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
