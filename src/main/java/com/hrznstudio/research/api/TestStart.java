package com.hrznstudio.research.api;

import com.hrznstudio.research.api.impl.ResearchBuilder;
import com.hrznstudio.research.api.research.IResearchStep;
import com.hrznstudio.research.api.research.IResearchAid;
import com.hrznstudio.research.api.research.IResearchTool;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TestStart {
    
    public static void main(String[] args) {
        
        
        final IResearchStep a = new MockResearchStep(new ResourceLocation("minecraft", "a"));
        final IResearchStep b = new MockResearchStep(new ResourceLocation("minecraft", "b"));
        final IResearchStep c = new MockResearchStep(new ResourceLocation("minecraft", "c"));
        final IResearchStep d = new MockResearchStep(new ResourceLocation("minecraft", "d"));
        final IResearchStep e = new MockResearchStep(new ResourceLocation("minecraft", "e"));
        final IResearchStep f = new MockResearchStep(new ResourceLocation("minecraft", "f"));
        
        
        System.out.println(new ResearchBuilder(new ResourceLocation("minecraft", "research_a"), a).addChoice(b, c, d)
                                   .addResearchStep(e)
                                   .toResearch());
        System.out.print("\n\n\n");
        System.out.println(new ResearchBuilder(new ResourceLocation("minecraft", "research_b"), a).addOptional(b, c, d)
                                   .addResearchStep(e)
                                   .toResearch());
        System.out.print("\n\n\n");
        
        final List<ResearchBuilder> builders = new ResearchBuilder(new ResourceLocation("minecraft", "research_c"), a).splitPaths(b, c);
        builders.get(0).addResearchStep(d);
        builders.get(1).addResearchStep(e);
        
        System.out.println(ResearchBuilder.mergePaths(builders.toArray(new ResearchBuilder[]{}))
                                   .addResearchStep(f)
                                   .toResearch());
        
        
    }
    
    
    private static final class MockResearchStep implements IResearchStep {
        
        private final ResourceLocation id;
        
        private MockResearchStep(ResourceLocation id) {
            this.id = id;
        }
        
        @Contract(pure = true)
        @Nonnull
        @Override
        public ResourceLocation getId() {
            return id;
        }
        
        @Nonnull
        @Contract(pure = true)
        @Override
        public Map<IResearchTool, Integer> getRequiredToolsAndLevels() {
            return Collections.emptyMap();
        }
        
        @Contract(pure = true)
        @Nonnull
        @Override
        public Map<IResearchAid, Object> getRequiredAidsAndData() {
            return Collections.emptyMap();
        }
        
        @Contract(pure = true)
        @Override
        public double getMinSuccess() {
            return 0.50;
        }
    }
}
