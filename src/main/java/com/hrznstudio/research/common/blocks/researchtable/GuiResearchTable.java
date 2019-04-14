package com.hrznstudio.research.common.blocks.researchtable;

import com.hrznstudio.research.ResearchMod;
import com.hrznstudio.research.api.player.IResearchProgress;
import com.hrznstudio.research.api.research.APIMethods;
import com.hrznstudio.research.api.research.IResearch;
import com.hrznstudio.research.api.research.IResearchStepInstance;
import com.hrznstudio.research.common.blocks.researchtable.gui.GuiButtonData;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;


public class GuiResearchTable extends GuiContainer {
    
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(ResearchMod.MODID, "textures/gui/container/research_table.png");
    private static final int rows = 6;
    
    
    @SuppressWarnings("unchecked")
    private final GuiButtonData<IResearch>[] researchButtons = new GuiButtonData[rows];
    @SuppressWarnings("unchecked")
    private final GuiButtonData<IResearchStepInstance>[] researchStepButtons = new GuiButtonData[rows];
    
    private final TileResearchTable tile;
    private final IResearchProgress playerProgress;
    private IResearch selectedResearch;
    
    public GuiResearchTable(TileResearchTable tile, InventoryPlayer inventory) {
        super(new ContainerResearchTable(tile, inventory));
        this.tile = tile;
        playerProgress = APIMethods.getProgressForPlayer(inventory.player);
        selectedResearch = playerProgress.getCurrentResearchProgress().getCurrentResearch();
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.drawWorldBackground(0);
        
        mc.renderEngine.bindTexture(GUI_TEXTURE);
        drawModalRectWithCustomSizedTexture(16, 16, 0, 0, 427 - 32, 240 - 32, 427 - 32, 240 - 32);
        
        drawCenteredString(fontRenderer, "Researches", 362, 80, 0x11ffaa);
        
        drawCenteredString(fontRenderer, "Steps", 214, 80, 0x11ffaa);
        if(this.selectedResearch == null) {
            drawCenteredString(fontRenderer, "No Research selected", 214, 94, 0x00ffff);
        }
    }
    
    @Override
    public void initGui() {
        super.initGui();
        
        
        for(int i = 0; i < rows; i++) {
            
            //Researches
            {
                final GuiButtonData<IResearch> buttonResearch = new GuiButtonData<>(i, 330, 92 + 14 * i, 62, 13, r -> r.getId().getPath());
                this.researchButtons[i] = buttonResearch;
                buttonResearch.visible = true;
                addButton(buttonResearch);
            }
            
            //Research steps
            {
                final GuiButtonData<IResearchStepInstance> buttonStep = new GuiButtonData<>(i + rows, 112, 92 + 14 * i, 203, 13, s -> s.getStep().getId().getPath());
                this.researchStepButtons[i] = buttonStep;
                buttonStep.visible = true;
                addButton(buttonStep);
            }
        }
    
        updateButtons();
    
    }
    
    private void updateButtons() {
        setButtons(playerProgress.getAvailableResearches(), researchButtons);
        setButtons(playerProgress.getCurrentResearchProgress().getAvailableSteps(), researchStepButtons);
    }
    
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        
        if(!(button instanceof GuiButtonData))
            return;
        
        if(button.id < rows) {
            //Is research
            final IResearch research = (IResearch) ((GuiButtonData) button).getData();
            setResearch(research);
        } else {
            //Is research step
            final IResearchStepInstance step = (IResearchStepInstance) ((GuiButtonData) button).getData();
            playerProgress.getCurrentResearchProgress().completeStep(step, 1.00d);
        }
        updateButtons();
    }
    
    private void setResearch(@Nullable IResearch research) {
        this.selectedResearch = research;
        playerProgress.getCurrentResearchProgress().startResearch(research);
    }
    
    private <T> void setButtons(List<T> data, GuiButtonData<T>[] buttonList) {
        if(data.size() < rows) {
            for(GuiButtonData<?> researchButton : buttonList) {
                researchButton.visible = false;
            }
            
            for(int i = 0; i < data.size(); i++) {
                buttonList[i].setData(data.get(i));
                buttonList[i].visible = true;
            }
        } else {
            for(int i = 0; i < rows; i++) {
                buttonList[i].setData(data.get(i));
                buttonList[i].visible = true;
            }
        }
    }
}
