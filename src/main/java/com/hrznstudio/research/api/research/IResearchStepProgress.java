package com.hrznstudio.research.api.research;

import com.hrznstudio.research.api.gui.Canvas;
import com.hrznstudio.research.api.place.IResearchPlace;
import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.player.ResearchProgress;
import com.hrznstudio.research.common.gui.DrawPane;

import java.io.Serializable;

public interface IResearchStepProgress extends Serializable {
    boolean canBeCompleted(PlayerProgress progress, IResearchPlace place);

    IResearchStep getStep();

    DrawPane getDrawPane(DrawPane parent, ResearchProgress progress);

    void attachCanvas(Canvas canvasContent);
}
