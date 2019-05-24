package com.hrznstudio.research.api.gui;

public class CanvasTextSpecial extends CanvasText {

    protected CanvasTextSpecial(Canvas parent, double offsetX, double offsetY, double width, double height, String text, int color, String otherMessage) {
        super(parent, offsetX, offsetY, width, height, text + otherMessage, color);
    }
}
