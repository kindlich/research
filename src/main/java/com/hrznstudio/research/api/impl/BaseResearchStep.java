package com.hrznstudio.research.api.impl;

import com.hrznstudio.research.api.research.IResearchAid;
import com.hrznstudio.research.api.research.IResearchStep;
import com.hrznstudio.research.api.research.IResearchTool;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Map;

public class BaseResearchStep implements IResearchStep {
    
    private final ResourceLocation id;
    private final Map<IResearchTool, Integer> requiredToolsAndLevels;
    private final Map<IResearchAid, Object> requiredAidsAndData;
    private final double minSuccess;
    
    public BaseResearchStep(ResourceLocation id, Map<IResearchTool, Integer> requiredToolsAndLevels, Map<IResearchAid, Object> requiredAidsAndData, double minSuccess) {
        this.id = id;
        this.requiredToolsAndLevels = requiredToolsAndLevels;
        this.requiredAidsAndData = requiredAidsAndData;
        this.minSuccess = minSuccess;
    }
    
    @Nonnull
    @Override
    public ResourceLocation getId() {
        return id;
    }
    
    @Nonnull
    @Override
    public Map<IResearchTool, Integer> getRequiredToolsAndLevels() {
        return requiredToolsAndLevels;
    }
    
    @Nonnull
    @Override
    public Map<IResearchAid, Object> getRequiredAidsAndData() {
        return requiredAidsAndData;
    }
    
    @Override
    public double getMinSuccess() {
        return minSuccess;
    }
}
