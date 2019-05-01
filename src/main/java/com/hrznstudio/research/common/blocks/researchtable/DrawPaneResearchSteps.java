package com.hrznstudio.research.common.blocks.researchtable;

import com.hrznstudio.research.api.player.ResearchProgress;
import com.hrznstudio.research.api.research.IResearchStepProgress;
import com.hrznstudio.research.common.gui.DrawPane;
import com.hrznstudio.research.common.gui.DrawPaneBorder;
import com.hrznstudio.research.common.gui.DrawPaneCollectionVertical;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collection;

@ParametersAreNonnullByDefault
public class DrawPaneResearchSteps extends DrawPane {
    private final Collection<DrawPane> stepList = new ArrayList<>();
    private Status status = DrawPaneResearchSteps.Status.NO_RESEARCH;
    private DrawPane header = null;
    private DrawPane body = null;

    public DrawPaneResearchSteps(GuiResearchTable guiResearchTable) {
        super(guiResearchTable);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        header.draw(mouseX, mouseY);
        switch (status) {
            case NO_RESEARCH:
                header.innerPane(2).drawLocalizedText(0xff000000, "research.table.gui.steps.desc");
                body.drawBorderAndReturnInnerPane(0xff000000, 1, 1).drawLocalizedText(0xffff0000, "research.table.gui.no.research");
                break;
            case STEPS:
                header.innerPane(2).drawLocalizedText(0xff000000, "research.table.gui.steps.desc");
                body.draw(mouseX, mouseY);
                break;
            case CURRENT_STEP:
                final IResearchStepProgress currentStep = getCurrentStep();
                header.innerPane(2).drawLocalizedText(0xff000000, "research.table.gui.steps.current.desc", currentStep == null ? "null" : currentStep);
                body.draw(mouseX, mouseY);
        }
    }


    @Override
    public void drawBackground(int mouseX, int mouseY) {
        if (this.status == Status.CURRENT_STEP)
            this.body.drawBackground(mouseX, mouseY);
    }

    @Override
    public void onTableUpdated() {
        super.onTableUpdated();
        init();
    }

    @Override
    public void init() {
        final int heightUnit = height / 7;
        this.header = new DrawPaneBorder(getSubPane(0, 0, width, heightUnit), 1, 0xff000000);

        this.body = getSubPane(0, heightUnit, width, 6 * heightUnit);
        if (this.guiResearchTable.getSelectedResearch() == null) {
            this.status = Status.NO_RESEARCH;
            return;
        }

        final ResearchProgress rProgess = this.guiResearchTable.progress.getProgressFor(this.guiResearchTable.getSelectedResearch());
        if (rProgess.hasCurrentStep()) {
            this.status = Status.CURRENT_STEP;
            this.body = rProgess.getCurrentStep().getDrawPane(this.body);
            return;
        }

        final Collection<DrawPane> buttons = new ArrayList<>(6);
        this.status = Status.STEPS;
        for (int i = 0; i < 6; i++) {
            final DrawPaneResearchStepSingle step = new DrawPaneResearchStepSingle(body.getSubPane(0, i * heightUnit, width, heightUnit), rProgess, i);
            step.init();
            buttons.add(step);
        }

        this.body = new DrawPaneCollectionVertical(body, buttons);

    }

    @Override
    public boolean resize(int startX, int startY, int width, int height) {
        if (!super.resize(startX, startY, width, height))
            return false;
        final int heightUnit = height / 7;
        if (header != null)
            header.resize(startX, startY, width, heightUnit);
        if (body != null)
            body.resize(startX, heightUnit, width, heightUnit * 6);
        return true;
    }

    @Override
    public void handleClick(int mouseX, int mouseY, int mouseButton) {
        if (this.status != Status.NO_RESEARCH && body.containsPoint(mouseX, mouseY))
            this.body.handleClick(mouseX, mouseY, mouseButton);
    }

    private IResearchStepProgress getCurrentStep() {
        if (this.guiResearchTable.getSelectedResearch() == null)
            return null;
        return this.guiResearchTable.progress.getProgressFor(this.guiResearchTable.getSelectedResearch()).getCurrentStep();
    }


    private enum Status {NO_RESEARCH, STEPS, CURRENT_STEP}
}
