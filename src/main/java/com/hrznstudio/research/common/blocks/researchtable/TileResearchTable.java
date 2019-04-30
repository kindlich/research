package com.hrznstudio.research.common.blocks.researchtable;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

public class TileResearchTable extends TileEntity {
    private final ItemStackHandler toolSlots;

    public TileResearchTable() {
        toolSlots = new ItemStackHandler(9);
        for (int i = 0; i < toolSlots.getSlots(); i++) {
            toolSlots.setStackInSlot(i, new ItemStack(Items.APPLE, i + 1));
        }
    }

    public ItemStackHandler getToolSlots() {
        return toolSlots;
    }
}
