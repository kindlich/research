package com.hrznstudio.research.common.gui;

public class DrawPaneWrapperHoverColor<Content extends DrawPane> extends DrawPaneWrapper<Content> {

    private final int color;

    public DrawPaneWrapperHoverColor(Content content, int color) {
        super(content);
        this.color = color;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (containsPoint(mouseX, mouseY))
            drawRect(color);

        super.draw(mouseX, mouseY);
    }
}
