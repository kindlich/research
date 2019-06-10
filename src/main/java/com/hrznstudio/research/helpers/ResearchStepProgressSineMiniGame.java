package com.hrznstudio.research.helpers;

import com.hrznstudio.research.api.gui.Canvas;
import com.hrznstudio.research.api.gui.CanvasBorder;
import com.hrznstudio.research.api.gui.CanvasConstructors;
import com.hrznstudio.research.api.place.IResearchPlace;
import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.research.IResearchStep;
import com.hrznstudio.research.api.research.IResearchStepProgress;
import org.jetbrains.annotations.Contract;

public class ResearchStepProgressSineMiniGame implements IResearchStepProgress {

    private final IResearchStep step;
    public double userOffset = (Math.random() * 12.566370614359172) - 6.283185307179586D;
    private static final double offsetValue = 0.3D;

    @Contract(pure = true)
    public ResearchStepProgressSineMiniGame(IResearchStep step) {
        this.step = step;
    }

    @Override
    public boolean canBeCompleted(PlayerProgress progress, IResearchPlace place) {
        return canBeCompleted();
    }

    public boolean canBeCompleted() {
        return Math.abs(userOffset) < 0.3D;
    }

    @Override
    public IResearchStep getStep() {
        return step;
    }

    @Override
    public void attachCanvas(Canvas canvasContent) {
        final CanvasBorder innerCanvas = canvasContent.getInnerCanvas(2.0, CanvasConstructors.getBorder(1, 0xff000000));
        final CanvasSineMiniGame game = new CanvasSineMiniGame(innerCanvas, this);
        innerCanvas.addChild(game, 0, 0);
    }


    public double addToOffset() {
        this.userOffset += offsetValue;
        return verifyBoundaries();
    }

    public double removeFromOffset() {
        this.userOffset -= offsetValue;
        return verifyBoundaries();
    }

    private double verifyBoundaries() {
        if (userOffset >= 6.283185307179586D)
            userOffset -= 6.283185307179586D;
        else if (userOffset <= -6.283185307179586D)
            userOffset += 6.283185307179586D;
        return userOffset;
    }
}
