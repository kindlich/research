package com.hrznstudio.research.api.place;

import com.hrznstudio.research.api.research.IResearchAid;
import com.hrznstudio.research.api.research.IResearchTool;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;

public interface IResearchPlace {

    ResourceLocation getId();

    Collection<IResearchTool> getTools();

    Collection<IResearchAid> getAids();

    void setActiveAid(IResearchAid aid);

    void addAid(IResearchAid aid);
}
