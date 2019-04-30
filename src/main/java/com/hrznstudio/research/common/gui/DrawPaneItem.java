package com.hrznstudio.research.common.gui;

import com.hrznstudio.research.common.blocks.researchtable.GuiResearchTable;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class DrawPaneItem extends DrawPane {
    private final ItemStackHandler inventory;
    private final int inventorySlot;

    public DrawPaneItem(GuiResearchTable guiResearchTable, ItemStackHandler inventory, int inventorySlot) {
        super(guiResearchTable);
        this.inventory = inventory;
        this.inventorySlot = inventorySlot;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        final ItemStack item = getStack();
        final RenderItem renderItem = guiResearchTable.mc.getRenderItem();

        renderItem.zLevel = -10.0F;

        renderItem.renderItemAndEffectIntoGUI(guiResearchTable.mc.player, item, startX, startY);
        renderItem.renderItemOverlayIntoGUI(guiResearchTable.mc.fontRenderer, item, startX, startY, null);
        drawBorder(0xffffffff, 2);
        renderItem.zLevel = 0.0F;
    }

    @Override
    void init() {

    }

    public ItemStack getStack() {
        return inventory.getStackInSlot(inventorySlot);
    }

    @Override
    public void handleClick(int mouseX, int mouseY, int mouseButton) {
        final ItemStack stack = new ItemStack(getStack().getItem() == Items.APPLE ? Items.ARROW : Items.APPLE);
        inventory.setStackInSlot(inventorySlot, stack);
    }
}
