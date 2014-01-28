package com.teammetallurgy.metallurgy.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.teammetallurgy.metallurgy.Metallurgy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemOreFinder extends Item
{
    private ExecutorService exec = Executors.newCachedThreadPool();

    public ItemOreFinder(int id)
    {
        super(id);
        setMaxDamage(64);
        this.maxStackSize = 1;
        this.setCreativeTab(Metallurgy.instance.creativeTabItems);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float xOffset, float yOffset, float zOffset)
    {
        int mode = 0;
        if (stack.hasTagCompound())
        {
            mode = stack.getTagCompound().getInteger("mode");
        }
        else
        {
            stack.setTagCompound(new NBTTagCompound());
            stack.getTagCompound().setInteger("mode", mode);
        }

        if (player.isSneaking())
        {
            mode++;
            mode %= 6;

            stack.getTagCompound().setInteger("mode", mode);

            if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
            {
                player.addChatMessage("Radius: " + mode + " chunk" + (mode != 1 ? "s" : ""));
            }
            return false;
        }

        if (world.isRemote) { return false; }

        Chunk chunkFromBlockCoords = world.getChunkFromBlockCoords(x, z);

        Chunk minChunk = world.getChunkFromChunkCoords(chunkFromBlockCoords.xPosition - mode, chunkFromBlockCoords.zPosition - mode);
        Chunk maxChunk = world.getChunkFromChunkCoords(chunkFromBlockCoords.xPosition + mode, chunkFromBlockCoords.zPosition + mode);

        int minX = minChunk.xPosition << 4;
        int maxX = maxChunk.xPosition << 4;

        int minZ = minChunk.zPosition << 4;
        int maxZ = maxChunk.zPosition << 4;

        int minY = 0;
        int maxY = 128;

        try
        {
            if (mode == 0)
            {
                maxX += 16;
                maxZ += 16;
            }

            getOresInArea(world, player, minX, minY, minZ, maxX, maxY, maxZ);

        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public class OreGeneratorm implements Callable<Map<String, Integer>>
    {
        private final World world;
        private final int minX, minY, minZ;
        private final int maxX, maxY, maxZ;
        private EntityPlayer player;

        public OreGeneratorm(World world, EntityPlayer player, int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
        {
            this.world = world;
            this.player = player;

            this.minX = minX;
            this.minY = minY;
            this.minZ = minZ;

            this.maxX = maxX;
            this.maxY = maxY;
            this.maxZ = maxZ;

        }

        @Override
        public Map<String, Integer> call() throws Exception
        {
            Map<String, Integer> oreCount = new HashMap<String, Integer>();

            for (int y = this.minY; y <= this.maxY; y++)
            {
                for (int x = this.minX; x <= this.maxX; x++)
                {
                    for (int z = this.minZ; z <= this.maxZ; z++)
                    {
                        final int id = this.world.getBlockId(x, y, z);
                        final int meta = this.world.getBlockMetadata(x, y, z);
                        final ItemStack stack = new ItemStack(id, 1, meta);
                        String name = null;
                        final int oreID = OreDictionary.getOreID(stack);
                        if (oreID != -1)
                        {
                            name = OreDictionary.getOreName(oreID);
                        }
                        if (id == Block.oreIron.blockID)
                        {
                            name = "oreIron";
                        }
                        else if (id == Block.oreGold.blockID)
                        {
                            name = "oreGold";
                        }
                        else if (id == Block.oreDiamond.blockID)
                        {
                            name = "oreDiamond";
                        }
                        else if (id == Block.oreNetherQuartz.blockID)
                        {
                            name = "oreNetherQuartz";
                        }

                        if (name != null && name.contains("ore"))
                        {
                            if (oreCount.containsKey(name))
                            {
                                final int count = oreCount.get(name);
                                oreCount.put(name, count + 1);
                            }
                            else
                            {
                                oreCount.put(name, 1);
                            }
                        }

                    }
                }
            }

            final Set<String> names = oreCount.keySet();
            final String[] sort = names.toArray(new String[names.size()]);
            Arrays.sort(sort);
            this.player.addChatMessage("In Area (" + this.minX + ", " + this.minZ + ") to (" + this.maxX + ", " + this.maxZ + ")");
            for (final String name : sort)
            {
                final int amount = oreCount.get(name);
                this.player.addChatMessage("Found " + amount + " " + name);
            }

            return oreCount;
        }

    }

    public synchronized void getOresInArea(World world, EntityPlayer player, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) throws InterruptedException, ExecutionException
    {
        OreGeneratorm ore = new OreGeneratorm(world, player, minX, minY, minZ, maxX, maxY, maxZ);
        this.exec.submit(ore);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("Metallurgy:machines/OreFinder");
    }

}
