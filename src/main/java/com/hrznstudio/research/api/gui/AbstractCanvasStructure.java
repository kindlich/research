package com.hrznstudio.research.api.gui;

public abstract class AbstractCanvasStructure extends Canvas {

    protected AbstractCanvasStructure(Canvas parent, double width, double height) {
        super(parent, width, height);
    }

    @Override
    protected void drawContent(int mouseX, int mouseY) {

    }

    @Override
    protected void drawBackgroundContent(int mouseX, int mouseY) {

    }
}
