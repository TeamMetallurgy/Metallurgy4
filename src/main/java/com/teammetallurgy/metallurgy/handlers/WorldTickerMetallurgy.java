package com.teammetallurgy.metallurgy.handlers;

import java.util.Random;

import net.minecraft.world.WorldServer;

import com.teammetallurgy.metallurgy.world.WorldGenMetals;
import com.teammetallurgy.metallurgycore.handlers.ChunkLoc;
import com.teammetallurgy.metallurgycore.handlers.WorldTicker;

public class WorldTickerMetallurgy extends WorldTicker
{

    @Override
    public void worldGenerator(WorldServer world, ChunkLoc loc, Random fmlRandom)
    {
        WorldGenMetals.generateAll(fmlRandom, loc.chunkXPos, loc.chunkZPos, world, false);
    }

}
