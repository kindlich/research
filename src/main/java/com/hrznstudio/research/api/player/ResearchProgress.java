package com.hrznstudio.research.api.player;

import com.hrznstudio.research.api.research.APIMethods;
import com.hrznstudio.research.api.research.IResearchStep;
import com.hrznstudio.research.api.research.IResearch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class ResearchProgress {
    
    private final EntityPlayer player;
    private final List<ResourceLocation> completedResearches;
    private final List<ResourceLocation> completedResearchSteps;
    private Map<ResourceLocation, IResearch> visibleResearches = null;
    private Map<ResourceLocation, IResearch> availableResearches = null;
    
    public ResearchProgress(EntityPlayer player, List<ResourceLocation> completedResearches, List<ResourceLocation> completedResearchSteps) {
        this.player = player;
        this.completedResearches = completedResearches;
        this.completedResearchSteps = completedResearchSteps;
    }
    
    public EntityPlayer getPlayer() {
        return player;
    }
    
    public List<ResourceLocation> getCompletedResearches() {
        return completedResearches;
    }
    
    public List<ResourceLocation> getCompletedResearchSteps() {
        return completedResearchSteps;
    }
    
    public boolean hasCompletedResearch(ResourceLocation research) {
        return completedResearches.contains(research);
    }
    
    public boolean hasCompletedResearch(IResearch research) {
        return hasCompletedResearch(research.getId());
    }
    
    public boolean hasCompletedResearchStep(ResourceLocation researchStep) {
        return completedResearchSteps.contains(researchStep);
    }
    
    public boolean hasCompletedAllResearches(Collection<ResourceLocation> researches) {
        return researches.stream().allMatch(this::hasCompletedResearch);
    }
    
    public boolean canSeeResearch(IResearch research) {
        return research.getPrerequisites().stream().allMatch(this::hasCompletedResearch);
    }
    
    public boolean canSeeResearch(ResourceLocation research) {
        return canSeeResearch(APIMethods.getResearch(research));
    }
    
    /**
     * All visible, that is those who are completed and those who can be started
     */
    public Map<ResourceLocation, IResearch> getVisibleResearches() {
        if(visibleResearches != null)
            return visibleResearches;
        
        final Map<ResourceLocation, IResearch> out = new HashMap<>();
        APIMethods.getResearches().forEach((location, research) -> {
            if(this.canSeeResearch(research))
                out.put(location, research);
        });
        return visibleResearches = out;
    }
    
    /**
     * Only those that are not yet completed
     */
    public Map<ResourceLocation, IResearch> getAvailableResearches() {
        if(availableResearches != null)
            return availableResearches;
        final Map<ResourceLocation, IResearch> out = new HashMap<>();
        getVisibleResearches().forEach((location, research) -> {
            if(!this.completedResearches.contains(location) || research.canBeCompletedMultipleTimes())
                out.put(location, research);
        });
        return availableResearches = out;
    }
    
    
    public void completeResearch(IResearch research) {
        completedResearches.add(research.getId());
        
        if(visibleResearches != null && availableResearches != null) {
            APIMethods.getResearches().forEach((location, research1) -> {
                if(research1.getPrerequisites().contains(research) && canSeeResearch(research1)) {
                    visibleResearches.put(location, research1);
                    availableResearches.put(location, research1);
                }
            });
        } else {
            //Already assigns it to the field so "ignoring" that result here
            getAvailableResearches();
        }
    }
    
    
    public void completeResearchStep(IResearchStep step) {
        completedResearchSteps.add(step.getId());
    }
}
