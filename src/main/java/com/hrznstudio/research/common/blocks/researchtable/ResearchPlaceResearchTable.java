package com.hrznstudio.research.common.blocks.researchtable;

import com.hrznstudio.research.ResearchMod;
import com.hrznstudio.research.api.place.IResearchPlace;
import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.research.IResearchAid;
import com.hrznstudio.research.api.research.IResearchTool;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.Collections;

public class ResearchPlaceResearchTable implements IResearchPlace {
    private final PlayerProgress playerProgress;
    private IResearchAid activeAid;

    public ResearchPlaceResearchTable(IResearchAid activeAid, PlayerProgress playerProgress) {
        this.activeAid = activeAid;
        this.playerProgress = playerProgress;
    }

    @Override
    public IResearchAid getActiveAid() {
        return activeAid;
    }

    @Override
    public void setActiveAid(IResearchAid aid) {

    }

    @Override
    public ResourceLocation getId() {
        return new ResourceLocation(ResearchMod.MODID, "research_place:table");
    }

    @Override
    public Collection<IResearchTool> getTools() {
        return Collections.emptySet();
    }

    @Override
    public Collection<IResearchAid> getAids() {
        return Collections.emptySet();
    }

    @Override
    public void addAid(IResearchAid aid) {

    }

    @Override
    public PlayerProgress getPlayerProgress() {
        return playerProgress;
    }
}
