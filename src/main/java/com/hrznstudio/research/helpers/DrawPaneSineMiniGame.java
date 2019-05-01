package com.hrznstudio.research.helpers;

import com.hrznstudio.research.api.player.ResearchProgress;
import com.hrznstudio.research.common.gui.DrawPane;
import com.hrznstudio.research.common.gui.DrawPaneButton;
import net.minecraft.client.resources.I18n;

public class DrawPaneSineMiniGame extends DrawPane {
    private final ResearchProgress progress;
    private final ResearchStepProgressSineMiniGame stepProgress;
    private double current = 0.0D;
    private double userOffset;
    private DrawPane buttonPlus;
    private DrawPane buttonMinus;
    private DrawPane buttonConfirm;


    public DrawPaneSineMiniGame(DrawPane other, ResearchProgress progress, ResearchStepProgressSineMiniGame stepProgress) {
        super(other);
        this.progress = progress;
        this.userOffset = stepProgress.userOffset;
        this.stepProgress = stepProgress;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        final DrawPane drawPane = drawBorderAndReturnInnerPane(0xff000000, 1, 1);
        drawPane.drawSine(current, current + 2 * Math.PI, 0xffff0000, 2);
        final double withOffset = current + userOffset;
        drawPane.drawSine(withOffset, withOffset + 2 * Math.PI, 0xff00ff00, 2);
        current += 0.05;

        //we don't need the extra calculation step:
        //Tau = 2 * Math.Pi = 6.283185307179586
        if (current >= 6.283185307179586)
            current -= 6.283185307179586;

        buttonPlus.draw(mouseX, mouseY);
        buttonMinus.draw(mouseX, mouseY);

        if (Math.abs(userOffset) <= 0.5D)
            buttonConfirm.draw(mouseX, mouseY);
    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {

    }

    @Override
    public void init() {
        final int sY = 4 * (height / 6);

        buttonPlus = new DrawPaneButton(getSubPane(82, sY, 16, 16), "+", 0xff000000, 0xff000000, 1, 3) {
            @Override
            public void handleClick(int mouseX, int mouseY, int mouseButton) {
                userOffset = stepProgress.addToOffset();
            }
        };

        buttonMinus = new DrawPaneButton(getSubPane(16, sY, 16, 16), "-", 0xff000000, 0xff000000, 1, 3) {
            @Override
            public void handleClick(int mouseX, int mouseY, int mouseButton) {
                userOffset = stepProgress.removeFromOffset();
            }
        };

        buttonConfirm = new DrawPaneButton(getSubPane(32, sY, 50, 16), I18n.format("research.minigame.sine.buttons.confirm"), 0xff000000, 0xff000000, 1, 1) {
            @Override
            public void handleClick(int mouseX, int mouseY, int mouseButton) {
                progress.tryCompleteCurrentStep(null);
            }
        };
    }

    @Override
    public void tearDown() {

    }

    @Override
    public void handleClick(int mouseX, int mouseY, int mouseButton) {
        if (buttonPlus.containsPoint(mouseX, mouseY))
            buttonPlus.handleClick(mouseX, mouseY, mouseButton);

        if (buttonMinus.containsPoint(mouseX, mouseY))
            buttonMinus.handleClick(mouseX, mouseY, mouseButton);

        if (buttonConfirm.containsPoint(mouseX, mouseY) && Math.abs(userOffset) <= 0.5D)
            buttonConfirm.handleClick(mouseX, mouseY, mouseButton);
    }
}
