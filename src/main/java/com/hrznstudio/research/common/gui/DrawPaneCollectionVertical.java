package com.hrznstudio.research.common.gui;

import com.hrznstudio.research.common.blocks.researchtable.GuiResearchTable;

import java.util.Collection;

public class DrawPaneCollectionVertical extends DrawPane {
    private final Collection<DrawPane> children;

    public DrawPaneCollectionVertical(GuiResearchTable guiResearchTable, Collection<DrawPane> children) {
        super(guiResearchTable);
        this.children = children;
    }

    public DrawPaneCollectionVertical(DrawPane other, Collection<DrawPane> children) {
        super(other);
        this.children = children;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        for (DrawPane child : children)
            child.draw(mouseX, mouseY);

    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {
        for (DrawPane child : children)
            child.drawBackground(mouseX, mouseY);
    }

    @Override
    public void init() {
        for (DrawPane child : children)
            child.init();
    }

    @Override
    public void handleClick(int mouseX, int mouseY, int mouseButton) {
        for (DrawPane child : children)
            if (child.containsPoint(mouseX, mouseY))
                child.handleClick(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean resize(int startX, int startY, int width, int height) {
        double ratio = this.height != 0.0D ? (double) this.height / height : 1.0D;
        if (!super.resize(startX, startY, width, height))
            return false;
        int setStartY = startY;
        if (children != null)
            for (DrawPane child : children) {
                final int h = (int) (child.height * ratio);
                child.resize(startX, setStartY, width, h);
                setStartY += h;
            }

        return true;
    }
}
