package com.hrznstudio.research.helpers;

import com.hrznstudio.research.api.gui.CanvasConstructors;
import com.hrznstudio.research.api.gui.CanvasResearchStep;
import com.hrznstudio.research.api.gui.SharedCanvas;
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
        System.out.println(playerProgress.getPlayerProgress().getPlayer() + " started " + getId());
        return null;
    }

    @Override
    public void onCompleted(ResearchProgress playerProgress, IResearchPlace place) {
        playerProgress.tryCompleteCurrentStep(place);
        System.out.println(playerProgress.getPlayerProgress().getPlayer() + " completed " + getId());
    }
}
