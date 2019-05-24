package com.hrznstudio.research.helpers;

import com.hrznstudio.research.api.place.IResearchPlace;
import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.player.ResearchProgress;
import com.hrznstudio.research.api.research.IResearchStep;
import com.hrznstudio.research.api.research.IResearchStepProgress;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nullable;

public class SimpleResearchStep implements IResearchStep {
    private final ResourceLocation id;

    @Contract(pure = true)
    public SimpleResearchStep(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    //@Override
    //public Rectangle drawInfo(DrawTool drawTool, PlayerProgress playerProgress, Rectangle maximumSize) {
    //    drawTool.drawLocalizedText(getId().getPath());
    //    return new Rectangle(maximumSize.x, maximumSize.y, maximumSize.width, 14);
    //}
//
    //@Override
    //public Rectangle drawContent(DrawTool drawTool, PlayerProgress playerProgress, Rectangle maximumSize) {
    //    drawTool.drawLocalizedText(getId().getPath());
    //    return new Rectangle(maximumSize.x, maximumSize.y, maximumSize.width, 14);
    //}

    @Override
    public boolean canBeStarted(PlayerProgress progress, IResearchPlace place) {
        return true;
    }

    @Nullable
    @Override
    public IResearchStepProgress onStarted(ResearchProgress playerProgress, IResearchPlace place) {
        System.out.println(playerProgress.getPlayerProgress().getPlayer() + " started " + getId());
        return null;
    }

    @Override
    public void onCompleted(ResearchProgress playerProgress, IResearchPlace place) {
        playerProgress.tryCompleteCurrentStep(place);
        System.out.println(playerProgress.getPlayerProgress().getPlayer() + " completed " + getId());
    }
}
