package com.hrznstudio.research.api.gui;

public class CanvasFilled extends Canvas {
    private final int color;

    protected CanvasFilled(Canvas parent, double offsetX, double offsetY, double width, double height, int color) {
        super(parent, offsetX, offsetY, width, height);
        this.color = color;
    }

    @Override
    void drawContent(int mouseX, int mouseY) {
    }

    @Override
    void drawBackgroundContent(int mouseX, int mouseY) {
        renderer.drawRect(getAbsX(), getAbsY(), getWidth(), getHeight(), color);
    }
}
