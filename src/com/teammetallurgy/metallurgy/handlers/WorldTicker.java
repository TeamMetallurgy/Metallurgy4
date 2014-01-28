package com.teammetallurgy.metallurgy.handlers;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;

import com.teammetallurgy.metallurgy.world.WorldGenMetals;

import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class WorldTicker implements ITickHandler
{

    public static HashMap<Integer, ArrayList<ChunkLoc>> chunksToGenerate = new HashMap<Integer, ArrayList<ChunkLoc>>();
    private long timeThisTick = 0L;

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {

    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
        WorldServer world = (WorldServer) tickData[0];
        int dim = world.provider.dimensionId;
        this.timeThisTick = System.currentTimeMillis();

        int count = 0;
        ArrayList<ChunkLoc> chunks = (ArrayList) chunksToGenerate.get(Integer.valueOf(dim));
        if ((chunks != null) && (chunks.size() > 0))
        {
            for (int a = 0; a < 10; a++)
            {
                chunks = (ArrayList) chunksToGenerate.get(Integer.valueOf(dim));
                if ((chunks == null) || (chunks.size() <= 0))
                {
                    break;
                }
                count++;
                ChunkLoc loc = (ChunkLoc) chunks.get(0);
                long worldSeed = world.getSeed();
                Random fmlRandom = new Random(worldSeed);
                long xSeed = fmlRandom.nextLong() >> 3;
                long zSeed = fmlRandom.nextLong() >> 3;
                fmlRandom.setSeed(xSeed * loc.chunkXPos + zSeed * loc.chunkZPos ^ worldSeed);
                WorldGenMetals.generateAll(fmlRandom, loc.chunkXPos, loc.chunkZPos, world, false);
                chunks.remove(0);
                chunksToGenerate.put(Integer.valueOf(dim), chunks);
            }
        }
        if (count > 0)
        {
            LogHandler.log("Regenerated " + count + " chunks. " + Math.max(0, chunks.size()) + " chunks left");
        }
    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.WORLD);
    }

    @Override
    public String getLabel()
    {
        return "MetallurgyWorld";
    }

}
