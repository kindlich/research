package com.hrznstudio.research.common.gui;

import com.hrznstudio.research.common.blocks.researchtable.GuiResearchTable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collection;

@ParametersAreNonnullByDefault
public class DrawPaneResearchTools extends DrawPane {

    private final ItemStackHandler itemStackHandler;
    private final Collection<DrawPaneWrapperHoverColor<DrawPaneItem>> toolSlots = new ArrayList<>();

    public DrawPaneResearchTools(GuiResearchTable guiResearchTable, ItemStackHandler itemStackHandler) {
        super(guiResearchTable);
        this.itemStackHandler = itemStackHandler;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        drawBorder(0xffffffff);
        //drawRect(0xabcdefff);
        for (DrawPaneWrapperHoverColor<DrawPaneItem> subPane : toolSlots) {
            subPane.draw(mouseX, mouseY);
        }
    }

    @Override
    void init() {
        toolSlots.clear();
        final int slotCount = itemStackHandler.getSlots();
        final int slotSize = width / (slotCount + 2);

        for (int i = 0; i < slotCount; ) {
            final int i1 = i;
            final DrawPaneItem subPane = getSubPane(++i * slotSize, 0, slotSize, height, g -> new DrawPaneItem(g, itemStackHandler, i1));

            final DrawPaneWrapperHoverColor<DrawPaneItem> hoverItem = new DrawPaneWrapperHoverColor<>(subPane, 0xff345678);
            toolSlots.add(hoverItem);
        }
    }

    @Override
    public void handleClick(int mouseX, int mouseY, int mouseButton) {
        for (DrawPaneWrapperHoverColor<DrawPaneItem> toolSlot : this.toolSlots) {
            if (toolSlot.containsPoint(mouseX, mouseY)) {
                toolSlot.handleClick(mouseX, mouseY, mouseButton);
            }
        }
    }
}
