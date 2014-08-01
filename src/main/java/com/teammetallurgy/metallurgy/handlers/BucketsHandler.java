package com.teammetallurgy.metallurgy.handlers;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.teammetallurgy.metallurgy.Metallurgy;
import com.teammetallurgy.metallurgy.metals.ItemMoltenMetalBucket;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.registry.GameRegistry;

public class BucketsHandler
{
    public static BucketsHandler instance = new BucketsHandler();
    private HashMap<ItemStack, Block> bucketBlocks = new HashMap<ItemStack, Block>();
    private HashMap<Block, ItemStack> blockBuckets = new HashMap<Block, ItemStack>();
    private static ItemMoltenMetalBucket bucket;

    public void fillBucket(FillBucketEvent event)
    {
        World world = event.world;
        MovingObjectPosition pos = event.target;

        Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);

        ItemStack bucketStack = blockBuckets.get(block);

        if (bucketStack == null) { return; }

        world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);

        event.result = bucketStack;
        event.setResult(Event.Result.ALLOW);
    }

    public Block getFilledBlock(ItemStack filledBucket)
    {
        Block block = Blocks.air;

        for (Entry<ItemStack, Block> entry : bucketBlocks.entrySet())
        {
            if (entry.getKey().isItemEqual(filledBucket))
            {
                block = entry.getValue();
            }
        }

        return block;
    }

    public void addMoltenMapping(ItemStack stack, Block fluidBlock)
    {
        bucketBlocks.put(stack, fluidBlock);
        blockBuckets.put(fluidBlock, stack);
    }

    public void addBucketMapping(int id, String unlocalizedName, String texture)
    {
        bucket.addMaping(id, "metallurgy.bucket.filled." + unlocalizedName, Metallurgy.MODID.toLowerCase() + ":" + texture);
    }

    public void init()
    {
        bucket = new ItemMoltenMetalBucket();
        GameRegistry.registerItem(bucket, "molten.bucket");

        int bucketVol = FluidContainerRegistry.BUCKET_VOLUME;
        ItemStack emptyBucketStack = new ItemStack(Items.bucket);

        TreeMap<String, String[]> materialSets = new TreeMap<String, String[]>();

        String[] baseMaterials = { "angmallen", "bronze", "copper", "damascus steel", "hepatizon", "manganese", "steel", "tin" };
        String[] enderMaterials = { "desichalkos", "eximite", "meutoite" };
        String[] fantasyMaterials = { "adamantine", "astral silver", "atlarus", "black steel", "carmot", "celenegil", "deep iron", "haderoth", "infuscolium", "mithril", "orichalcum", "oureclase",
                "prometheum", "quicksilver", "rubracium", "tartarite" };
        String[] netherMaterials = { "alduorite", "amordrine", "ceruclase", "ignatius", "inolashite", "kalendrite", "lemurite", "midasium", "sanguinite", "shadow iron", "shadow steel", "vulcanite",
                "vyroxeres" };
        String[] preciousMaterials = { "brass", "electrum", "platinum", "silver", "zinc" };

        materialSets.put("base", baseMaterials);
        materialSets.put("ender", enderMaterials);
        materialSets.put("fantasy", fantasyMaterials);
        materialSets.put("nether", netherMaterials);
        materialSets.put("precious", preciousMaterials);
        int bucketId = 0;

        for (Entry<String, String[]> entrySet : materialSets.entrySet())
        {
            String set = entrySet.getKey();
            String[] materials = entrySet.getValue();

            for (int i = 0; i < materials.length; i++)
            {
                String unlocalizedName = materials[i].trim().replace(" ", ".").toLowerCase();
                unlocalizedName += ".molten";

                String texture = set.trim().replace(" ", "_").toLowerCase() + "/";
                texture += materials[i].trim().replace(" ", "_").toLowerCase();
                texture += "_bucket_fill";

                FluidStack fluidStack = FluidRegistry.getFluidStack(unlocalizedName, bucketVol);

                if (fluidStack != null)
                {

                    bucketId += 1;

                    addBucketMapping(bucketId, unlocalizedName, texture);

                    ItemStack bucketStack = new ItemStack(bucket, 1, bucketId);

                    addMoltenMapping(bucketStack, fluidStack.getFluid().getBlock());

                    FluidContainerRegistry.registerFluidContainer(fluidStack, bucketStack, emptyBucketStack);
                }
            }
        }

    }
}
