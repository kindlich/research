package com.hrznstudio.research.common.blocks.researchtable;

import com.hrznstudio.research.ResearchMod;
import com.hrznstudio.research.common.gui.DrawPane;
import com.hrznstudio.research.common.gui.Renderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DrawPaneResearchTable extends DrawPane {
    public final ResourceLocation backgroundTexture;
    public final DrawPaneResearchList researchList;
    public final DrawPaneResearchSteps researchSteps;
    public final DrawPaneResearchAids researchAids;
    public final DrawPaneResearchTools researchTools;

    @Contract(pure = true)
    public DrawPaneResearchTable(Renderer renderer, DrawPaneResearchList researchList, DrawPaneResearchSteps researchSteps, DrawPaneResearchAids researchAids, DrawPaneResearchTools researchTools) {
        super(renderer);
        this.researchList = researchList;
        this.researchSteps = researchSteps;
        this.researchAids = researchAids;
        this.researchTools = researchTools;
        this.backgroundTexture = new ResourceLocation(ResearchMod.MODID, "textures/gui/container/research_table.png");
    }

    @Contract(pure = true)
    public DrawPaneResearchTable(Renderer guiResearchTable, DrawPaneResearchList researchList, DrawPaneResearchSteps researchSteps, DrawPaneResearchAids researchAids, DrawPaneResearchTools researchTools, ResourceLocation backgroundTexture) {
        super(guiResearchTable);
        this.researchList = researchList;
        this.researchSteps = researchSteps;
        this.researchAids = researchAids;
        this.researchTools = researchTools;
        this.backgroundTexture = backgroundTexture;
    }


    @Override
    public void draw(int mouseX, int mouseY) {

        this.researchList.draw(mouseX, mouseY);
        this.researchSteps.draw(mouseX, mouseY);
        this.researchAids.draw(mouseX, mouseY);
        this.researchTools.draw(mouseX, mouseY);
    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {
        renderer.drawTexture(startX, startY, width, height, backgroundTexture);

        this.researchList.drawBackground(mouseX, mouseY);
        this.researchSteps.drawBackground(mouseX, mouseY);
        this.researchAids.drawBackground(mouseX, mouseY);
        this.researchTools.drawBackground(mouseX, mouseY);
    }

    @Override
    public void init() {
        this.researchList.init();
        this.researchSteps.init();
        this.researchAids.init();
        this.researchTools.init();
    }

    @Override
    public void tearDown() {
        this.researchList.tearDown();
        this.researchSteps.tearDown();
        this.researchAids.tearDown();
        this.researchTools.tearDown();
    }

    @Override
    public void handleClick(int mouseX, int mouseY, int mouseButton) {
        if (this.researchList.containsPoint(mouseX, mouseY))
            this.researchList.handleClick(mouseX, mouseY, mouseButton);

        if (this.researchSteps.containsPoint(mouseX, mouseY))
            this.researchSteps.handleClick(mouseX, mouseY, mouseButton);

        if (this.researchAids.containsPoint(mouseX, mouseY))
            this.researchAids.handleClick(mouseX, mouseY, mouseButton);

        if (this.researchTools.containsPoint(mouseX, mouseY))
            this.researchTools.handleClick(mouseX, mouseY, mouseButton);
    }


    @Override
    public boolean resize(int startX, int startY, int width, int height) {
        if (!super.resize(startX, startY, width, height))
            return false;

        final double unitLeft = width / 25.0D;
        final double unitDown = height / 10.0D;

        {
            //Tools
            final int offSet = (int) (unitLeft * 3);
            final int setWidth = width - 2 * offSet;
            final int setX = startX + offSet;
            final int setY = (int) (startY + unitDown);
            researchTools.resize(setX, setY, setWidth, (int) unitDown);
        }


        final int panelHeights = (int) (unitDown * 5);
        final int panelY = (int) (startY + 2 * unitDown);
        {
            //Aids
            final int setWidth = (int) (unitLeft * 4);
            researchAids.resize((int) (startX + unitLeft), startY + panelY, setWidth, panelHeights);
        }

        {
            //Steps
            final int setWidth = (int) (unitLeft * 13);
            final int setX = (int) (startX + 6 * unitLeft);
            researchSteps.resize(setX, startY + panelY, setWidth, panelHeights);
        }

        {
            //List
            final int setWidth = (int) (unitLeft * 4);
            final int setX = (int) (startX + 20 * unitLeft);
            researchList.resize(setX, startY + panelY, setWidth, panelHeights);
        }

        return true;
    }

    @Override
    public void onTableUpdated() {
        super.onTableUpdated();
        researchList.onTableUpdated();
        researchSteps.onTableUpdated();
        researchAids.onTableUpdated();
        researchTools.onTableUpdated();
    }
}
