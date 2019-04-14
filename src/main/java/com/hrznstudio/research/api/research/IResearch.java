package com.hrznstudio.research.api.research;

import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;

import java.util.List;

public interface IResearch {
    
    @Contract(pure = true)
    IResearchStepInstance getStart();
    
    @Contract(mutates = "this")
    void addPrerequisite(IResearch prerequisite);
    
    @Contract(pure = true)
    List<IResearch> getPrerequisites();
    
    @Contract(pure = true)
    ResourceLocation getId();
    
    /**
     * Can the player do this research multiple times?
     * Usually false, but you could for example use this system to instead craft something then you might want it to be true.
     */
    @Contract(pure = true)
    default boolean canBeCompletedMultipleTimes() {
        return false;
    }
    
    @Contract(value = "null -> false", pure = true)
    boolean equals(Object other);
}
