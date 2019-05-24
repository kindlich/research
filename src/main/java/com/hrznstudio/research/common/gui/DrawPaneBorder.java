package com.hrznstudio.research.common.gui;

public class DrawPaneBorder extends DrawPaneWrapper<DrawPane> {
    private int size, color;

    public DrawPaneBorder(DrawPane parent, int size, int color) {
        super(parent);
        this.size = size;
        this.color = color;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        drawBorder(color, size);
        super.draw(mouseX, mouseY);
    } 
}
