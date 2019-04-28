package com.hrznstudio.research.helpers;

import com.hrznstudio.research.api.place.IResearchPlace;
import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.player.ResearchProgress;
import com.hrznstudio.research.api.research.IResearch;
import com.hrznstudio.research.api.research.IResearchStep;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class LinearResearch implements IResearch {
    private final ResourceLocation id;
    private final ResourceLocation tableId;
    private final List<IResearchStep> steps;

    @Contract(pure = true)
    public LinearResearch(ResourceLocation id, ResourceLocation tableId, List<IResearchStep> steps) {
        this.id = id;
        this.tableId = tableId;
        this.steps = steps;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public boolean isRepeatable(PlayerProgress playerProgress) {
        return false;
    }

    @Override
    public Predicate<PlayerProgress> getPrerequisite() {
        return playerProgress -> true;
    }

    @Override
    public boolean visibleAt(IResearchPlace researchPlace) {
        return researchPlace.getId() == this.tableId;
    }

    @Override
    public Collection<IResearchStep> getAvailableSteps(ResearchProgress playerProgress) {

        for (IResearchStep step : this.steps) {
            if (!playerProgress.getCompletedSteps().contains(step))
                return Collections.singleton(step);
        }
        return Collections.emptySet();
    }

    @Override
    public void onPlayerComplete(PlayerProgress playerProgress) {
        System.out.println(playerProgress.getPlayer() + " completed " + getId());
    }
}
