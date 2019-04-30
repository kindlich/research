package com.hrznstudio.research.common.gui;

import com.hrznstudio.research.api.player.ResearchProgress;
import com.hrznstudio.research.api.research.IResearchStep;
import com.hrznstudio.research.common.blocks.researchtable.GuiResearchTable;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collection;

@ParametersAreNonnullByDefault
public class DrawPaneResearchSteps extends DrawPane {
    private final Collection<DrawPane> stepList = new ArrayList<>();
    private boolean noResearch = true;
    private DrawPane displayContent = null;
    private int heightUnit = 0;

    public DrawPaneResearchSteps(GuiResearchTable guiResearchTable) {
        super(guiResearchTable);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (noResearch) {
            drawRect(0xffff0000);
            drawLocalizedText(0xffffffff, "research.table.gui.no.research");
            return;
        } else if (displayContent == null) {
            drawRect(0xffff0000);
            drawLocalizedText(0xffffffff, "research.table.gui.no.steps");
            return;
        }

        for (DrawPane drawPane : stepList) {
            drawPane.draw(mouseX, mouseY);
        }
    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {
        //drawRect(0xabcdefff);
        if (noResearch || displayContent == null) {
            if (guiResearchTable.selectedResearch != null)
                init();
            return;
        }
        displayContent.draw(mouseX, mouseY);
    }

    @Override
    public void init() {

        if (guiResearchTable.selectedResearch == null) {
            noResearch = true;
            return;
        }
        noResearch = false;
        final ResearchProgress progress = guiResearchTable.progress.getProgressFor(guiResearchTable.selectedResearch);
        if (progress.hasCurrentStep()) {
            displayContent = progress.getCurrentStep().getDrawPane(this);
            displayContent.init();
            return;
        }
        heightUnit = height / 7;
        final Collection<DrawPane> buttons = new ArrayList<>();

        for (int i = 1; i < 7; i++) {
            final int iFinal = i;
            buttons.add(new DrawPane(getSubPane(0, i * heightUnit, width, heightUnit)) {
                @Nullable
                private IResearchStep getStep() {
                    final Collection<IResearchStep> availableSteps = progress.getAvailableSteps();
                    return availableSteps.size() < iFinal ? null : (IResearchStep) availableSteps.toArray()[iFinal - 1];
                }

                @Override
                public void draw(int mouseX, int mouseY) {
                    final IResearchStep res = getStep();

                    if (res == null)
                        return;

                    final int color;
                    if (res.canBeStarted(progress.getPlayerProgress(), null)) {
                        color = containsPoint(mouseX, mouseY) ? 0xff00ffff : 0xff000000;
                    } else {
                        color = containsPoint(mouseX, mouseY) ? 0xffff00ff : 0xffff0000;
                    }

                    drawBorderAndReturnInnerPane(0xff000000, 1, 1)
                            .drawText(color, res.getId().toString());
                }

                @Override
                public void drawBackground(int mouseX, int mouseY) {

                }

                @Override
                public void init() {

                }

                @Override
                public void handleClick(int mouseX, int mouseY, int mouseButton) {
                    final IResearchStep step = getStep();
                    if (step == null) {
                        return;
                    }
                    progress.StartStep(step, null);
                }
            });
        }

        final DrawPane researches = new DrawPaneWrapper<DrawPane>(getSubPane(0, heightUnit, this.width, heightUnit * 6)) {
            @Override
            public void draw(int mouseX, int mouseY) {
                super.draw(mouseX, mouseY);
                for (DrawPane button : buttons) {
                    //if(button.containsPoint(mouseX, mouseY)) {
                    button.draw(mouseX, mouseY);
                    //}
                }
            }

            @Override
            public void handleClick(int mouseX, int mouseY, int mouseButton) {
                super.handleClick(mouseX, mouseY, mouseButton);
                for (DrawPane button : buttons) {
                    if (button.containsPoint(mouseX, mouseY))
                        button.handleClick(mouseX, mouseY, mouseButton);
                }
            }
        };


        this.displayContent = new DrawPaneWrapper<DrawPane>(this) {
            @Override
            public void draw(int mouseX, int mouseY) {
                super.draw(mouseX, mouseY);
                //"Available Steps"
                getSubPane(0, 0, this.width, heightUnit).drawBorderAndReturnInnerPane(0xff000000, 2, 1).drawLocalizedText(0xff000000, "research.table.gui.steps.desc");
                researches.draw(mouseX, mouseY);
                //The actual steps
            }

            @Override
            public void handleClick(int mouseX, int mouseY, int mouseButton) {
                if (researches.containsPoint(mouseX, mouseY))
                    researches.handleClick(mouseX, mouseY, mouseButton);
            }
        };

    }

    @Override
    public boolean resize(int startX, int startY, int width, int height) {
        heightUnit = height / 7;
        return super.resize(startX, startY, width, height);
    }

    @Override
    public void handleClick(int mouseX, int mouseY, int mouseButton) {
        if (this.displayContent != null && this.displayContent.containsPoint(mouseX, mouseY))
            this.displayContent.handleClick(mouseX, mouseY, mouseButton);
    }
}
