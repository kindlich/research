package com.hrznstudio.research.common.gui;

public abstract class DrawPaneWrapper<Content extends DrawPane> extends DrawPane {

    protected final Content content;

    public DrawPaneWrapper(Content content) {
        super(content);
        this.content = content;
        this.resize(content.startX, content.startY, content.width, content.height);
    }


    @Override
    public void draw(int mouseX, int mouseY) {
        content.draw(mouseX, mouseY);
    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {
        content.drawBackground(mouseX, mouseY);
    }

    @Override
    public void init() {
        content.init();
    }

    @Override
    public void handleClick(int mouseX, int mouseY, int mouseButton) {
        content.handleClick(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean resize(int startX, int startY, int width, int height) {
        return !super.resize(startX, startY, width, height) && content.resize(startX, startY, width, height);
    }

    @Override
    public boolean containsPoint(int x, int y) {
        return content.containsPoint(x, y);
    }

    @Override
    public void tearDown() {
        content.tearDown();
    }
}
