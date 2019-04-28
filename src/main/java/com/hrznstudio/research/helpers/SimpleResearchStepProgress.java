package com.hrznstudio.research.helpers;

import com.hrznstudio.research.api.place.IResearchPlace;
import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.research.IResearchStep;
import com.hrznstudio.research.api.research.IResearchStepProgress;
import org.jetbrains.annotations.Contract;

import java.util.Random;

public class SimpleResearchStepProgress implements IResearchStepProgress {


    private final IResearchStep step;
    private final Random random;

    @Contract(pure = true)
    public SimpleResearchStepProgress(IResearchStep step) {
        this.step = step;
        random = new Random(1234567890123456789L);
    }

    @Override
    public boolean canBeCompleted(PlayerProgress progress, IResearchPlace place) {
        return random.nextBoolean();
    }

    @Override
    public IResearchStep getStep() {
        return step;
    }
}
