package com.hrznstudio.research.api.research;

import com.hrznstudio.research.api.place.IResearchPlace;
import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.player.ResearchProgress;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.function.Predicate;

public interface IResearch {

    ResourceLocation getId();

    boolean isRepeatable(PlayerProgress playerProgress);

    default boolean isVisibleTo(PlayerProgress playerProgress) {
        return getPrerequisite().test(playerProgress) && (isRepeatable(playerProgress) || !playerProgress.hasCompletedResearch(this));
    }

    Predicate<PlayerProgress> getPrerequisite();

    boolean visibleAt(IResearchPlace researchPlace);

    Collection<IResearchStep> getAvailableSteps(ResearchProgress playerProgress);

    void onPlayerComplete(PlayerProgress playerProgress);
}
