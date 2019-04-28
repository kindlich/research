package com.hrznstudio.research.api.research;

import com.hrznstudio.research.api.gui.DrawTool;
import com.hrznstudio.research.api.place.IResearchPlace;
import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.player.ResearchProgress;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.awt.*;

public interface IResearchStep {

    //Properties
    boolean isRepeatable();

    default boolean isGlobal() {
        return false;
    }

    ResourceLocation getId();


    //GUI
    Rectangle drawInfo(DrawTool drawTool, PlayerProgress playerProgress, Rectangle maximumSize);

    Rectangle drawContent(DrawTool drawTool, PlayerProgress playerProgress, Rectangle maximumSize);


    //Progressing

    //Not yet started
    boolean canBeStarted(PlayerProgress progress, IResearchPlace place);

    //Events

    //Return null to instantly complete the research
    @Nullable
    IResearchStepProgress onStarted(ResearchProgress playerProgress, IResearchPlace place);

    void onCompleted(ResearchProgress playerProgress, IResearchPlace place);
}
