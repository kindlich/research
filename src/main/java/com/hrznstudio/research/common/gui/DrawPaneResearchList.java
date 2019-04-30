package com.hrznstudio.research.common.gui;

import com.hrznstudio.research.common.blocks.researchtable.GuiResearchTable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DrawPaneResearchList extends DrawPane {
    public DrawPaneResearchList(GuiResearchTable guiResearchTable) {
        super(guiResearchTable);
    }

    @Override
    public void draw(int mouseX, int mouseY) {

    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {
        drawRect(0xabcdefff);
    }

    @Override
    void init() {

    }

    @Override
    public void handleClick(int mouseX, int mouseY, int mouseButton) {

    }
}
