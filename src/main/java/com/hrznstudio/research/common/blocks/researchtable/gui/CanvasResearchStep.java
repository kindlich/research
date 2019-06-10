package com.hrznstudio.research.common.blocks.researchtable.gui;

import com.hrznstudio.research.api.gui.Canvas;
import com.hrznstudio.research.api.gui.CanvasConstructors;
import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.research.IResearchStep;

public class CanvasResearchStep extends Canvas {

    private final IResearchStep step;

    public CanvasResearchStep(Canvas parent, double width, double height, IResearchStep step) {
        super(parent, width, height);
        this.step = step;
    }

    @Override
    protected void drawContent(int mouseX, int mouseY) {

    }

    @Override
    protected void drawBackgroundContent(int mouseX, int mouseY) {

    }

    @Override
    protected void initContent() {
        getInnerCanvas(1.5D, CanvasConstructors.getText(this.step.getId().toString(), 0xff000000));
    }

    @Override
    public void handleClick(int mouseX, int mouseY, int mouseButton) {
        if (containsAbsolutePoint(mouseX, mouseY)) {
            final PlayerProgress playerProgress = this.place.getPlayerProgress();
            playerProgress.getProgressFor(playerProgress.getSelectedResearch()).startStep(this.step, this.place);
        }
    }
}
