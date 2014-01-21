package com.teammetallurgy.metallurgy.machines;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotMetallurgy extends Slot
{

    private int id;
    private TileEntityMetallurgy container;

    public SlotMetallurgy(TileEntityMetallurgy inventory, int id, int x, int y)
    {
        super(inventory, id, x, y);
        this.id = id;
        this.container = inventory;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return container.isItemValidForSlot(id, stack);
    }

}
