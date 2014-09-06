package com.teammetallurgy.metallurgy.tnt;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityExplosive extends Entity
{
    private byte fuse = 80;
    private byte type;
    private byte updateTick = 0;
    private EntityLivingBase placedBy;

    public EntityExplosive(World world)
    {
        super(world);
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.98F);
        this.yOffset = this.height / 2.0F;
    }

    public EntityExplosive(World world, double x, double y, double z, byte explosiveType, EntityLivingBase placer)
    {
        this(world);
        this.setPosition(x, y, z);
        double fuzzyMotion = (Math.random() * Math.PI * 2.0D);
        this.motionX = (-(Math.sin(fuzzyMotion) * 0.02D));
        this.motionY = 0.02D;
        this.motionZ = (-(Math.cos(fuzzyMotion) * 0.02D));
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.type = explosiveType;
        this.placedBy = placer;
    }

    @Override
    protected void entityInit()
    {
        this.dataWatcher.addObject(2, Byte.valueOf((byte) this.fuse));
        this.dataWatcher.addObject(3, Byte.valueOf((byte) this.type));
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

    @Override
    public void onUpdate()
    {

        if (this.worldObj.isRemote && this.dataWatcher.hasChanges())
        {
            this.fuse = this.dataWatcher.getWatchableObjectByte(2);
            this.type = this.dataWatcher.getWatchableObjectByte(3);
            this.dataWatcher.func_111144_e();
        }

        if (this.updateTick % 7 == 0 && !this.worldObj.isRemote)
        {
            this.dataWatcher.updateObject(2, this.fuse);
            this.dataWatcher.updateObject(3, this.type);
        }

        this.updateTick++;

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.04D;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.98D;
        this.motionY *= 0.98D;
        this.motionZ *= 0.98D;

        if (this.onGround)
        {
            this.motionX *= 0.7D;
            this.motionY *= -0.5D;
            this.motionZ *= 0.7D;
        }

        if (this.fuse-- <= 0)
        {

            if (!this.worldObj.isRemote)
            {
                this.setDead();
                this.explode();
            }

        }
        else
        {
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
        }

    }

    private void explode()
    {
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 4.0F, true);
    }

    public EntityLivingBase getTntPlacedBy()
    {
        return this.placedBy;
    }

    public int getType()
    {
        return this.type;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getShadowSize()
    {
        return 0.0F;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tagCompound)
    {
        this.fuse = tagCompound.getByte("Fuse");
        this.type = tagCompound.getByte("Type");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setByte("Fuse", this.fuse);
        tagCompound.setShort("Type", this.type);
    }

    public void setFuse(byte length)
    {
        this.fuse = length;
        this.dataWatcher.updateObject(2, length);
    }

    public byte getFuse()
    {
        return this.fuse;
    }

}
