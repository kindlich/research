package com.hrznstudio.research.api.gui;

/**
 * Used for Canvasses that are not meant for drawing themselves,
 * but only to provide structure and delegate to their children
 *
 * In short it saves you implementing the two empty {@link Canvas#drawContent} and {@link Canvas#drawBackgroundContent} methods
 */
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
