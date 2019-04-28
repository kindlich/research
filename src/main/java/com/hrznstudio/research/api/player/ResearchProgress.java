package com.hrznstudio.research.api.player;

import com.hrznstudio.research.api.place.IResearchPlace;
import com.hrznstudio.research.api.research.IResearch;
import com.hrznstudio.research.api.research.IResearchStep;
import com.hrznstudio.research.api.research.IResearchStepProgress;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Collection;

public class ResearchProgress {
    private final Collection<IResearchStep> completedSteps;
    private final IResearch research;
    private final PlayerProgress playerProgress;
    private IResearchStepProgress currentStep;

    @Contract(pure = true)
    public ResearchProgress(IResearch research, PlayerProgress playerProgress) {
        this.research = research;
        this.playerProgress = playerProgress;
        this.completedSteps = new ArrayList<>();
        currentStep = null;
    }

    public Collection<IResearchStep> getCompletedSteps() {
        return completedSteps;
    }

    public PlayerProgress getPlayerProgress() {
        return playerProgress;
    }

    public void StartStep(IResearchStep step, IResearchPlace place) {
        if (step.canBeStarted(playerProgress, place))
            this.currentStep = step.onStarted(this, place);
        if (this.currentStep == null) {
            step.onCompleted(this, place);
            this.completedSteps.add(step);
        }
    }

    public IResearchStepProgress getCurrentStep() {
        return currentStep;
    }

    public boolean tryCompleteCurrentStep(IResearchPlace place) {
        if (this.currentStep == null)
            return false;

        if (this.currentStep.canBeCompleted(playerProgress, place)) {
            this.completedSteps.add(currentStep.getStep());
            this.currentStep.getStep().onCompleted(this, place);
            this.currentStep = null;
            return true;
        } else {
            return false;
        }
    }


    public Collection<IResearchStep> getAvailableSteps() {
        return this.research.getAvailableSteps(this);
    }

}
