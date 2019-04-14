package com.hrznstudio.research.api.research;

import com.hrznstudio.research.ResearchMod;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnull;

public interface IResearchPlace {
    
    IResearchPlace defaultPlace = new DefaultPlace(new ResourceLocation(ResearchMod.MODID, "defaultPlace"));
    
    @Nonnull
    @Contract(pure = true)
    ResourceLocation getId();
}

final class DefaultPlace implements IResearchPlace {
    
    private final ResourceLocation id;
    
    DefaultPlace(ResourceLocation id) {
        this.id = id;
    }
    
    
    @Override
    @Nonnull
    @Contract(pure = true)
    public ResourceLocation getId() {
        return id;
    }
}
