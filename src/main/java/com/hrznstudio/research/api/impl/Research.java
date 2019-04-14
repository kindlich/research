package com.hrznstudio.research.api.impl;

import com.hrznstudio.research.api.research.IResearch;
import com.hrznstudio.research.api.research.IResearchStepInstance;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Research implements IResearch {
    
    private final List<IResearch> prerequisites = new ArrayList<>();
    private final ResourceLocation id;
    private IResearchStepInstance start = null;
    
    public Research(ResourceLocation id) {
        this.id = id;
    }
    
    @Override
    public IResearchStepInstance getStart() {
        return start;
    }
    
    public void setStart(IResearchStepInstance start) {
        this.start = start;
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
    
    @Override
    public String toString() {
        return "{Research: {start: " + start.toString() + "}}";
    }
    
    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(!(o instanceof Research))
            return false;
        Research research = (Research) o;
        return id.equals(research.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
