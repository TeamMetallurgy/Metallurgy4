package com.teammetallurgy.metallurgy.guiwidgets;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityBlock extends Entity {

    private int brightness = -1;
    public double iSize, jSize, kSize;
    public float rotationX = 0;
    public float rotationY = 0;
    public float rotationZ = 0;
    public float shadowSize = 0;
    @SideOnly(Side.CLIENT)
    public Icon texture;

    public EntityBlock(final World world)
    {
        super(world);
        this.preventEntitySpawning = false;
        this.noClip = true;
        this.isImmuneToFire = true;
    }

    public EntityBlock(final World world, final double xPos, final double yPos, final double zPos)
    {
        super(world);
        setPositionAndRotation(xPos, yPos, zPos, 0, 0);
    }

    public EntityBlock(final World world, final double i, final double j, final double k, final double iSize, final double jSize, final double kSize)
    {
        this(world);
        this.iSize = iSize;
        this.jSize = jSize;
        this.kSize = kSize;
        setPositionAndRotation(i, j, k, 0, 0);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
    }

    @Override
    protected void entityInit()
    {
    }

    @Override
    public int getBrightnessForRender(final float par1)
    {
        return this.brightness > 0 ? this.brightness : super.getBrightnessForRender(par1);
    }

    @Override
    public void moveEntity(final double d, final double d1, final double d2)
    {
        setPosition(this.posX + d, this.posY + d1, this.posZ + d2);
    }

    @Override
    protected void readEntityFromNBT(final NBTTagCompound data)
    {
        this.iSize = data.getDouble("iSize");
        this.jSize = data.getDouble("jSize");
        this.kSize = data.getDouble("kSize");
    }

    public void setBrightness(final int brightness)
    {
        this.brightness = brightness;
    }

    @Override
    public void setPosition(final double d, final double d1, final double d2)
    {
        super.setPosition(d, d1, d2);
        this.boundingBox.minX = this.posX;
        this.boundingBox.minY = this.posY;
        this.boundingBox.minZ = this.posZ;

        this.boundingBox.maxX = this.posX + this.iSize;
        this.boundingBox.maxY = this.posY + this.jSize;
        this.boundingBox.maxZ = this.posZ + this.kSize;
    }

    @Override
    protected void writeEntityToNBT(final NBTTagCompound data)
    {
        data.setDouble("iSize", this.iSize);
        data.setDouble("jSize", this.jSize);
        data.setDouble("kSize", this.kSize);
    }
}
