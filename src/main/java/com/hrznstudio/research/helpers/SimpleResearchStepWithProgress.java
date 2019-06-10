package com.hrznstudio.research.helpers;

import com.hrznstudio.research.api.gui.CanvasConstructors;
import com.hrznstudio.research.api.gui.CanvasResearchStep;
import com.hrznstudio.research.api.gui.SharedCanvas;
import com.hrznstudio.research.api.place.IResearchPlace;
import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.player.ResearchProgress;
import com.hrznstudio.research.api.research.IResearchStep;
import com.hrznstudio.research.api.research.IResearchStepProgress;
import javafx.util.Pair;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.function.BiFunction;

public class SimpleResearchStepWithProgress implements IResearchStep {
    private final BiFunction<IResearchStep, Pair<ResearchProgress, IResearchPlace>, IResearchStepProgress> stepFactory;
    private ResourceLocation id;

    @Contract(pure = true)
    public SimpleResearchStepWithProgress(ResourceLocation id, BiFunction<IResearchStep, Pair<ResearchProgress, IResearchPlace>, IResearchStepProgress> stepFactory) {
        this.id = id;
        this.stepFactory = stepFactory;
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public void attachCanvas(SharedCanvas parentCanvas) {
        //parentCanvas.getSubCanvas(0, 0, parentCanvas.getWidth(), parentCanvas.getHeight() / parentCanvas.getExpectedCanvasCount(), CanvasConstructors.getText(this.id.toString(), 0xff000000));
        parentCanvas.getSubCanvas(0, 0, parentCanvas.getWidth(), parentCanvas.getHeight() / parentCanvas.getExpectedCanvasCount(), CanvasConstructors.getConstructorTypes(CanvasResearchStep.class, new Object[]{this}, IResearchStep.class));
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
        System.out.println("Started " + getId());
        return stepFactory.apply(this, new Pair<>(playerProgress, place));
    }

    @Override
    public void onCompleted(ResearchProgress playerProgress, IResearchPlace place) {
        System.out.println("Completed " + getId());
    }
}
