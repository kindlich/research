package com.hrznstudio.research.helpers;

import com.hrznstudio.research.api.gui.Canvas;
import com.hrznstudio.research.api.gui.CanvasArrow;
import com.hrznstudio.research.api.gui.CanvasConstructors;
import com.hrznstudio.research.api.player.PlayerProgress;

public class CanvasSineMiniGame extends Canvas {

    private final ResearchStepProgressSineMiniGame miniGame;

    public CanvasSineMiniGame(Canvas parent, ResearchStepProgressSineMiniGame miniGame) {
        super(parent, parent.getWidth(), parent.getHeight());
        this.miniGame = miniGame;
    }

    @Override
    protected void drawContent(int mouseX, int mouseY) {
    }

    @Override
    protected void drawBackgroundContent(int mouseX, int mouseY) {

    }

    @Override
    protected void initContent() {
        final double heightUnit = this.height / 5.0D;
        getSubCanvas(0, 0, width, heightUnit * 4.0D, CanvasConstructors.getConstructor(SinePart.class, this.miniGame));

        final Canvas buttons = getSubCanvas(0, 4 * heightUnit, width, height);

        final Canvas confirm = new CheckBox(buttons, width / 3, heightUnit);
        confirm.setActive(miniGame.canBeCompleted());

        final CanvasArrow arrowLeft = new CanvasArrow(buttons, width / 3, heightUnit, CanvasArrow.Direction.LEFT) {
            @Override
            public void handleClick(int mouseX, int mouseY, int mouseButton) {
                miniGame.removeFromOffset();
                confirm.setActive(miniGame.canBeCompleted());
            }
        };

        final CanvasArrow arrowRight = new CanvasArrow(buttons, width / 3, heightUnit, CanvasArrow.Direction.RIGHT) {

            @Override
            public void handleClick(int mouseX, int mouseY, int mouseButton) {
                miniGame.addToOffset();
                confirm.setActive(miniGame.canBeCompleted());
            }
        };

        buttons.addChild(arrowLeft, 0, 0);
        buttons.addChild(confirm, width / 3, 0);
        buttons.addChild(arrowRight, 2 * width / 3, 0);

    }

    public static class SinePart extends Canvas {
        private double current = 0.0D;
        private ResearchStepProgressSineMiniGame miniGame;

        public SinePart(Canvas parent, double width, double height, ResearchStepProgressSineMiniGame miniGame) {
            super(parent, width, height);
            this.miniGame = miniGame;
        }

        @Override
        protected void drawContent(int mouseX, int mouseY) {
            renderer.drawSine(this.getAbsX(), this.getAbsY(), this.getWidth(), this.getHeight(), current, current + 2 * Math.PI, 2, 0xffff0000);
            renderer.drawSine(this.getAbsX(), this.getAbsY(), this.getWidth(), this.getHeight(), current + this.miniGame.userOffset, current + 2 * Math.PI + this.miniGame.userOffset, 2, 0xff00ff00);

            current += 0.01D;
        }

        @Override
        protected void drawBackgroundContent(int mouseX, int mouseY) {

        }

        @Override
        protected void initContent() {

        }
    }

    private final class CheckBox extends Canvas {

        protected CheckBox(Canvas parent, double width, double height) {
            super(parent, width, height);
        }

        @Override
        protected void drawContent(int mouseX, int mouseY) {
        }

        @Override
        protected void drawBackgroundContent(int mouseX, int mouseY) {

        }

        @Override
        protected void initContent() {
            getInnerCanvas(1, CanvasConstructors.getText("Confirm", 0xff000000));
        }

        @Override
        public void handleClick(int mouseX, int mouseY, int mouseButton) {
            if(containsAbsolutePoint(mouseX, mouseY)) {
                final PlayerProgress playerProgress = CanvasSineMiniGame.this.place.getPlayerProgress();
                playerProgress.getProgressFor(playerProgress.getSelectedResearch()).tryCompleteCurrentStep(CanvasSineMiniGame.this.place);
            }
        }
    }
}
