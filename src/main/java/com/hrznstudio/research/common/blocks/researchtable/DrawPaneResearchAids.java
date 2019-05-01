package com.hrznstudio.research.common.blocks.researchtable;

import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.research.IResearchAid;
import com.hrznstudio.research.common.blocks.aids.aidBattery.AidBattery;
import com.hrznstudio.research.common.gui.DrawPane;
import com.hrznstudio.research.common.gui.Renderer;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DrawPaneResearchAids extends DrawPane {
    private IResearchAid selectedAid = new AidBattery();
    private DrawPane content;
    private DrawPane header;
    private final PlayerProgress progress;

    public DrawPaneResearchAids(Renderer renderer, PlayerProgress progress) {
        super(renderer);
        this.progress = progress;
    }

    @Override
    public void draw(int mouseX, int mouseY) {

        drawBorder(0xff000000, 2);

        if (header != null)
            header.innerPane(1).drawLocalizedText(0xab345678, "research.table.aid.desc");

        if (content != null) {
            content.draw(mouseX, mouseY);
        }
    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {
        final int heightUnit = height / 7;
        if (header != null)
            header.fill(0xabcdef01);

        final DrawPane mainContent = getSubPane(0, heightUnit, width, heightUnit * 6);
        mainContent.fill(0x30663301);

        if (content != null)
            content.drawBackground(mouseX, mouseY);
    }

    @Override
    public void init() {
        final int heightUnit = height / 7;
        this.header = getSubPane(2, 2, width - 2, heightUnit - 2);

        final DrawPane mainContent = getSubPane(1, heightUnit, width - 1, heightUnit * 6);
        this.content = selectedAid == null ? null : selectedAid.drawInfo(null, mainContent, progress);
        if (content != null)
            content.init();
    }

    @Override
    public void tearDown() {
        if(this.content != null)
            this.content.tearDown();
        if (this.header != null) {
            this.header.tearDown();
        }
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
