package com.hrznstudio.research.api.gui;

import com.hrznstudio.research.common.blocks.researchtable.ItemStackHandlerResearchTable;

public class CanvasItemHolder extends Canvas {

    private final ItemStackHandlerResearchTable items;
    private int slot;

    public CanvasItemHolder(Canvas parent, double width, double height, ItemStackHandlerResearchTable items, int slot) {
        super(parent, width, height);
        this.items = items;
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    @Override
    protected void drawContent(int mouseX, int mouseY) {
        renderer.drawItemStack(getAbsX(), getAbsY(), getWidth(), getHeight(), items.getStackInSlot(slot));
    }

    @Override
    protected void drawBackgroundContent(int mouseX, int mouseY) {
        if (containsAbsolutePoint(mouseX, mouseY))
            renderer.drawRect(getAbsX(), getAbsY(), getWidth(), getHeight(), 0x89abcdef);
    }

    @Override
    protected void initContent() {

    }

    @Override
    public void handleClick(int mouseX, int mouseY, int mouseButton) {
        if (containsAbsolutePoint(mouseX, mouseY))
            items.clickSlot(slot, mouseButton);
    }
}
