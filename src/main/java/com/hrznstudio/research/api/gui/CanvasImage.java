package com.hrznstudio.research.api.gui;

import net.minecraft.util.ResourceLocation;

public class CanvasImage extends Canvas {
    private final ResourceLocation image;

    protected CanvasImage(Canvas parent, double offsetX, double offsetY, double width, double height, ResourceLocation image) {
        super(parent, offsetX, offsetY, width, height);
        this.image = image;
    }

    @Override
    void drawContent(int mouseX, int mouseY) {

    }

    @Override
    void drawBackgroundContent(int mouseX, int mouseY) {
        this.renderer.drawTexture(getAbsX(), getAbsY(), getWidth(), getHeight(), image);
    }
}
