package com.hrznstudio.research.api.research;

import java.util.List;
import java.util.Map;

public interface IResearchPlaceInstance {
    
    IResearchPlace getPlace();
    
    Map<IResearchTool, Integer> getToolsAndLevels();
    
    List<IResearchAidInstance> getResearchAids();
}
