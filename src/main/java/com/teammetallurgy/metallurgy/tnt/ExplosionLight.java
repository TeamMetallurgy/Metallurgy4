package com.teammetallurgy.metallurgy.tnt;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class ExplosionLight extends Explosion
{
    protected World worldObj;
    protected int BlockRadiusLimit = 16;
    protected Random explosionRNG = new Random();

    public ExplosionLight(World world, Entity entityExplosive, double posX, double posY, double posZ, float power)
    {
        super(world, entityExplosive, posX, posY, posZ, power);
        this.worldObj = world;
    }

}
