package com.hrznstudio.research.common.blocks.researchtable;

import com.hrznstudio.research.APIMethods;
import com.hrznstudio.research.api.gui.CanvasResearchTable;
import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.research.IResearch;
import com.hrznstudio.research.common.gui.Renderer;
import com.hrznstudio.research.helpers.ContainerResearchTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;

@ParametersAreNonnullByDefault
public class GuiResearchTable2 extends GuiContainer {

    public final TileResearchTable table;
    public final PlayerProgress progress;
    public final CanvasResearchTable canvasResearchTable;
    private IResearch selectedResearch;


    public GuiResearchTable2(TileResearchTable table, EntityPlayer player) {
        super(new ContainerResearchTable());
        this.mc = Minecraft.getMinecraft();

        final ScaledResolution scaledResolution = new ScaledResolution(mc);
        this.width = scaledResolution.getScaledWidth();
        this.height = scaledResolution.getScaledHeight();
        final Renderer renderer = new Renderer(this);
        this.table = table;
        this.progress = APIMethods.getProgress(player);
        this.canvasResearchTable = new CanvasResearchTable(renderer, 16, 16, width - 32, height - 32, table, player, table.makePlace(APIMethods.getProgress(player)));
        this.canvasResearchTable.init();
    }

    public RenderItem getItemRenderer() {
        return this.itemRender;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.drawWorldBackground(0);
        canvasResearchTable.drawBackground(mouseX, mouseY);
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
        this.canvasResearchTable.moveAndResize(16, 16, width - 32, height - 32);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        canvasResearchTable.draw(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (canvasResearchTable.containsAbsolutePoint(mouseX, mouseY))
            canvasResearchTable.handleClick(mouseX, mouseY, mouseButton);
    }


    public ContainerResearchTable getContainer() {
        return (ContainerResearchTable) this.inventorySlots;
    }


    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        canvasResearchTable.tearDown();
    }
}
