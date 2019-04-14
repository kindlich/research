package com.hrznstudio.research.api.player;

import com.hrznstudio.research.api.research.IResearch;
import com.hrznstudio.research.api.research.IResearchStep;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public interface IResearchProgress {
    
    //Info, getters
    EntityPlayer getPlayer();
    
    List<IResearch> getCompletedResearches();
    
    List<IResearchStep> getCompletedResearchSteps();
    
    List<IResearch> getVisibleResearches();
    
    List<IResearch> getAvailableResearches();
    
    CurrentResearchProgress getCurrentResearchProgress();
    
    
    //Info, checks
    boolean hasCompletedResearch(IResearch research);
    
    boolean canSeeResearch(IResearch research);
    
    //Actions
    void update();
    
    void finishResearch(IResearch research);
    
    boolean hasCompletedResearchStep(IResearchStep step);
    
    void clearAllProgress();
}
