package com.hrznstudio.research.api.research;

import com.hrznstudio.research.api.gui.Canvas;
import com.hrznstudio.research.api.gui.CanvasConstructor;
import com.hrznstudio.research.api.gui.SharedCanvas;
import com.hrznstudio.research.api.place.IResearchPlace;
import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.player.ResearchProgress;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public interface IResearchStep {

    //Properties
    boolean isRepeatable();

    default boolean isGlobal() {
        return false;
    }

    ResourceLocation getId();


    ////GUI
    //Rectangle drawInfo(DrawTool drawTool, PlayerProgress playerProgress, Rectangle maximumSize);
//
    //Rectangle drawContent(DrawTool drawTool, PlayerProgress playerProgress, Rectangle maximumSize);

    /**
     * Return a canvas that should be added to the parent canvas.
     * Return null if the space would not fit.
     *
     * @param parentCanvas The place available for the info
     */
    void attachCanvas(SharedCanvas parentCanvas);


    //Progressing

    //Not yet started
    boolean canBeStarted(PlayerProgress progress, IResearchPlace place);

    //Events

    //Return null to instantly complete the research
    @Nullable
    IResearchStepProgress onStarted(ResearchProgress playerProgress, IResearchPlace place);

    void onCompleted(ResearchProgress playerProgress, IResearchPlace place);
}
