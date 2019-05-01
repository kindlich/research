package com.hrznstudio.research.common.gui;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
class DrawPaneBasic extends DrawPane {


    public DrawPaneBasic(Renderer renderer) {
        super(renderer);
    }

    public DrawPaneBasic(DrawPane parent) {
        super(parent);
    }

    @Override
    public void draw(int mouseX, int mouseY) {

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

    @Override
    public void handleClick(int mouseX, int mouseY, int mouseButton) {

    }
}
