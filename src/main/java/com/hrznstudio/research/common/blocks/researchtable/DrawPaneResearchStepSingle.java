package com.hrznstudio.research.common.blocks.researchtable;

import com.hrznstudio.research.api.player.ResearchProgress;
import com.hrznstudio.research.api.research.IResearchStep;
import com.hrznstudio.research.common.gui.DrawPane;

import java.util.function.Consumer;

public class DrawPaneResearchStepSingle extends DrawPane{
    private final ResearchProgress progress;
    private final int buttonNumber;
    private IResearchStep step;

    public DrawPaneResearchStepSingle(DrawPane other, ResearchProgress progress, int step) {
        super(other);
        this.progress = progress;
        this.buttonNumber = step;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (step == null)
            return;

        final int color;
        if (step.canBeStarted(progress.getPlayerProgress(), null)) {
            color = containsPoint(mouseX, mouseY) ? 0xff00ffff : 0xff000000;
        } else {
            color = containsPoint(mouseX, mouseY) ? 0xffff00ff : 0xffff0000;
        }
        this.drawBorderAndReturnInnerPane(0xff000000, 1, 1).drawLocalizedText(color, step.getId().getPath());
    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {

    }

    @Override
    public void init() {
        final Object[] objects = progress.getAvailableSteps().toArray();
        if (objects.length > buttonNumber)
            this.step = (IResearchStep) objects[buttonNumber];
    }

    @Override
    public void handleClick(int mouseX, int mouseY, int mouseButton) {
        if (step != null && containsPoint(mouseX, mouseY)) {
            progress.startStep(step, null);
        }
    }


    @Override
    public void tearDown() {
    }
}
