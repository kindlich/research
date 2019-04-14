package com.hrznstudio.research.api.impl;

import com.hrznstudio.research.api.research.IResearchAid;
import com.hrznstudio.research.api.research.IResearchStep;
import com.hrznstudio.research.api.research.IResearchTool;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Range;

import java.util.HashMap;
import java.util.Map;

public class ResearchStepBuilder {
    
    private final ResourceLocation id;
    private final double minSuccess;
    private final Map<IResearchAid, Object> researchAidsToData = new HashMap<>();
    private final Map<IResearchTool, Integer> researchToolsAndLevels = new HashMap<>();
    
    public ResearchStepBuilder(ResourceLocation id, double minSuccess) {
        this.id = id;
        this.minSuccess = minSuccess;
    }
    
    @Contract(mutates = "this")
    public ResearchStepBuilder addResearchAid(IResearchAid aid, Object data) {
        researchAidsToData.put(aid, data);
        return this;
    }
    
    @Contract(mutates = "this")
    public ResearchStepBuilder addResearchTool(IResearchTool tool, @Range(from = 0, to = Integer.MAX_VALUE) int minLevel) {
        researchToolsAndLevels.put(tool, minLevel);
        return this;
    }
    
    @Contract(pure = true, value = "-> new")
    public IResearchStep toResearchStep() {
        return new BaseResearchStep(id, researchToolsAndLevels, researchAidsToData, minSuccess);
    }
    
}
