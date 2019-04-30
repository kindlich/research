package com.hrznstudio.research.helpers;

import com.hrznstudio.research.api.place.IResearchPlace;
import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.research.IResearchStep;
import com.hrznstudio.research.api.research.IResearchStepProgress;
import com.hrznstudio.research.common.gui.DrawPane;
import com.hrznstudio.research.common.gui.DrawPaneWrapper;
import org.jetbrains.annotations.Contract;

import java.util.Random;

public class SimpleResearchStepProgress implements IResearchStepProgress {


    private final IResearchStep step;
    private final Random random;

    @Contract(pure = true)
    public SimpleResearchStepProgress(IResearchStep step) {
        this.step = step;
        random = new Random(1234567890123456789L);
    }

    @Override
    public boolean canBeCompleted(PlayerProgress progress, IResearchPlace place) {
        return random.nextBoolean();
    }

    @Override
    public IResearchStep getStep() {
        return step;
    }

    @Override
    public DrawPane getDrawPane(DrawPane parent) {
        return new DrawPaneWrapper<DrawPane>(parent) {
            @Override
            public void draw(int mouseX, int mouseY) {
                super.draw(mouseX, mouseY);
                drawBorderAndReturnInnerPane(0xff000000, 1, 1).drawText(0xff000000, "Hello World");
            }
        };
    }


}
