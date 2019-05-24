package com.hrznstudio.research.common.blocks.researchtable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStackHandlerResearchTable extends ItemStackHandler {
    private final int sizeToolSlots;
    private final int sizeAids;
    private ItemStack stackAtMouse = ItemStack.EMPTY;

    public ItemStackHandlerResearchTable(int sizeToolSlots, int sizeAids) {
        super(sizeToolSlots + sizeAids);
        this.sizeToolSlots = sizeToolSlots;
        this.sizeAids = sizeAids;
    }

    public ItemStack getStackAtMouse() {
        return this.stackAtMouse;
    }


    public void setStackAtMouse(ItemStack stack) {
        this.stackAtMouse = stack;
    }


    public ItemStack getToolSlot(int slot) {
        return getStackInSlot(getToolSlotId(slot));
    }


    public void setToolSlot(int slot, ItemStack stack) {
        setStackInSlot(getToolSlotId(slot), stack);
    }


    public ItemStack getAidSlot(int slot) {
        return getStackInSlot(getAidSlotId(slot));
    }


    public void setAidSlot(int slot, ItemStack stack) {
        setStackInSlot(getAidSlotId(slot), stack);
    }

    public int getSizeToolSlots() {
        return sizeToolSlots;
    }

    public int getSizeAids() {
        return sizeAids;
    }

    public int getToolSlotId(int slotNo) {
        return slotNo;
    }

    public int getAidSlotId(int slotNo) {
        return slotNo + getSizeToolSlots();
    }


    public void clickSlot(int slotNo) {
        final ItemStack existing = this.getStackInSlot(slotNo);
        final ItemStack mouse = this.getStackAtMouse();


        if (mouse.isEmpty()) {
            //Nothing at mouse -> pick up
            this.setStackAtMouse(existing);
            this.setStackInSlot(slotNo, ItemStack.EMPTY);

        } else if (existing.isEmpty()) {
            //Nothing in slot -> insert
            this.setStackAtMouse(ItemStack.EMPTY);
            this.setStackInSlot(slotNo, mouse);

        } else if (ItemHandlerHelper.canItemStacksStack(existing, mouse)) {
            //Can merge -> put in slot
            existing.setCount(existing.getCount() + mouse.getCount());
            this.setStackInSlot(slotNo, existing);
            this.setStackAtMouse(ItemStack.EMPTY);

        } else {
            //Cannot merge -> Swap
            this.setStackAtMouse(existing);
            this.setStackInSlot(slotNo, mouse);
        }
    }
}
