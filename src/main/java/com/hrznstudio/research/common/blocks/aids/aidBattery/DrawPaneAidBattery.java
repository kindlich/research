package com.hrznstudio.research.common.blocks.aids.aidBattery;

import com.hrznstudio.research.common.gui.DrawPane;

public class DrawPaneAidBattery extends DrawPane {

    private static double progress = 0.0D;
    private static boolean countingUp = true;

    private DrawPane name;
    private DrawPane body;
    private int heightUnit;

    public DrawPaneAidBattery(DrawPane parent) {
        super(parent);
    }

    @Override
    public void init() {
        heightUnit = height / 5;

        name = getSubPane(0, 0, width, heightUnit);
        body = getSubPane(0, heightUnit, width, heightUnit * 4);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (name == null || body == null)
            return;

        name.drawBorderAndReturnInnerPane(0xff000000, 1, 1).drawLocalizedText(0xff000000, "research.aids.battery.name");

        if(progress <= 0)
            countingUp = true;
        if(progress >= 1)
            countingUp = false;

        progress += countingUp ? 0.01D : -0.01D;

        final DrawPane subPane = body.getSubPane(0, 0, width, heightUnit);
        subPane.drawProgressBar(progress);
        subPane.innerPane(2).drawText(0xffffffff, String.format("%2.2f%%", progress * 100));
    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {

    }

    @Override
    public void handleClick(int mouseX, int mouseY, int mouseButton) {

    }


}
