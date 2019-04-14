package com.hrznstudio.research.api.impl;

import com.hrznstudio.research.api.research.IResearchStep;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResearchBuilder {
    
    
    private final Research research;
    
    private List<ResearchStepInstance> lastResearchSteps;
    
    
    public ResearchBuilder(ResourceLocation id, IResearchStep start) {
        this.research = new Research(id);
        final ResearchStepInstance stepInstance = new ResearchStepInstance(research, start);
        research.setStart(stepInstance);
        lastResearchSteps = Collections.singletonList(stepInstance);
    }
    
    private ResearchBuilder(ResearchBuilder toClone) {
        this.research = toClone.research;
        lastResearchSteps = toClone.lastResearchSteps;
    }
    
    @Contract(value = "null -> fail", pure = true)
    public static ResearchBuilder mergePaths(ResearchBuilder... paths) {
        if(paths == null || paths.length == 0)
            throw new IllegalArgumentException("Null or no paths!");
        
        if(paths.length == 1)
            return paths[0];
        
        final ResearchBuilder builder = new ResearchBuilder(paths[0]);
        for(int i = 1; i < paths.length; i++) {
            final ResearchBuilder path = paths[i];
            if(path.research !=builder.research)
                throw new IllegalArgumentException("Cannot merge paths from different researches");
            builder.lastResearchSteps.addAll(path.lastResearchSteps);
        }
        
        return builder;
    }
    
    public ResearchBuilder addResearchStep(IResearchStep step) {
        return addChoice(step);
    }
    
    public ResearchBuilder addOptional(IResearchStep... steps) {
        final List<ResearchStepInstance> ssteps = new ArrayList<>(lastResearchSteps);
        
        for(IResearchStep step : steps) {
            final ResearchStepInstance stepInstance = new ResearchStepInstance(research, step);
            for(ResearchStepInstance lastResearchStep : lastResearchSteps) {
                lastResearchStep.addNextStep(stepInstance);
            }
            ssteps.add(stepInstance);
        }
        
        this.lastResearchSteps = ssteps;
        return this;
    }
    
    public ResearchBuilder addChoice(IResearchStep... steps) {
        final List<ResearchStepInstance> ssteps = new ArrayList<>();
        for(final IResearchStep step : steps) {
            
            final ResearchStepInstance stepInstance = new ResearchStepInstance(research, step);
            ssteps.add(stepInstance);
            
            for(final ResearchStepInstance lastResearchStep : lastResearchSteps) {
                lastResearchStep.addNextStep(stepInstance);
            }
        }
        
        lastResearchSteps = ssteps;
        
        return this;
    }
    
    public List<ResearchBuilder> splitPaths(IResearchStep... steps) {
        final List<ResearchBuilder> out = new ArrayList<>();
        
        for(final IResearchStep step : steps) {
            final ResearchBuilder builder = new ResearchBuilder(this);
            out.add(builder.addResearchStep(step));
        }
        
        return out;
    }
    
    public Research toResearch() {
        return research;
    }
}
