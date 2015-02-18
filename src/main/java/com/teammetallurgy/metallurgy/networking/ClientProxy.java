package com.teammetallurgy.metallurgy.networking;

import java.io.File;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FileResourcePack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemReed;
import net.minecraftforge.client.MinecraftForgeClient;

import com.teammetallurgy.metallurgy.BlockList;
import com.teammetallurgy.metallurgy.machines.RenderBlockMachine;
import com.teammetallurgy.metallurgy.machines.abstractor.ItemRendererAbstractor;
import com.teammetallurgy.metallurgy.machines.abstractor.RendererAbstractor;
import com.teammetallurgy.metallurgy.machines.abstractor.TileEntityAbstractor;
import com.teammetallurgy.metallurgy.machines.closet.ItemRendererCloset;
import com.teammetallurgy.metallurgy.machines.closet.RendererCloset;
import com.teammetallurgy.metallurgy.machines.closet.TileEntityCloset;
import com.teammetallurgy.metallurgy.tnt.EntityExplosive;
import com.teammetallurgy.metallurgy.tnt.RenderExplosive;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class ClientProxy extends CommonProxy
{

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void injectZipAsResource(String zipDir)
    {

        Object value = ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, FMLClientHandler.instance().getClient(), "defaultResourcePacks");

        if (value instanceof List)
        {
            FileResourcePack pack = new FileResourcePack(new File(zipDir));

            ((List) value).add(pack);
        }
        FMLClientHandler.instance().getClient().refreshResources();
    }

    @Override
    public void registerBlockRenderers()
    {
        RenderingRegistry.registerBlockHandler(new RenderBlockMachine());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAbstractor.class, new RendererAbstractor());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockList.getAbstractor()), new ItemRendererAbstractor());
        
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCloset.class, new RendererCloset());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockList.getCloset()), new ItemRendererCloset());
    }

    @Override
    public void registerEntityRenderers()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityExplosive.class, new RenderExplosive());
    }
}
