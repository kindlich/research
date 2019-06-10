package com.hrznstudio.research.api.gui;

import net.minecraft.util.ResourceLocation;

public class CanvasImage extends Canvas {
    private final ResourceLocation image;

    protected CanvasImage(Canvas parent, double width, double height, ResourceLocation image) {
        super(parent, width, height);
        this.image = image;
    }

    @Override
    protected void drawContent(int mouseX, int mouseY) {

    }

    @Override
    protected void drawBackgroundContent(int mouseX, int mouseY) {
        this.renderer.drawTexture(getAbsX(), getAbsY(), getWidth(), getHeight(), image);
    }

    @Override
    protected void initContent() {

    }
}
