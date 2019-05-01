package com.hrznstudio.research.common.blocks.researchtable;

import com.hrznstudio.research.APIMethods;
import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.research.IResearch;
import com.hrznstudio.research.common.gui.Renderer;
import com.hrznstudio.research.helpers.ContainerResearchTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;

@ParametersAreNonnullByDefault
public class GuiResearchTable extends GuiContainer {

    public final TileResearchTable table;
    public final PlayerProgress progress;
    public final DrawPaneResearchTable drawPane;
    private IResearch selectedResearch;

    public GuiResearchTable(TileResearchTable table, EntityPlayer player) {
        super(new ContainerResearchTable(table, player.inventory));
        final Renderer renderer = new Renderer(this);
        this.table = table;
        this.progress = APIMethods.getProgress(player);
        this.drawPane = new DrawPaneResearchTable(renderer,
                new DrawPaneResearchList(renderer, progress),
                new DrawPaneResearchSteps(renderer, progress),
                new DrawPaneResearchAids(renderer, progress),
                new DrawPaneResearchTools(renderer, table.getToolSlots())
        );
    }

    public RenderItem getItemRenderer() {
        return this.itemRender;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.drawWorldBackground(0);
        drawPane.drawBackground(mouseX, mouseY);
    }

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
    }

    @Override
    protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
        super.handleMouseClick(slotIn, slotId, mouseButton, type);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    public void initGui() {
        super.initGui();

        this.drawPane.resize(16, 16, width - 32, height - 32);
        drawPane.init();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawPane.draw(mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (drawPane.containsPoint(mouseX, mouseY))
            drawPane.handleClick(mouseX, mouseY, mouseButton);
    }


    public ContainerResearchTable getContainer() {
        return (ContainerResearchTable) this.inventorySlots;
    }

    public IResearch getSelectedResearch() {
        return selectedResearch;
    }

    public void setSelectedResearch(@Nullable IResearch research) {
        this.selectedResearch = research;
        this.tableUpdated();
    }

    public void tableUpdated() {
        this.drawPane.onTableUpdated();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        drawPane.tearDown();
    }
}
