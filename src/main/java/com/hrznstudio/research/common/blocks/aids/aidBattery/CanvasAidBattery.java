package com.hrznstudio.research.common.blocks.aids.aidBattery;

import com.hrznstudio.research.api.gui.Canvas;

public class CanvasAidBattery extends Canvas {

    private boolean countingUp;
    private double progress;


    protected CanvasAidBattery(Canvas parent, double width, double height) {
        super(parent, width, height);
    }

    @Override
    protected void initContent() {

    }

    @Override
    protected void drawContent(int mouseX, int mouseY) {

    }

    @Override
    protected void drawBackgroundContent(int mouseX, int mouseY) {
        renderer.drawRect(getAbsX(), getAbsY(), getWidth(), getHeight(), 0xcf000000);
        renderer.drawRect(getAbsX() + 1, getAbsY() + 1, (int) ((getWidth() - 2) * progress), getHeight() - 2, 0xff00bbb0);
        renderer.drawText(getAbsX() + 1, getAbsY() + 10, getWidth() - 2, getHeight() - 20, 0xffffffff, String.format("%2.2f%%", progress * 100.0D));

        if(progress <= 0)
            countingUp = true;
        if(progress >= 1)
            countingUp = false;

        progress += countingUp ? 0.01D : -0.01D;
    }
}
