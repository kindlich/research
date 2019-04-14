package com.hrznstudio.research.api.research;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Range;

import java.util.Map;

public interface IResearchTool {
    
    @Contract(pure = true)
    ResourceLocation getId();
    
    @Contract(pure = true)
    Map<Ingredient, Integer> ingredientsToLevel();
    
    
    @Contract(pure = true)
    @Range(from = -1, to = Integer.MAX_VALUE) int getLevel(Ingredient ingredient);
    
}
