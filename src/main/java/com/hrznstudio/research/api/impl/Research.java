package com.hrznstudio.research.api.impl;

import com.hrznstudio.research.api.research.IResearch;
import com.hrznstudio.research.api.research.IResearchStepInstance;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class Research implements IResearch {
    
    private final List<IResearch> prerequisites = new ArrayList<>();
    private IResearchStepInstance start = null;
    private final ResourceLocation id;
    
    public Research(ResourceLocation id) {
        this.id = id;
    }
    
    @Override
    public IResearchStepInstance getStart() {
        return start;
    }
    
    @Override
    public void addPrerequisite(IResearch prerequisite) {
        this.prerequisites.add(prerequisite);
    }
    
    @Override
    public List<IResearch> getPrerequisites() {
        return prerequisites;
    }
    
    @Override
    public ResourceLocation getId() {
        return id;
    }
    
    public void setStart(IResearchStepInstance start) {
        this.start = start;
    }
    
    @Override
    public String toString() {
        return "{Research: {start: " + start.toString() + "}}";
    }
}
