package com.teammetallurgy.metallurgy.handlers;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.world.ChunkDataEvent;

import com.teammetallurgy.metallurgy.lib.Configs;
import com.teammetallurgy.metallurgycore.handlers.ChunkLoc;
import com.teammetallurgy.metallurgycore.handlers.EventHandler;
import com.teammetallurgy.metallurgycore.handlers.LogHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerMetallurgy extends EventHandler
{

    @SubscribeEvent
    @Override
    public void chunkLoad(ChunkDataEvent.Load event)
    {
        int dim = event.world.provider.dimensionId;

        Chunk regenChunk = event.getChunk();

        NBTTagCompound chunkNBT = event.getData().getCompoundTag(this.getModTag());
        if (Configs.regen && !Configs.regen_key.equals("") && !Configs.regen_key.equals(chunkNBT.getString("regen_key")))
        {
            LogHandler.log("World gen was never run for chunk at (" + regenChunk.xPosition + ", " + regenChunk.zPosition + ") Dim: " + dim);

            ArrayList<ChunkLoc> chunks = WorldTickerMetallurgy.chunksToGenerate.get(dim);

            if (chunks == null)
            {
                WorldTickerMetallurgy.chunksToGenerate.put(dim, new ArrayList<ChunkLoc>());
                chunks = WorldTickerMetallurgy.chunksToGenerate.get(dim);
            }

            if (chunks != null)
            {
                chunks.add(new ChunkLoc(regenChunk.xPosition, regenChunk.zPosition));
                WorldTickerMetallurgy.chunksToGenerate.put(dim, chunks);
            }
            
        }
    }

    @SubscribeEvent
    @Override
    public void chunkSave(ChunkDataEvent.Save event)
    {
        NBTTagCompound tagCompound = new NBTTagCompound();
        event.getData().setTag(this.getModTag(), tagCompound);
        tagCompound.setString("regen_key", Configs.regen_key);
    }

    @Override
    public String getModTag()
    {
        return "Metallurgy";
    }

    @SubscribeEvent
    public void onBucketFill(FillBucketEvent event)
    {
        BucketsHandler.instance.fillBucket(event);
    }

}
