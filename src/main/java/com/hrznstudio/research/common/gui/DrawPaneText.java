package com.hrznstudio.research.common.gui;

public class DrawPaneText extends DrawPaneWrapper<DrawPane> {
    private final int color;
    private final String localizationKey;
    private final Object[] parameters;

    public DrawPaneText(DrawPane other, int color, String localizationKey, Object... parameters) {
        super(other);
        this.color = color;
        this.localizationKey = localizationKey;
        this.parameters = parameters;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        super.draw(mouseX, mouseY);
        drawLocalizedText(color, localizationKey, parameters);
    }
}
