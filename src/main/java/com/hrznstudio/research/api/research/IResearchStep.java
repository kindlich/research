package com.hrznstudio.research.api.research;

import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnull;
import java.util.Map;

public interface IResearchStep {
    
    @Nonnull
    @Contract(pure = true)
    ResourceLocation getId();
    
    @Nonnull
    @Contract(pure = true)
    Map<IResearchTool, Integer> getRequiredToolsAndLevels();
    
    @Nonnull
    @Contract(pure = true)
    Map<IResearchAid, Object> getRequiredAidsAndData();
    
    /**
     * How much completion the player must gather to complete this step
     * Will be shown in the table (thought the actual logic might differ)
     */
    @Contract(pure = true)
    double getMinSuccess();
    
    /**
     * Decides if the player can continue the research or not
     */
    @Nonnull
    @Contract(pure = true)
    default Result getResult(double completion) {
        return getMinSuccess() > completion ? Result.goTo(this) : Result.CONTINUE;
    }
    
    /**
     * Can the research be completed multiple times (e.g. when multiple researches depend on it)
     */
    @Contract(pure = true)
    default boolean canBeCompletedMultipleTimes() {
        return true;
    }
    
    final class Result {
        
        public static final Result CONTINUE = new Result();
        public static final Result ABORT_RESEARCH = new Result();
        
        private final IResearchStep nextStep;
    
        @Contract(pure = true)
        public IResearchStep getNextStep() {
            return nextStep;
        }
    
        private Result() {
            this(null);
        }
        
        private Result(IResearchStep nextStep) {
            this.nextStep = nextStep;
        }
    
    
        /**
         * Allows you to jump to another research step in the line
         * Just make sure that this step is in the same research as otherwise the whole research will be aborted.
         */
        @Nonnull
        @Contract("_ -> new")
        public static Result goTo(IResearchStep iResearchStep) {
            return new Result(iResearchStep);
        }
    }
}
