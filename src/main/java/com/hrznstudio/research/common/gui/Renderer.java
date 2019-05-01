package com.hrznstudio.research.common.gui;

import com.hrznstudio.research.common.blocks.researchtable.GuiResearchTable;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;

public class Renderer {

    private final GuiResearchTable guiResearchTable;

    @Contract(pure = true)
    public Renderer(GuiResearchTable guiResearchTable) {
        this.guiResearchTable = guiResearchTable;
    }

    public void drawRect(int startX, int startY, int width, int height, int color) {
        Gui.drawRect(startX, startY, startX + width, startY + height, color);
    }

    public void drawText(int startX, int startY, int width, int height, int color, String text) {
        final FontRenderer fontRenderer = guiResearchTable.mc.fontRenderer;
        final int fontHeightPre = fontRenderer.FONT_HEIGHT;
        while (fontRenderer.getWordWrappedHeight(text, width) > height) {
            fontRenderer.FONT_HEIGHT--;
        }
        fontRenderer.drawSplitString(text, startX, startY, width, color);
        fontRenderer.FONT_HEIGHT = fontHeightPre;
    }

    public void drawItemStack(int startX, int startY, int width, int height, ItemStack item) {
        final RenderItem renderItem = guiResearchTable.mc.getRenderItem();

        renderItem.renderItemAndEffectIntoGUI(guiResearchTable.mc.player, item, startX, startY);
        renderItem.renderItemOverlayIntoGUI(guiResearchTable.mc.fontRenderer, item, startX, startY, null);
    }

    public void drawTexture(int startX, int startY, int width, int height, ResourceLocation location) {
        guiResearchTable.mc.renderEngine.bindTexture(location);
        Gui.drawModalRectWithCustomSizedTexture(startX, startY, 0, 0, width, height, width, height);

    }
}
