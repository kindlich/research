package com.hrznstudio.research.api.player;

import com.hrznstudio.research.api.research.IResearch;
import com.hrznstudio.research.api.research.IResearchStepInstance;

import javax.annotation.Nullable;
import java.util.List;

public class CurrentResearchProgress {
    
    private final List<IResearchStepInstance> completedSteps;
    private final List<IResearchStepInstance> availableSteps;
    private final IResearchProgress researchProgress;
    private IResearch currentResearch;
    private double currentSuccess;
    
    
    public CurrentResearchProgress(IResearch currentResearch, List<IResearchStepInstance> completedSteps, List<IResearchStepInstance> availableSteps, double currentSuccess, ResearchProgress researchProgress) {
        this.currentResearch = currentResearch;
        this.completedSteps = completedSteps;
        this.availableSteps = availableSteps;
        this.currentSuccess = currentSuccess;
        this.researchProgress = researchProgress;
    }
    
    /**
     * Returns the step you have completed last
     * Useful if you only want to show the step you have completed and the ones you have available, for example.
     */
    @Nullable
    public IResearchStepInstance getLastCompletedStep() {
        return availableSteps.isEmpty() ? null : availableSteps.get(availableSteps.size() - 1);
    }
    
    public IResearch getCurrentResearch() {
        return currentResearch;
    }
    
    
    public List<IResearchStepInstance> getAvailableSteps() {
        return availableSteps;
    }
    
    public List<IResearchStepInstance> getCompletedSteps() {
        return completedSteps;
    }
    
    public double getCurrentSuccess() {
        return currentSuccess;
    }
    
    public boolean completeStep(IResearchStepInstance step, double currentSuccess) {
        if(completedSteps.isEmpty() && currentSuccess == 0.0D) {
            this.currentSuccess = currentSuccess;
        } else {
            this.currentSuccess *= currentSuccess;
        }
        
        if(!this.availableSteps.contains(step)) {
            return false;
        }
        
        this.completedSteps.add(step);
        this.availableSteps.clear();
        final List<IResearchStepInstance> nextSteps = step.getNextSteps();
        if(!nextSteps.isEmpty())
            this.availableSteps.addAll(nextSteps);
        else
            this.completeCurrentResearch();
        return true;
    }
    
    private void completeCurrentResearch() {
        this.researchProgress.finishResearch(this.currentResearch);
        this.currentResearch = null;
        
        this.availableSteps.clear();
        this.completedSteps.clear();
        this.currentSuccess = 0.0D;
    }
    
    public void startResearch(IResearch research) {
        if(this.currentResearch == research)
            return;
        this.availableSteps.clear();
        
        this.currentResearch = research;
        IResearchStepInstance step = research.getStart();
        
        if(!this.researchProgress.hasCompletedResearchStep(step.getStep())){
            this.availableSteps.add(step);
            return;
        }
        
        while(this.researchProgress.hasCompletedResearchStep(step.getStep()) && !step.getStep().canBeCompletedMultipleTimes()) {
            if(step.getNextSteps().size() != 1) {
                break;
            }
            step = step.getNextSteps().get(0);
        }
        
        this.availableSteps.addAll(step.getNextSteps());
        
        
    }
    
    public void clear() {
        completedSteps.clear();
        availableSteps.clear();
        currentResearch = null;
        currentSuccess = 0.0D;
    }
}
