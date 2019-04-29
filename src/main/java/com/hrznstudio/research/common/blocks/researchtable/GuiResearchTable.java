package com.hrznstudio.research.common.blocks.researchtable;

import com.hrznstudio.research.APIMethods;
import com.hrznstudio.research.ResearchMod;
import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.player.ResearchProgress;
import com.hrznstudio.research.api.research.IResearch;
import com.hrznstudio.research.api.research.IResearchStep;
import com.hrznstudio.research.helpers.ContainerResearchTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.util.Collection;

public class GuiResearchTable extends GuiContainer {
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(ResearchMod.MODID, "textures/gui/container/research_table.png");
    private static final int rows = 6;
    private final RTDrawTool drawTool;

    private final TileResearchTable table;
    private final PlayerProgress progress;
    private IResearch selectedResearch;

    public GuiResearchTable(TileResearchTable table, @NotNull EntityPlayer player) {
        super(new ContainerResearchTable(table, player.inventory));
        this.table = table;
        this.drawTool = new RTDrawTool(new Rectangle(this.guiLeft, this.guiTop, this.width, this.height), this);
        this.progress = APIMethods.getProgress(player);


    }

    public Minecraft getMC() {
        return this.mc;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.drawWorldBackground(0);

        mc.renderEngine.bindTexture(GUI_TEXTURE);
        drawModalRectWithCustomSizedTexture(16, 16, 0, 0, 427 - 32, 240 - 32, 427 - 32, 240 - 32);
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        mc.renderEngine.bindTexture(GUI_TEXTURE);
        drawModalRectWithCustomSizedTexture(16, 16, 0, 0, 427 - 32, 240 - 32, 427 - 32, 240 - 32);

        drawCenteredString(fontRenderer, "Researches", 362, 80, 0x11ffaa);

        if (this.selectedResearch == null) {
            drawCenteredString(fontRenderer, "Steps", 214, 80, 0x11ffaa);
            drawCenteredString(fontRenderer, "No Research selected", 214, 94, 0x00ffff);
        } else {
            final ResearchProgress researchProgress = this.progress.getProgressFor(this.selectedResearch);
            Rectangle researchSteps = new Rectangle(162, 93, 62, 65);

            if(researchProgress.hasCurrentStep()) {

            } else {
                for (IResearchStep availableStep : researchProgress.getAvailableSteps()) {
                    final Rectangle usedSpace = availableStep.drawInfo(new RTDrawTool(researchSteps, this), progress, researchSteps);
                    researchSteps.setBounds(researchSteps.x + usedSpace.height, researchSteps.y, researchSteps.width, researchSteps.height - usedSpace.height);
                }
                drawCenteredString(fontRenderer, researchProgress.hasCurrentStep() ? "Steps" : "Current Step", 214, 80, 0x11ffaa);
            }

        }

        {
            int i = 0;
            for (IResearch availableResearch : this.progress.getAvailableResearches()) {
                drawCenteredString(mc.fontRenderer, availableResearch.getId().getPath(), 362, 92 + 14 * i++, 0x00ffff);
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);


        if (mouseX > 300) {
            final Collection<IResearch> allResearches = progress.getAvailableResearches();
            if(allResearches.isEmpty())
                return;

            this.selectedResearch = allResearches.iterator().next();
            final Collection<IResearchStep> availableSteps = this.selectedResearch.getAvailableSteps(progress.getProgressFor(selectedResearch));
            if (availableSteps.isEmpty()) {
                this.selectedResearch = null;
            }
        } else if (selectedResearch != null) {
            final ResearchProgress progressFor = progress.getProgressFor(this.selectedResearch);

            if (progressFor.hasCurrentStep()) {
                System.out.println("Additional info!");
                if (!progressFor.tryCompleteCurrentStep(null)) {
                    System.out.println("Could not complete");
                }
                return;
            }

            final Collection<IResearchStep> availableSteps = this.selectedResearch.getAvailableSteps(progressFor);
            if (availableSteps.isEmpty()) {
                this.selectedResearch = null;
                return;
            }

            final IResearchStep next = availableSteps.iterator().next();
            if (next.canBeStarted(progress, null)) {
                progressFor.StartStep(next, null);
            }

        }
    }
}
