package com.teammetallurgy.metallurgy.world;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import com.teammetallurgy.metallurgycore.handlers.ConfigHandler;

import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenMetals implements IWorldGenerator
{
    int genBlockId;
    int genMetaId;
    /**
     * 0: Veins Pre Chunk, 1: ores Pre Chunk, 2: minLvl, 3:maxLvl, <br />
     * 4: Vein Chance PreChunk, 5: Vine Density
     */
    private int[] generation;
    private String dimensions;
    private WorldGenMinable mineable;

    private static ArrayList<WorldGenMetals> generators = new ArrayList<WorldGenMetals>();

    public static void generateAll(Random fmlRandom, int chunkXPos, int chunkZPos, WorldServer world, boolean b)
    {
        synchronized (WorldGenMetals.generators)
        {
            for (WorldGenMetals gen : WorldGenMetals.generators)
            {
                gen.generate(fmlRandom, chunkXPos, chunkZPos, world, b);
            }
        }
    }

    public WorldGenMetals(int blockId, int metaId, int[] generationInfo, String dimensionsInfo)
    {

        this.genBlockId = blockId;
        this.genMetaId = metaId;

        this.generation = generationInfo;
        this.dimensions = dimensionsInfo;

        int targetId = Block.stone.blockID;

        if (this.vaildDimension(-1))
        {
            targetId = Block.netherrack.blockID;
        }

        if (this.vaildDimension(1))
        {
            targetId = Block.whiteStone.blockID;
        }

        this.mineable = new WorldGenMinable(blockId, metaId, this.generation[1], targetId);
    }

    public void generate(Random random, int chunkX, int chunkZ, World world, boolean b)
    {
        if (b || ConfigHandler.regen())
        {

            Random chunkRand = this.getRandom(random, chunkX, chunkZ);

            int dimId = world.provider.dimensionId;

            if (!this.vaildDimension(dimId)) { return; }

            if (chunkRand.nextInt(100) > this.generation[4]) { return; }

            for (int i = 0; i <= this.generation[0]; i++)
            {
                int initX = chunkX * 16 + chunkRand.nextInt(16);
                int initY = this.generation[2] + chunkRand.nextInt(this.generation[3] - this.generation[2]);
                int initZ = chunkZ * 16 + chunkRand.nextInt(16);

                this.mineable.generate(world, chunkRand, initX, initY, initZ);
            }
        }
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        WorldGenMetals.generators.add(this);
        this.generate(random, chunkX, chunkZ, world, true);
    }

    private Random getRandom(Random fmlRandom, int chunkX, int chunkZ)
    {
        // Create our own random chunk seed, to get different random numbers
        // than other mods.
        long seed = fmlRandom.nextLong();
        // Get a different seed for each ore.
        long seedBlock = this.genBlockId * this.genMetaId ^ fmlRandom.nextLong();
        seed = chunkX * seed * chunkZ * seed * seedBlock ^ fmlRandom.nextInt(Integer.MAX_VALUE);

        return new Random(seed);
    }

    private boolean vaildDimension(int dimensionId)
    {

        boolean vaild = false;
        String[] dims = this.dimensions.split(" ");

        if (dims.length < 1) { return false; }

        for (String dim : dims)
        {
            if (dim.contains("-"))
            {
                String[] range = dim.split("-");

                if (range.length > 1)
                {

                    if (!(range[0].compareTo("") == 0))
                    {
                        int low = Integer.parseInt(range[0]);
                        int high = Integer.parseInt(range[1]);

                        if (dimensionId >= low && dimensionId <= high)
                        {
                            vaild = true;
                            break;
                        }
                    }
                    else
                    {
                        int canidateDim = -1 * Integer.parseInt(range[1]);

                        if (canidateDim == dimensionId)
                        {
                            vaild = true;
                            break;
                        }
                    }

                }
            }
            else
            {
                int canidateDim = Integer.parseInt(dim);

                if (canidateDim == dimensionId)
                {
                    vaild = true;
                    break;
                }
            }
        }

        return vaild;
    }

}
