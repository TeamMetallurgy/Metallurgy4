package com.teammetallurgy.metallurgy.handlers;

import java.io.Serializable;

import net.minecraft.world.ChunkCoordIntPair;

public class ChunkLoc implements Serializable
{
    public final int chunkXPos;
    public final int chunkZPos;

    public ChunkLoc(int x, int z)
    {
        this.chunkXPos = x;
        this.chunkZPos = z;
    }

    public boolean equals(ChunkLoc obj)
    {
        return (obj.chunkXPos == this.chunkXPos) && (obj.chunkZPos == this.chunkZPos);
    }

    public boolean equals(ChunkCoordIntPair pair)
    {
        return (pair.chunkXPos == this.chunkXPos) && (pair.chunkZPos == this.chunkZPos);
    }

    public int getCenterXPos()
    {
        return (this.chunkXPos << 4) + 8;
    }

    public int getCenterZPos()
    {
        return (this.chunkZPos << 4) + 8;
    }

    @Override
    public String toString()
    {
        return "[" + this.chunkXPos + ", " + this.chunkZPos + "]";
    }
}
