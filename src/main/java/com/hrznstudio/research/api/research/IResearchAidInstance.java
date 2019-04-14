package com.hrznstudio.research.api.research;

import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnull;
import java.util.Objects;

public interface IResearchAidInstance {
    
    IResearchAid getAid();
    
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
    void draw(Minecraft mc, int x, int y, int xSize, int ySize);
    
    
    /**
     * Checks if the aid is enough to complete the step
     */
    boolean matches(IResearchStepInstance step);
    
    
    /**
     * Checks if the aid provides a minigame for the given block
     *
     * @return {@code null} -> no minigame,
     * {@code true} -> mandatory,
     * {@code false} -> optional
     */
    Boolean providesMiniGame(IResearchStepInstance step);
    
    /**
     * Do the research step and return the score (usually between 0 and 1)
     */
    double doStep(IResearchStepInstance step, boolean doMiniGame, Minecraft mc);
    
    SuccessChances getChancesFor(IResearchStepInstance step);
    
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
        
        @Contract(value = "null -> false", pure = true)
        @Override
        public boolean equals(Object o) {
            if(this == o)
                return true;
            if(!(o instanceof SuccessChances))
                return false;
            SuccessChances that = (SuccessChances) o;
            return Double.compare(that.minManual, minManual) == 0 && Double.compare(that.maxManual, maxManual) == 0 && Double.compare(that.minAutomatic, minAutomatic) == 0 && Double.compare(that.maxAutomatic, maxAutomatic) == 0;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(minManual, maxManual, minAutomatic, maxAutomatic);
        }
    }
}
