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
    
    /**
     * Whether or not this aid provides a small mini game
     */
    @Contract(pure = true)
    boolean providesGUI();
    
    
    /**
     * Use this to draw on the table for extended info (e.g. stored RF if you do a "battery" research aid)
     *
     * @param x     the x coordinate of the left side of the box
     * @param y     the y coordinate of the lower side of the box
     * @param xSize the width
     * @param ySize the height
     */
    void draw(int x, int y, int xSize, int ySize);
    
    
    /**
     * Checks if the aid is enough to complete the step
     */
    boolean matches(IResearchStep step, World world, BlockPos position);
    
    
    /**
     * Checks if the aid provides a minigame for the given block
     *
     * @return {@code null} -> no minigame,
     * {@code true} -> mandatory,
     * {@code false} -> optional
     */
    Boolean providesMiniGame(IResearchStep step, World world, BlockPos position);
    
    /**
     * Do the research step and return the score (usually between 0 and 1)
     */
    double doStep(IResearchStep step, World world, BlockPos position, boolean doMiniGame);
    
    SuccessChances getChancesFor(IResearchStep step, World world, BlockPos position);
    
    class SuccessChances {
        
        private final double minManual, maxManual, minAutomatic, maxAutomatic;
        
        public SuccessChances(double minManual, double maxManual, double minAutomatic, double maxAutomatic) {
            this.minManual = minManual;
            this.maxManual = maxManual;
            this.minAutomatic = minAutomatic;
            this.maxAutomatic = maxAutomatic;
        }
        
        @Nonnull
        @Contract("_, _ -> new")
        public static SuccessChances manualOnly(double min, double max) {
            return new SuccessChances(min, max, -1, -1);
        }
        
        @Nonnull
        @Contract("_, _ -> new")
        public static SuccessChances automaticOnly(double min, double max) {
            return new SuccessChances(-1, -1, min, max);
        }
        
        @Nonnull
        @Contract("_, _ -> new")
        public static SuccessChances bothSame(double min, double max) {
            return new SuccessChances(min, max, min, max);
        }
    }
}
