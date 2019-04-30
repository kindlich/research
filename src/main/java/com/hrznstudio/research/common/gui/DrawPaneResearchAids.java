package com.hrznstudio.research.common.gui;

import com.hrznstudio.research.api.research.IResearchAid;
import com.hrznstudio.research.common.blocks.aids.aidBattery.AidBattery;
import com.hrznstudio.research.common.blocks.researchtable.GuiResearchTable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DrawPaneResearchAids extends DrawPane {
    private IResearchAid selectedAid = new AidBattery();
    private DrawPane content;
    private DrawPane header;

    public DrawPaneResearchAids(GuiResearchTable guiResearchTable) {
        super(guiResearchTable);
    }

    @Override
    public void draw(int mouseX, int mouseY) {

        drawBorder(0xff000000, 2);

        header.innerPane(1).drawLocalizedText(0xab345678, "research.table.aid.desc");

        if (content != null) {
            content.draw(mouseX, mouseY);
        }
    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {
        final int heightUnit = height / 7;
        header.drawRect(0xabcdef01);

        final DrawPane mainContent = getSubPane(0, heightUnit, width, heightUnit * 6);
        mainContent.drawRect(0x30663301);

        if (content != null)
            content.drawBackground(mouseX, mouseY);
    }

    @Override
    public void init() {
        final int heightUnit = height / 7;
        this.header = getSubPane(2, 2, width-2, heightUnit - 2);

        final DrawPane mainContent = getSubPane(1, heightUnit, width - 1, heightUnit * 6);
        this.content = selectedAid == null ? null : selectedAid.drawInfo(null, mainContent, guiResearchTable.progress);
        if (content != null)
            content.init();
    }

    @Override
    public void handleClick(int mouseX, int mouseY, int mouseButton) {
        if (content != null)
            content.handleClick(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean resize(int startX, int startY, int width, int height) {
        if (!super.resize(startX, startY, width, height))
            return false;
        return content == null;//TODO recalculate content size || content.resize(startX, startY, width, height);
    }
}
