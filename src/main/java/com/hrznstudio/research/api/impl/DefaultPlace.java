package com.hrznstudio.research.api.impl;

import com.hrznstudio.research.api.research.IResearchPlace;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnull;

public class DefaultPlace implements IResearchPlace {
    
    private final ResourceLocation id;
    
    public DefaultPlace(ResourceLocation id) {
        this.id = id;
    }
    
    
    @Override
    @Nonnull
    @Contract(pure = true)
    public ResourceLocation getId() {
        return id;
    }
}
