package com.teammetallurgy.metallurgy.handlers;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.ChunkDataEvent;

public class EventHandler
{

    @ForgeSubscribe
    public void chunkSave(ChunkDataEvent.Save event)
    {
        NBTTagCompound tagCompound = new NBTTagCompound();
        event.getData().setTag("Metallurgy4", tagCompound);
        tagCompound.setBoolean(ConfigHandler.regenKey(), true);
    }

    @ForgeSubscribe
    public void chunkLoad(ChunkDataEvent.Load event)
    {
        int dim = event.world.provider.dimensionId;

        ChunkCoordIntPair loc = event.getChunk().getChunkCoordIntPair();

        if ((!event.getData().getCompoundTag("Metallurgy4").hasKey(ConfigHandler.regenKey()) && ConfigHandler.regen()))
        {
            LogHandler.log("Worlg gen was never run for chunk at " + loc);

            ArrayList<ChunkLoc> chunks = WorldTicker.chunksToGenerate.get(dim);

            if (chunks == null)
            {
                WorldTicker.chunksToGenerate.put(dim, new ArrayList<ChunkLoc>());
                chunks = WorldTicker.chunksToGenerate.get(dim);
            }

            if (chunks != null)
            {
                chunks.add(new ChunkLoc(loc.chunkXPos, loc.chunkZPos));
                WorldTicker.chunksToGenerate.put(dim, chunks);
            }
        }
    }
}
