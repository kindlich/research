package com.hrznstudio.research.api.player;

import com.hrznstudio.research.api.research.*;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResearchProgress implements IResearchProgress {
    
    private final EntityPlayer player;
    private final List<IResearch> completedResearches;
    private final List<IResearchStep> completedResearchSteps;
    private final CurrentResearchProgress currentResearch;
    
    private List<IResearch> visibleResearches = null;
    private List<IResearch> availableResearches = null;
    
    //TODO make this work
    public ResearchProgress(EntityPlayer player) {
        this.player = player;
        this.completedResearches = new ArrayList<>();
        this.completedResearchSteps = new ArrayList<>();
        this.currentResearch = new CurrentResearchProgress(null, new ArrayList<>(), new ArrayList<>(), 0.0D, this);
        this.update();
    }
    
    @Override
    public EntityPlayer getPlayer() {
        return player;
    }
    
    @Override
    public List<IResearch> getCompletedResearches() {
        return completedResearches;
    }
    
    @Override
    public List<IResearchStep> getCompletedResearchSteps() {
        return completedResearchSteps;
    }
    
    
    @Override
    public boolean hasCompletedResearch(IResearch research) {
        return completedResearches.contains(research);
    }
    
    @Override
    public boolean canSeeResearch(IResearch research) {
        return research.getPrerequisites().stream().allMatch(this::hasCompletedResearch);
    }
    
    @Override
    public void update() {
        visibleResearches = APIMethods.getResearches().values().stream().filter(this::canSeeResearch).collect(Collectors.toList());
        availableResearches = visibleResearches.stream().filter(r -> r.canBeCompletedMultipleTimes() || !completedResearches.contains(r)).collect(Collectors.toList());
    }
    
    @Override
    public void finishResearch(IResearch research) {
        this.completedResearches.add(research);
        this.update();
    }
    
    @Override
    public boolean hasCompletedResearchStep(IResearchStep step) {
        return this.completedResearchSteps.contains(step);
    }
    
    /**
     * All visible, that is those who are completed and those who can be started
     */
    @Override
    public List<IResearch> getVisibleResearches() {
        return visibleResearches;
    }
    
    /**
     * Only those that are not yet completed
     */
    @Override
    public List<IResearch> getAvailableResearches() {
        return availableResearches;
    }
    
    @Override
    public CurrentResearchProgress getCurrentResearchProgress() {
        return currentResearch;
    }
    
    @Override
    public void clearAllProgress() {
        completedResearches.clear();
        completedResearchSteps.clear();
        currentResearch.clear();
        update();
    }
}
