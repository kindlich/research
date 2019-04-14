package com.hrznstudio.research.api.research;

import com.hrznstudio.research.ResearchMod;
import com.hrznstudio.research.api.impl.DefaultPlace;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnull;

public interface IResearchPlace {
    
    IResearchPlace defaultPlace = new DefaultPlace(new ResourceLocation(ResearchMod.MODID, "defaultPlace"));
    
    @Nonnull
    @Contract(pure = true)
    ResourceLocation getId();
}

