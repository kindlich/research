package com.hrznstudio.research.common.blocks.researchtable;

import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.research.IResearch;
import com.hrznstudio.research.common.gui.DrawPane;
import org.jetbrains.annotations.Nullable;

public class DrawPaneResearchListSingle extends DrawPane {

    private final int i;
    private final PlayerProgress progress;

    public DrawPaneResearchListSingle(DrawPane other, int i, PlayerProgress progress) {
        super(other);
        this.i = i;
        this.progress = progress;
    }

    @Nullable
    private IResearch getResearch() {
        final Object[] availableResearches = progress.getAvailableResearches().toArray();
        return availableResearches.length <= i ? null : (IResearch) availableResearches[i];
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
        progress.setSelectedResearch(getResearch());
    }

    @Override
    public void tearDown() {

    }

}
