package com.hrznstudio.research.common.gui;

public abstract class DrawPaneButton extends DrawPane {


    private final String text;
    private final int textColor;
    private final int borderColor;
    private final int borderSize;
    private final int borderOffset;

    public DrawPaneButton(DrawPane other, String text, int textColor, int borderColor, int borderSize, int borderOffset) {
        super(other);
        this.text = text;
        this.textColor = textColor;
        this.borderColor = borderColor;
        this.borderSize = borderSize;
        this.borderOffset = borderOffset;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        drawBorderAndReturnInnerPane(borderColor, borderSize, borderOffset).drawText(textColor, text);
    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {

    }

    @Override
    public void init() {

    }

    @Override
    public void tearDown() {

    }
}
