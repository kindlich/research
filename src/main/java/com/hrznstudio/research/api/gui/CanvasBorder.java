package com.hrznstudio.research.api.gui;

public class CanvasBorder extends Canvas {

    private final int borderColor;
    private final int borderSize;

    public CanvasBorder(Canvas parent, double width, double height, int borderColor, int borderSize) {
        super(parent, width, height);
        this.borderColor = borderColor;
        this.borderSize = borderSize;
    }

    @Override
    protected void drawContent(int mouseX, int mouseY) {
        //Top/Bottom
        renderer.drawRect(getAbsX(), getAbsY(), getWidth(), borderSize, borderColor);
        renderer.drawRect(getAbsX(), getAbsY() + getHeight(), getWidth(), borderSize, borderColor);

        //Sides
        renderer.drawRect(getAbsX(), getAbsY(), borderSize, getHeight(), borderColor);
        renderer.drawRect(getAbsX() + getWidth(), getAbsY(), borderSize, getHeight() + borderSize, borderColor);
    }

    @Override
    protected void drawBackgroundContent(int mouseX, int mouseY) {
        //Top/Bottom
        renderer.drawRect(getAbsX(), getAbsY(), getWidth(), borderSize, borderColor);
        renderer.drawRect(getAbsX(), getAbsY() + getHeight(), getWidth(), borderSize, borderColor);

        //Sides
        renderer.drawRect(getAbsX(), getAbsY(), borderSize, getHeight(), borderColor);
        renderer.drawRect(getAbsX() + getWidth(), getAbsY(), borderSize, getHeight() + borderSize, borderColor);
    }

    @Override
    protected void initContent() {

    }
}
