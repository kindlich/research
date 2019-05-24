package com.hrznstudio.research.common.blocks.researchtable;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

import javax.swing.*;

public class TileResearchTable extends TileEntity {
    private final ItemStackHandlerResearchTable toolSlots;

    public TileResearchTable() {

        toolSlots = new ItemStackHandlerResearchTable(30, 3);
        for (int i = 0; i < toolSlots.getSlots(); i++) {
            toolSlots.setStackInSlot(i, new ItemStack(Blocks.WOOL, i + 1, i % 16));
        }

        toolSlots.setToolSlot(3, ItemStack.EMPTY);
    }

    public ItemStackHandlerResearchTable getToolSlots() {
        return toolSlots;
    }
}
