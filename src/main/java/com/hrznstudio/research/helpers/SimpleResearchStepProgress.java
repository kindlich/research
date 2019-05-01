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
        return false;
        //return random.nextBoolean();
    }

    @Override
    public IResearchStep getStep() {
        return step;
    }

    private double current = 0.0D;

    @Override
    public DrawPane getDrawPane(DrawPane parent) {
        return new DrawPaneWrapper<DrawPane>(parent) {
            @Override
            public void draw(int mouseX, int mouseY) {
                super.draw(mouseX, mouseY);
                final DrawPane drawPane = drawBorderAndReturnInnerPane(0xff000000, 1, 1);
                drawPane.drawText(0xff000000, "Hello World");
                drawPane.drawSine(current, current + 2 * Math.PI, 0xffff0000, 2);
                current += 0.05;
            }
        };
    }


}
