package com.hrznstudio.research.api.gui;

import com.hrznstudio.research.ResearchMod;
import net.minecraft.util.ResourceLocation;

public abstract class CanvasArrow extends Canvas {
    private static final ResourceLocation arrowLeftLocation = new ResourceLocation(ResearchMod.MODID, "textures/gui/container/left.png");
    private static final ResourceLocation arrowRightLocation = new ResourceLocation(ResearchMod.MODID, "textures/gui/container/right.png");
    private static final ResourceLocation arrowUpLocation = new ResourceLocation(ResearchMod.MODID, "textures/gui/container/right.png");
    private static final ResourceLocation arrowDownLocation = new ResourceLocation(ResearchMod.MODID, "textures/gui/container/right.png");
    protected final Direction direction;

    public CanvasArrow(Canvas parent, double width, double height, Direction direction) {
        super(parent, width, height);
        this.direction = direction;
    }

    @Override
    protected void initContent() {

    }

    @Override
    protected void drawContent(int mouseX, int mouseY) {
    }

    @Override
    protected void drawBackgroundContent(int mouseX, int mouseY) {
        final ResourceLocation location;
        switch (direction) {
            case UP:
                location = arrowUpLocation;
                break;
            case DOWN:
                location = arrowDownLocation;
                break;
            case LEFT:
                location = arrowLeftLocation;
                break;
            default:
                location = arrowRightLocation;
                break;
        }

        renderer.drawTexture(this.getAbsX(), this.getAbsY(), this.getWidth(), this.getHeight(), location);
    }

    @Override
    public abstract void handleClick(int mouseX, int mouseY, int mouseButton);

    public enum Direction {UP, DOWN, LEFT, RIGHT}
}

