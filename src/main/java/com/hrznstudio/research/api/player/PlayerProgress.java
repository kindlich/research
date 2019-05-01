package com.hrznstudio.research.api.player;

import com.hrznstudio.research.APIMethods;
import com.hrznstudio.research.api.research.IResearch;
import com.hrznstudio.research.api.research.IResearchStep;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.Contract;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PlayerProgress {
    private final EntityPlayer player;
    private final Map<IResearch, ResearchProgress> progressMap;
    private final Collection<IResearch> completedResearches;
    private final Collection<Consumer<IResearch>> researchChangeListeners = new HashSet<>();
    private final Collection<Consumer<IResearchStep>> researchStepChangeListeners = new HashSet<>();
    private IResearch selectedResearch = null;

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
        setSelectedResearch(null);

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

    public boolean hasSelectedResearch() {
        return getSelectedResearch() != null;
    }

    public IResearch getSelectedResearch() {
        return selectedResearch;
    }

    public void setSelectedResearch(IResearch selectedResearch) {
        this.selectedResearch = selectedResearch;
        notifyResearchChangeListeners(selectedResearch);
    }

    public void notifyResearchChangeListeners(IResearch research) {
        for (Consumer<IResearch> listener : researchChangeListeners) {
            listener.accept(research);
        }
    }

    public void registerResearchChangeListener(Consumer<IResearch> listener) {
        researchChangeListeners.add(listener);
    }

    public void deRegisterResearchChangeListener(Consumer<IResearch> listener) {
        researchChangeListeners.remove(listener);
    }

    public void registerResearchStepChangeListener(Consumer<IResearchStep> listener) {
        researchStepChangeListeners.add(listener);
    }

    public void deRegisterResearchStepChangeListener(Consumer<IResearchStep> listener) {
        researchStepChangeListeners.remove(listener);
    }

    public void notifyResearchStepChangeListeners(IResearchStep research) {
        for (Consumer<IResearchStep> listener : researchStepChangeListeners) {
            listener.accept(research);
        }
    }
}
