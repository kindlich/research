package com.hrznstudio.research.common.blocks.researchtable.gui;

import com.hrznstudio.research.APIMethods;
import com.hrznstudio.research.api.gui.Canvas;
import com.hrznstudio.research.api.gui.CanvasConstructors;
import com.hrznstudio.research.api.gui.SharedCanvas;
import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.player.ResearchProgress;
import com.hrznstudio.research.api.research.IResearchStep;
import com.hrznstudio.research.common.blocks.researchtable.TileResearchTable;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Collection;
import java.util.function.Consumer;

public class CanvasResearchStepList extends Canvas {

    private final TileResearchTable table;
    private final PlayerProgress progress;
    private final Consumer<Object> researchChangeListener;

    public CanvasResearchStepList(Canvas parent, double width, double height, TileResearchTable table, EntityPlayer player) {
        super(parent, width, height);
        this.table = table;
        progress = APIMethods.getProgress(player);
        researchChangeListener = iResearch -> init();

        progress.registerResearchChangeListener(this.researchChangeListener);
        progress.registerResearchStepChangeListener(this.researchChangeListener);
    }

    @Override
    protected void drawContent(int mouseX, int mouseY) {

    }

    @Override
    protected void drawBackgroundContent(int mouseX, int mouseY) {
        this.renderer.drawRect(getAbsX(), getAbsY(), getWidth(), getHeight(), 0xddabcdef);
    }

    @Override
    protected void initContent() {
        this.clearChildren();

        Canvas canvasContent = getSubCanvas(0, 0, width, height, CanvasConstructors.getBorder(1, 0xff000000));

        if (!progress.hasSelectedResearch())
            return;

        final ResearchProgress researchProgress = progress.getProgressFor(progress.getSelectedResearch());

        //We already selected a research
        if (researchProgress.hasCurrentStep()) {
            researchProgress.getCurrentStep().attachCanvas(canvasContent);
            return;
        }


        final Collection<IResearchStep> availableSteps = researchProgress.getAvailableSteps();
        for (IResearchStep availableStep : availableSteps) {
            availableStep.attachCanvas(canvasContent.getInnerCanvas(1, CanvasConstructors.getConstructor(SharedCanvas.class, 3.0D, availableSteps.size())));
        }
    }

    @Override
    public void tearDown() {
        super.tearDown();
        progress.deRegisterResearchChangeListener(researchChangeListener);
        progress.deRegisterResearchStepChangeListener(researchChangeListener);
    }
}
