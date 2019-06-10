package com.hrznstudio.research.api.gui;

import com.hrznstudio.research.APIMethods;
import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.research.IResearch;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class CanvasResearchListEntry extends Canvas {

    private final int buttonNo;
    private final PlayerProgress progress;

    private IResearch research;

    protected CanvasResearchListEntry(Canvas parent, double width, double height, int buttonNo, EntityPlayer player) {
        super(parent, width, height);
        this.buttonNo = buttonNo;
        this.progress = APIMethods.getProgress(player);
    }

    @Override
    public void initContent() {
        final List<IResearch> availableResearches = this.progress.getAvailableResearches();
        if (availableResearches.size() <= buttonNo) {
            this.setActive(false);
            this.research = null;
            return;
        }

        this.research = availableResearches.get(buttonNo);
        this.getSubCanvas(0, 0, getWidth(), getHeight(), CanvasConstructors.getBorder(1, 0xff000000));
    }

    @Override
    protected void drawContent(int mouseX, int mouseY) {
        final String text = research == null ? "" : research.getId().toString();
        this.renderer.drawText(getAbsX(), getAbsY(), getWidth(), getHeight(), 0xff0000ff, text);
    }

    @Override
    protected void drawBackgroundContent(int mouseX, int mouseY) {
        if (this.containsAbsolutePoint(mouseX, mouseY)) {
            this.renderer.drawRect(getAbsX(), getAbsY(), getWidth(), getHeight(), 0x89abcdef);
        }
    }

    @Override
    public void handleClick(int mouseX, int mouseY, int mouseButton) {
        if (this.containsAbsolutePoint(mouseX, mouseY)) {
            this.progress.setSelectedResearch(research);
        }
    }
}
