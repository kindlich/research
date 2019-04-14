package com.hrznstudio.research.api.research;

import org.jetbrains.annotations.Contract;

import java.util.List;

public interface IResearchStepInstance {
    
    @Contract(mutates = "this")
    void addNextStep(IResearchStepInstance nextStep);
    
    @Contract(pure = true)
    IResearch getResearch();
    
    @Contract(pure = true)
    IResearchStep getStep();
    
    @Contract(pure = true)
    List<IResearchStepInstance> getNextSteps();
}
