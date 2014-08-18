package com.teammetallurgy.metallurgy.handlers;

import java.util.ArrayList;

import com.teammetallurgy.metallurgycore.handlers.ChunkLoc;
import com.teammetallurgy.metallurgycore.handlers.ConfigHandler;
import com.teammetallurgy.metallurgycore.handlers.EventHandler;
import com.teammetallurgy.metallurgycore.handlers.LogHandler;
import com.teammetallurgy.metallurgycore.handlers.WorldTicker;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerMetallurgy extends EventHandler
{

    @SubscribeEvent
    @Override
    public void chunkLoad(ChunkDataEvent.Load event)
    {
        int dim = event.world.provider.dimensionId;

        ChunkCoordIntPair loc = event.getChunk().getChunkCoordIntPair();

        if (!event.getData().getCompoundTag(this.getModTag()).hasKey(ConfigHandler.regenKey()) && ConfigHandler.regen())
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

    @SubscribeEvent
    @Override
    public void chunkSave(ChunkDataEvent.Save event)
    {
        NBTTagCompound tagCompound = new NBTTagCompound();
        event.getData().setTag(this.getModTag(), tagCompound);
        tagCompound.setBoolean(ConfigHandler.regenKey(), true);
    }

    @Override
    public String getModTag()
    {
        return "Metallurgy4";
    }

    @SubscribeEvent
    public void onBucketFill(FillBucketEvent event)
    {
        BucketsHandler.instance.fillBucket(event);
    }

}
