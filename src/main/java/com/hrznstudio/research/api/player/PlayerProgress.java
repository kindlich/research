package com.hrznstudio.research.api.player;

import com.hrznstudio.research.APIMethods;
import com.hrznstudio.research.api.research.IResearch;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerProgress {
    private final EntityPlayer player;
    private final Map<IResearch, ResearchProgress> progressMap;
    private final Collection<IResearch> completedResearches;

    @Contract(pure = true)
    public PlayerProgress(EntityPlayer player) {
        this.player = player;
        progressMap = new HashMap<>();
        completedResearches = new ArrayList<>();
    }

    public boolean hasCompletedResearch(IResearch research) {
        return completedResearches.contains(research);
    }

    public void completeResearch(IResearch research) {
        this.completedResearches.add(research);
    }

    public EntityPlayer getPlayer() {
        return this.player;
    }

    public ResearchProgress getProgressFor(IResearch research) {
        return progressMap.computeIfAbsent(research, r -> new ResearchProgress(r, this));
    }

    public Collection<IResearch> getAvailableResearches() {
        return APIMethods.getAllResearches().stream()
                .filter(r -> r.isVisibleTo(this))
                .collect(Collectors.toList());
    }
}
