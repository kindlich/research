package com.hrznstudio.research.api.research;

import com.hrznstudio.research.api.impl.BaseResearchTool;
import com.hrznstudio.research.api.player.IResearchProgress;
import com.hrznstudio.research.api.player.ResearchProgress;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
public class APIMethods {
    
    private static final Map<ResourceLocation, IResearchTool> researchTools = new HashMap<>();
    private static final Map<ResourceLocation, IResearchStep> researchSteps = new HashMap<>();
    private static final Map<ResourceLocation, IResearch> researches = new HashMap<>();
    private static final Map<UUID, IResearchProgress> playerResearches = new HashMap<>();
    
    public static IResearchTool getResearchTool(ResourceLocation location, boolean createIfAbsent) {
        return createIfAbsent ? researchTools.computeIfAbsent(location, BaseResearchTool::new) : researchTools.get(location);
    }
    
    @Contract(pure = true)
    public static IResearchStep getResearchStep(ResourceLocation location) {
        return researchSteps.get(location);
    }
    
    @Contract(pure = true)
    public static boolean containsResearchTool(ResourceLocation location) {
        return researchTools.containsKey(location);
    }
    
    @Contract(pure = true)
    public static boolean containsResearchStep(ResourceLocation location) {
        return researchSteps.containsKey(location);
    }
    
    public static void registerResearchStep(IResearchStep step) {
        if(containsResearchStep(step.getId()))
            throw new IllegalArgumentException(String.format("Step %s already exists", step.getId()));
        researchSteps.put(step.getId(), step);
    }
    
    
    public static void registerResearch(IResearch research) {
        if(containsResearchStep(research.getId())) {
            throw new IllegalArgumentException(String.format("Step %s already exists", research.getId()));
        }
        researches.put(research.getId(), research);
    }
    
    @Contract(pure = true)
    public static boolean containsResearch(ResourceLocation location) {
        return researches.containsKey(location);
    }
    
    @Contract(pure = true)
    public static IResearch getResearch(ResourceLocation location) {
        if(containsResearch(location))
            throw new IllegalArgumentException(String.format("No research for %s exists", location));
        
        return researches.get(location);
    }
    
    @Contract(pure = true)
    public static Map<ResourceLocation, IResearchTool> getResearchTools() {
        return researchTools;
    }
    
    @Contract(pure = true)
    public static Map<ResourceLocation, IResearchStep> getResearchSteps() {
        return researchSteps;
    }
    
    @Contract(pure = true)
    public static Map<ResourceLocation, IResearch> getResearches() {
        return researches;
    }
    
    
    @Nonnull
    @Contract("_ -> new")
    public static IResearchProgress getProgressForPlayer(EntityPlayer player) {
        return playerResearches.computeIfAbsent(player.getGameProfile().getId(), uuid -> {
            //TODO implement
            return new ResearchProgress(player);
        });
    }
}
