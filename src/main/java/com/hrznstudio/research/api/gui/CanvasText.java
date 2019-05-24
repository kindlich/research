package com.hrznstudio.research.api.gui;

public class CanvasText extends Canvas {
    private final String text;
    private final int color;

    protected CanvasText(Canvas parent, double offsetX, double offsetY, double width, double height, String text, int color) {
        super(parent, offsetX, offsetY, width, height);
        this.text = text;
        this.color = color;
    }

    @Override
    void drawContent(int mouseX, int mouseY) {
        this.renderer.drawText(getAbsX(), getAbsY(), getWidth(), getHeight(), color, text);
    }

    @Override
    void drawBackgroundContent(int mouseX, int mouseY) {

    }
}
