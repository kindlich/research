package com.hrznstudio.research.api.impl;

import com.hrznstudio.research.api.research.IResearchTool;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Range;

import java.util.HashMap;
import java.util.Map;

public class BaseResearchTool implements IResearchTool {
    
    private final Map<Ingredient, Integer> ingredientsToLevel = new HashMap<>();
    private final ResourceLocation id;
    
    public BaseResearchTool(ResourceLocation id) {
        this.id = id;
    }
    
    @Override
    public ResourceLocation getId() {
        return id;
    }
    
    @Override
    public Map<Ingredient, Integer> ingredientsToLevel() {
        return ingredientsToLevel;
    }
    
    @Override
    public @Range(from = -1, to = Integer.MAX_VALUE) int getLevel(Ingredient ingredient) {
        return ingredientsToLevel.getOrDefault(ingredient, -1);
    }
}
