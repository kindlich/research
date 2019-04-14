package com.hrznstudio.research.api.impl;

import com.hrznstudio.research.api.research.IResearch;
import com.hrznstudio.research.api.research.IResearchStep;
import com.hrznstudio.research.api.research.IResearchStepInstance;

import java.util.ArrayList;
import java.util.List;

public class ResearchStepInstance implements IResearchStepInstance {
    
    private final IResearch research;
    private final IResearchStep step;
    private List<IResearchStepInstance> nextSteps = new ArrayList<>();
    
    public ResearchStepInstance(IResearch research, IResearchStep step) {
        this.research = research;
        this.step = step;
    }
    
    @Override
    public void addNextStep(IResearchStepInstance nextStep) {
        this.nextSteps.add(nextStep);
    }
    
    @Override
    public IResearch getResearch() {
        return research;
    }
    
    @Override
    public IResearchStep getStep() {
        return step;
    }
    
    @Override
    public List<IResearchStepInstance> getNextSteps() {
        return nextSteps;
    }
    
    @Override
    public String toString() {
        return "{step: " +  step.getId() + ", next: " + nextSteps +"}";
    }
}
