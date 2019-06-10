package com.hrznstudio.research.api.gui;

public class CanvasText extends Canvas {
    private final String text;
    private final int color;

    public CanvasText(Canvas parent, double width, double height, String text, int color) {
        super(parent, width, height);
        this.text = text;
        this.color = color;
    }

    @Override
    protected void drawContent(int mouseX, int mouseY) {
        this.renderer.drawText(getAbsX(), getAbsY(), getWidth(), getHeight(), color, text);
    }

    @Override
    protected void drawBackgroundContent(int mouseX, int mouseY) {

    }

    @Override
    protected void initContent() {

    }
}
