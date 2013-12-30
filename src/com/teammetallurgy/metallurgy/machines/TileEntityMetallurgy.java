package com.teammetallurgy.metallurgy.machines;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityMetallurgy extends TileEntity
{

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound compound = new NBTTagCompound();
        writeCustomNBT(compound);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 3, compound);
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
    {
        readCustomNBT(pkt.data);
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        readCustomNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        writeCustomNBT(compound);
    }

    abstract protected void readCustomNBT(NBTTagCompound data);

    abstract protected void writeCustomNBT(NBTTagCompound compound);
}