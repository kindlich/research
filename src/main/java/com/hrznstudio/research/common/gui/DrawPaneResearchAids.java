package com.hrznstudio.research.common.gui;

import com.hrznstudio.research.common.blocks.researchtable.GuiResearchTable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DrawPaneResearchAids extends DrawPane {
    public DrawPaneResearchAids(GuiResearchTable guiResearchTable) {
        super(guiResearchTable);
    }

    @Override
    public void draw(int mouseX, int mouseY) {

        final int heightUnit = height / 7;
        final DrawPane header = getSubPane(0, 0, width, heightUnit);
        header.drawRect(0xabcdef01);
        header.drawLocalizedText("research.aid.desc", 0xab345678);

        final DrawPane mainContent = getSubPane(0, heightUnit, width, heightUnit * 6);
        mainContent.drawRect(0x30663301);
    }

    @Override
    void init() {

    }

    @Override
    public void handleClick(int mouseX, int mouseY, int mouseButton) {

    }
}
