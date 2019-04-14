package com.hrznstudio.research.common.blocks.researchtable;

import com.hrznstudio.research.ResearchMod;
import com.hrznstudio.research.api.research.*;
import com.hrznstudio.research.api.player.ResearchProgress;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class GuiResearchTable extends GuiContainer {
    
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(ResearchMod.MODID, "textures/gui/container/research_table.png");
    private final Map<GuiButton, IResearch> buttons = new HashMap<>();
    
    private final TileResearchTable tile;
    private final ResearchProgress playerProgress;
    private IResearch selectedResearch;
    
    public GuiResearchTable(TileResearchTable tile, InventoryPlayer inventory) {
        super(new ContainerResearchTable(tile, inventory));
        this.tile = tile;
        playerProgress = APIMethods.getProgressForPlayer(inventory.player);
        selectedResearch = tile.currentResearch;
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.drawWorldBackground(0);
        
        mc.renderEngine.bindTexture(GUI_TEXTURE);
        drawModalRectWithCustomSizedTexture(16, 16, 0, 0, 427 - 32, 240 - 32, 427 - 32, 240 - 32);
        
        {
            drawCenteredString(fontRenderer, "Researches", 362, 80, 0x11ffaa);
            int i = 0;
            
            for(final IResearch res : this.playerProgress.getAvailableResearches().values()) {
                drawCenteredString(fontRenderer, res.getId().getPath(), 362, 94 + 14 * i, 0x00ffff);
            }
        }
        
        {
            
            drawCenteredString(fontRenderer, "Steps", 214, 80, 0x11ffaa);
            int i = 0;
            if(this.selectedResearch == null) {
                drawCenteredString(fontRenderer, "No Research selected", 214, 80 + ++i * 14, 0x00ffff);
            } else{
                for(final IResearchStep completedStep : this.tile.getCompletedSteps()) {
                    drawCenteredString(fontRenderer, completedStep.getId().toString(), 214, 80 + ++i * 14, 0x00ffff);
                }
                
                if(tile.getCurrentStep() != null) {
                    
                    for(final IResearchStepInstance step : tile.getCurrentStep().getNextSteps()) {
                        drawCenteredString(fontRenderer, step.getStep().getId().toString(), 214, 80 + ++i * 14, 0x0000ff);
                    }
                } else {
                    drawCenteredString(fontRenderer, this.selectedResearch.getStart().getStep().getId().toString(), 214, 80 + ++i * 14, 0x0000ff);
                }
            }
        }
    }
    
    @Override
    public void initGui() {
        super.initGui();
        
        
        int i = 0;
        for(final IResearch res : this.playerProgress.getAvailableResearches().values()) {
            final GuiButton button = new GuiButton(i, 330, 92 + 14 * i++, res.getId().getPath());
            buttons.put(button, res);
            button.height = 13;
            button.width = 62;
            addButton(button);
        }
    }
    
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        final IResearch selectedResearch = buttons.get(button);
        setResearch(selectedResearch == this.selectedResearch ? null : selectedResearch);
    }
    
    private void setResearch(@Nullable IResearch research) {
        this.selectedResearch = research;
        this.tile.setCurrentResearch(research);
        
    }
}
