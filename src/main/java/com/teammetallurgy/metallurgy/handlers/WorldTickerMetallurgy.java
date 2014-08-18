package com.teammetallurgy.metallurgy.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.WorldEvent;

import com.teammetallurgy.metallurgy.world.WorldGenMetals;
import com.teammetallurgy.metallurgycore.handlers.ChunkLoc;
import com.teammetallurgy.metallurgycore.handlers.LogHandler;
import com.teammetallurgy.metallurgycore.handlers.WorldTicker;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WorldTickerMetallurgy extends WorldTicker
{
    public static HashMap<Integer, ArrayList<ChunkLoc>> chunksToGenerate = new HashMap<Integer, ArrayList<ChunkLoc>>();

    @SubscribeEvent
    @Override
    public void worldRetroGen(WorldEvent.Load event)
    {
        if (!(event.world instanceof WorldServer))
        {
            return;
        }

        WorldServer world = (WorldServer) event.world;
        int dim = world.provider.dimensionId;
        System.currentTimeMillis();

        int count = 0;
        ArrayList<ChunkLoc> chunks = WorldTicker.chunksToGenerate.get(Integer.valueOf(dim));
        if (chunks != null && chunks.size() > 0)
        {
            for (int a = 0; a < 10; a++)
            {
                chunks = WorldTicker.chunksToGenerate.get(Integer.valueOf(dim));
                if (chunks == null || chunks.size() <= 0)
                {
                    break;
                }
                count++;
                ChunkLoc loc = chunks.get(0);
                long worldSeed = world.getSeed();
                Random fmlRandom = new Random(worldSeed);
                long xSeed = fmlRandom.nextLong() >> 3;
                long zSeed = fmlRandom.nextLong() >> 3;
                fmlRandom.setSeed(xSeed * loc.chunkXPos + zSeed * loc.chunkZPos ^ worldSeed);
                this.worldGenerator(world, loc, fmlRandom);
                chunks.remove(0);
                WorldTicker.chunksToGenerate.put(Integer.valueOf(dim), chunks);
            }

            if (count > 0)
            {
                LogHandler.log("Regenerated " + count + " chunks. " + Math.max(0, chunks.size()) + " chunks left");
            }
        }
    }

    @Override
    public void worldGenerator(WorldServer world, ChunkLoc loc, Random fmlRandom)
    {
        WorldGenMetals.generateAll(fmlRandom, loc.chunkXPos, loc.chunkZPos, world, false);
    }

}
