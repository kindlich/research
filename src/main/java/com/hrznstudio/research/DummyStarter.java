package com.hrznstudio.research;

import com.hrznstudio.research.api.place.IResearchPlace;
import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.player.ResearchProgress;
import com.hrznstudio.research.api.research.IResearch;
import com.hrznstudio.research.api.research.IResearchStep;
import com.hrznstudio.research.helpers.LinearResearch;
import com.hrznstudio.research.helpers.SimpleResearchStep;
import com.hrznstudio.research.helpers.ResearchStepProgressSineMiniGame;
import com.hrznstudio.research.helpers.SimpleResearchStepWithProgress;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DummyStarter {

    public static void main(String[] args) {
        final ResourceLocation researchId = new ResourceLocation("research", "id1");
        final ResourceLocation table = new ResourceLocation("research", "place/table");

        final List<IResearchStep> steps = new ArrayList<>();

        steps.add(new SimpleResearchStep(new ResourceLocation("research", "step1")));
        steps.add(new SimpleResearchStep(new ResourceLocation("research", "step2")));
        steps.add(new SimpleResearchStep(new ResourceLocation("research", "step3")));
        steps.add(new SimpleResearchStepWithProgress(new ResourceLocation("research", "step4"), (s, p) -> new ResearchStepProgressSineMiniGame(s)));


        final IResearch research = new LinearResearch(researchId, table, steps);
        final IResearchPlace place = null;
        final EntityPlayer player = null;


        final PlayerProgress progress = APIMethods.getProgress(player);
        final ResearchProgress progressFor = progress.getProgressFor(research);

        System.out.println("Research: " + research.getId());

        Collection<IResearchStep> availableSteps;
        while (!(availableSteps = progressFor.getAvailableSteps()).isEmpty()) {
            System.out.println("Steps available: " + availableSteps.size());

            final IResearchStep next = availableSteps.iterator().next();
            if(!next.canBeStarted(progress, place)) {
                System.out.println("Cannot start!");
                continue;
            }

            System.out.println("Starting " + next.getId());
            progressFor.startStep(next, place);

            if(progressFor.hasCurrentStep()) {
                System.out.println("Step had additional content");
                while (!progressFor.tryCompleteCurrentStep(place)) {
                    System.out.println("Could not completed current step, trying again");
                }
            }
            System.out.println();
        }
    }
}
