package com.hrznstudio.research.api.research;

import java.util.List;

public interface IResearchStepInstance {
    
    void addNextStep(IResearchStepInstance nextStep);
    
    IResearch getResearch();
    
    IResearchStep getStep();
    
    List<IResearchStepInstance> getNextSteps();
}
