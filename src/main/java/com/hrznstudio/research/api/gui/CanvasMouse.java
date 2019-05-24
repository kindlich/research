package com.hrznstudio.research.api.gui;

import com.hrznstudio.research.common.blocks.researchtable.ItemStackHandlerResearchTable;
import com.hrznstudio.research.common.gui.Renderer;

public class CanvasMouse extends Canvas {

    private final ItemStackHandlerResearchTable toolSlots;

    protected CanvasMouse(Renderer renderer, double width, double height, ItemStackHandlerResearchTable toolSlots) {
        super(renderer, 0, 0, width, height);
        this.toolSlots = toolSlots;
    }

    @Override
    void drawContent(int mouseX, int mouseY) {
        renderer.drawItemStack(mouseX - 8, mouseY - 8, getWidth(), getHeight(), toolSlots.getStackAtMouse());
    }

    @Override
    void drawBackgroundContent(int mouseX, int mouseY) {

    }

    @Override
    public boolean moveAndResize(double absoluteX, double absoluteY, double width, double height) {
        return false;
    }

    @Override
    public boolean containsAbsolutePoint(double x, double y) {
        return false;
    }

    @Override
    public void handleClick(int mouseX, int mouseY, int mouseButton) {
    }


}
