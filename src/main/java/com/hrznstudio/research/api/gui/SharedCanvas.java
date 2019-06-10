package com.hrznstudio.research.api.gui;

import org.jetbrains.annotations.Contract;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SharedCanvas extends Canvas {

    private final double hGap;
    private final int expectedCanvasCount;
    private double usedY = 0.0D;

    protected SharedCanvas(Canvas parent, double width, double height, double hGap, int expectedCanvasCount) {
        super(parent, width, height);
        this.hGap = hGap;
        this.expectedCanvasCount = expectedCanvasCount;
    }

    @Override
    public void addChild(Canvas child, double offsetX, double offsetY) {
        super.addChild(child, offsetX, offsetY + usedY);
        if (getAvailableHeight() < child.height)
            child.setActive(false);
        else
            usedY += Math.max(child.height + hGap, this.height);
    }

    @Contract(pure = true)
    private double getAvailableHeight() {
        return this.height - this.usedY;
    }

    @Contract(pure = true)
    public int getExpectedCanvasCount() {
        return expectedCanvasCount;
    }

    @Override
    protected void drawContent(int mouseX, int mouseY) {
    }

    @Override
    protected void drawBackgroundContent(int mouseX, int mouseY) {
    }

    @Override
    protected void initContent() {
    }

    @Override
    public boolean moveAndResize(double absoluteX, double absoluteY, double width, double height) {
        return super.moveAndResize(absoluteX, absoluteY, width, height);
    }
}
