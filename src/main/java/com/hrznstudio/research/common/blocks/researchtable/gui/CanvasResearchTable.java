package com.hrznstudio.research.common.blocks.researchtable.gui;

import com.hrznstudio.research.ResearchMod;
import com.hrznstudio.research.api.gui.Canvas;
import com.hrznstudio.research.api.gui.CanvasConstructors;
import com.hrznstudio.research.api.gui.CanvasMouse;
import com.hrznstudio.research.api.place.IResearchPlace;
import com.hrznstudio.research.common.blocks.researchtable.TileResearchTable;
import com.hrznstudio.research.common.gui.Renderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class CanvasResearchTable extends Canvas {
    public static final ResourceLocation backgroundTexture = new ResourceLocation(ResearchMod.MODID, "textures/gui/container/research_table.png");
    private final TileResearchTable researchTable;
    private final EntityPlayer player;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private Canvas researchList, researchSteps, researchAids, researchTools, mouseHolder;

    public CanvasResearchTable(Renderer renderer, double absoluteX, double absoluteY, double width, double height, TileResearchTable researchTable, EntityPlayer player, IResearchPlace place) {
        super(renderer, place, absoluteX, absoluteY, width, height);
        this.researchTable = researchTable;
        this.player = player;
        //this.toolSlots = toolSlots;
        //this.createChildren(researchTable, player);
        this.mouseHolder = new CanvasMouse(renderer, place, 16, 16, researchTable.getToolSlots());
    }

    @Override
    protected void drawContent(int mouseX, int mouseY) {
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        super.draw(mouseX, mouseY);
        mouseHolder.draw(mouseX, mouseY);
    }

    @Override
    protected void drawBackgroundContent(int mouseX, int mouseY) {
        renderer.drawTexture(getAbsX(), getAbsY(), getWidth(), getHeight(), backgroundTexture);
    }

    @Override
    protected void initContent() {
        final double unitLeft = width / 25.0D;
        final double unitDown = height / 10.0D;

        {
            //Tools
            final double offSet = unitLeft * 3;
            final double setWidth = width - 2 * offSet;
            researchTools = getSubCanvas(offSet, unitDown, setWidth, unitDown, CanvasConstructors.getConstructor(CanvasResearchTools.class, researchTable.getToolSlots()));
        }


        final double panelHeights = unitDown * 5;
        final double panelY = 2 * unitDown + 10;
        {
            //Aids
            final double setWidth = unitLeft * 4;
            researchAids = getSubCanvas(unitLeft, panelY, setWidth, panelHeights, CanvasConstructors.getConstructor(CanvasResearchAids.class));
        }

        {
            //Steps
            final double setWidth = unitLeft * 13;
            final double setX = 6 * unitLeft;
            researchSteps = getSubCanvas(setX, panelY, setWidth, panelHeights, CanvasConstructors.getConstructorTypes(CanvasResearchStepList.class, new Object[]{researchTable, player}, TileResearchTable.class, EntityPlayer.class));
        }

        {
            //List
            final double setWidth = unitLeft * 4;
            final double setX = 20 * unitLeft;
            researchList = getSubCanvas(setX, panelY, setWidth, panelHeights, CanvasConstructors.getConstructorTypes(CanvasResearchList.class, new Object[]{researchTable, player}, TileResearchTable.class, EntityPlayer.class));
        }
    }
}
