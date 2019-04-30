package com.hrznstudio.research.common.gui;

import com.hrznstudio.research.api.research.IResearch;
import com.hrznstudio.research.common.blocks.researchtable.GuiResearchTable;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collection;

@ParametersAreNonnullByDefault
public class DrawPaneResearchList extends DrawPane {
    private final Collection<DrawPane> researchButtons = new ArrayList<>(6);
    private int heightUnit = 0;

    public DrawPaneResearchList(GuiResearchTable guiResearchTable) {
        super(guiResearchTable);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        for (DrawPane researchButton : researchButtons) {
            researchButton.draw(mouseX, mouseY);
        }
    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {
        drawRect(0xabcdefff);
    }

    @Override
    public void init() {
        researchButtons.clear();
        heightUnit = height / 7;


        for (int i = 1; i < 7; i++) {
            final int iFinal = i;
            this.researchButtons.add(new DrawPane(getSubPane(0, i * heightUnit, width, heightUnit)) {
                @Nullable
                private IResearch getResearch() {
                    final Collection<IResearch> availableResearches = guiResearchTable.progress.getAvailableResearches();
                    return availableResearches.size() < iFinal ? null : (IResearch) availableResearches.toArray()[iFinal - 1];
                }

                @Override
                public void draw(int mouseX, int mouseY) {
                    final IResearch res = getResearch();

                    if (res == null)
                        return;

                    final int color = containsPoint(mouseX, mouseY) ? 0xff00ffff : 0xff000000;

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
                    guiResearchTable.selectedResearch = getResearch();
                }
            });
        }
    }

    @Override
    public void handleClick(int mouseX, int mouseY, int mouseButton) {
        for (DrawPane researchButton : researchButtons) {
            if (researchButton.containsPoint(mouseX, mouseY))
                researchButton.handleClick(mouseX, mouseY, mouseButton);
        }
    }
}
