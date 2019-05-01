package com.hrznstudio.research.common.gui;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class DrawPaneItem extends DrawPane {
    private final ItemStackHandler inventory;
    private final int inventorySlot;

    public DrawPaneItem(Renderer renderer, ItemStackHandler inventory, int inventorySlot) {
        super(renderer);
        this.inventory = inventory;
        this.inventorySlot = inventorySlot;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        renderer.drawItemStack(startX, startY, width, height, getStack());
        drawBorder(0xff000000, 2);

    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {

    }

    @Override
    public void init() {

    }

    @Override
    public void tearDown() {

    }

    public ItemStack getStack() {
        return inventory.getStackInSlot(inventorySlot);
    }

    @Override
    public void handleClick(int mouseX, int mouseY, int mouseButton) {
        final ItemStack stack = new ItemStack(getStack().getItem() == Items.APPLE ? Items.ARROW : Items.APPLE);
        inventory.setStackInSlot(inventorySlot, stack);
    }
}
