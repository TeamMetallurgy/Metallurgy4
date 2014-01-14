package com.teammetallurgy.metallurgy.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

public abstract class ContainerMetallurgy extends Container
{

    public TileEntityMetallurgySided tileEntity;
    private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;

    @Override
    public void addCraftingToCrafters(ICrafting crafter)
    {
        super.addCraftingToCrafters(crafter);
        crafter.sendProgressBarUpdate(this, 0, this.tileEntity.cookTime);
        crafter.sendProgressBarUpdate(this, 1, this.tileEntity.burnTime);
        crafter.sendProgressBarUpdate(this, 2, this.tileEntity.currentItemBurnTime);
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if (this.lastCookTime != this.tileEntity.cookTime)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.tileEntity.cookTime);
            }

            if (this.lastBurnTime != this.tileEntity.burnTime)
            {
                icrafting.sendProgressBarUpdate(this, 1, this.tileEntity.burnTime);
            }

            if (this.lastItemBurnTime != this.tileEntity.currentItemBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 2, this.tileEntity.currentItemBurnTime);
            }
        }

        this.lastCookTime = this.tileEntity.cookTime;
        this.lastBurnTime = this.tileEntity.burnTime;
        this.lastItemBurnTime = this.tileEntity.currentItemBurnTime;
    }

    @Override
    public void updateProgressBar(int id, int newValue)
    {
        if (id == 0)
        {
            this.tileEntity.cookTime = newValue;
        }

        if (id == 1)
        {
            this.tileEntity.burnTime = newValue;
        }

        if (id == 2)
        {
            this.tileEntity.currentItemBurnTime = newValue;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return this.tileEntity.isUseableByPlayer(entityplayer);
    }

}