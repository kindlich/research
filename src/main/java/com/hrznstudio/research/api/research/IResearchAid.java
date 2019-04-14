package com.hrznstudio.research.api.research;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnull;

public interface IResearchAid {
    
    /**
     * The Aid's ID
     */
    @Nonnull
    @Contract(pure = true)
    ResourceLocation getId();
}
