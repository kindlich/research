package com.hrznstudio.research.api.gui;

public class CanvasFilled extends Canvas {
    private final int color;

    public CanvasFilled(Canvas parent, double width, double height, int color) {
        super(parent, width, height);
        this.color = color;
    }

    @Override
    protected void drawContent(int mouseX, int mouseY) {
    }

    @Override
    protected void drawBackgroundContent(int mouseX, int mouseY) {
        renderer.drawRect(getAbsX(), getAbsY(), getWidth(), getHeight(), color);
    }

    @Override
    protected void initContent() {

    }
}
