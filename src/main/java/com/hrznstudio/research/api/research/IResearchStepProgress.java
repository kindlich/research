package com.hrznstudio.research.api.research;

import com.hrznstudio.research.api.place.IResearchPlace;
import com.hrznstudio.research.api.player.PlayerProgress;

import java.io.Serializable;

public interface IResearchStepProgress extends Serializable {
    boolean canBeCompleted(PlayerProgress progress, IResearchPlace place);

    IResearchStep getStep();
}
