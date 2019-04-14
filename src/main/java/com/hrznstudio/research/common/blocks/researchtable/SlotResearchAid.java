package com.hrznstudio.research.common.blocks.researchtable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotResearchAid extends SlotItemHandler {
    
    
    public SlotResearchAid(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }
    
    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return super.isItemValid(stack);
    }
}
