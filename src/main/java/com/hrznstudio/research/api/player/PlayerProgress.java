package com.hrznstudio.research.api.player;

import com.hrznstudio.research.api.research.IResearch;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

public class PlayerProgress {
    private final EntityPlayer player;
    private final Map<IResearch, ResearchProgress> progressMap;

    @Contract(pure = true)
    public PlayerProgress(EntityPlayer player) {
        this.player = player;
        progressMap = new HashMap<>();
    }

    public boolean hasCompletedResearch(IResearch research) {
        //TODO implement
        throw new UnsupportedOperationException();
    }

    public EntityPlayer getPlayer() {
        return this.player;
    }

    public ResearchProgress getProgressFor(IResearch research) {
        return progressMap.computeIfAbsent(research, r -> new ResearchProgress(r, this));
    }
}
