package com.hrznstudio.research.api.gui;

import com.hrznstudio.research.ResearchMod;
import com.hrznstudio.research.common.blocks.researchtable.ItemStackHandlerResearchTable;
import com.hrznstudio.research.common.gui.Renderer;
import net.minecraft.util.ResourceLocation;

public class CanvasResearchTable extends Canvas {
    public static final ResourceLocation backgroundTexture = new ResourceLocation(ResearchMod.MODID, "textures/gui/container/research_table.png");

    @SuppressWarnings("FieldCanBeLocal")
    private Canvas researchList, researchSteps, researchAids, researchTools, mouseHolder;

    public CanvasResearchTable(Renderer renderer, ItemStackHandlerResearchTable toolSlots, double absoluteX, double absoluteY, double width, double height) {
        super(renderer, absoluteX, absoluteY, width, height);
        //getInnerCanvas(10.0D, CanvasConstructors.getFilled(0xff00ffff))
        //        .getInnerCanvas(10.0D, CanvasConstructors.getFilled(0xffffffff));
        this.createChildren(toolSlots);
        this.mouseHolder = new CanvasMouse(renderer, 16, 16, toolSlots);

    }

    @Override
    void drawContent(int mouseX, int mouseY) {
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        super.draw(mouseX, mouseY);
        mouseHolder.draw(mouseX, mouseY);
    }

    @Override
    void drawBackgroundContent(int mouseX, int mouseY) {
//        renderer.drawRect(getAbsX(), getAbsY(), getWidth(), getHeight(), 0xffff0000);

        renderer.drawTexture(getAbsX(), getAbsY(), getWidth(), getHeight(), backgroundTexture);
    }

    private void createChildren(ItemStackHandlerResearchTable toolSlots) {
        final double unitLeft = width / 25.0D;
        final double unitDown = height / 10.0D;

        {
            //Tools
            final double offSet = unitLeft * 3;
            final double setWidth = width - 2 * offSet;
            researchTools = getSubCanvas(offSet, unitDown, setWidth, unitDown, CanvasConstructors.getConstructor(CanvasResearchTools.class, toolSlots));
        }


        final double panelHeights = unitDown * 5;
        final double panelY = 2 * unitDown + 10;
        {
            //Aids
            final double setWidth = unitLeft * 4;
            researchAids = getSubCanvas(unitLeft, panelY, setWidth, panelHeights, CanvasConstructors.getConstructor(CanvasResearchAids.class, toolSlots));
        }

        {
            //Steps
            final double setWidth = unitLeft * 13;
            final double setX = 6 * unitLeft;
            //researchSteps = getSubCanvas(setX, panelY, setWidth, panelHeights, CanvasConstructors.getConstructor(CanvasResearchTools.class, toolSlots));
            researchSteps = getSubCanvas(setX, panelY, setWidth, panelHeights, CanvasConstructors.getFilled(0xffabcdef));
        }

        {
            //List
            final double setWidth = unitLeft * 4;
            final double setX = 20 * unitLeft;
            researchList = getSubCanvas(setX, panelY, setWidth, panelHeights, CanvasConstructors.getConstructor(CanvasResearchTools.class, toolSlots));
        }
    }

    @Override
    public void init() {
        super.init();
    }
}
