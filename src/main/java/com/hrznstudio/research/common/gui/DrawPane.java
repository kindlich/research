package com.hrznstudio.research.common.gui;

import com.hrznstudio.research.common.blocks.researchtable.GuiResearchTable;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.jetbrains.annotations.Contract;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Function;

@ParametersAreNonnullByDefault
public abstract class DrawPane {
    protected int startX;
    protected int startY;
    protected int width;
    protected int height;
    protected GuiResearchTable guiResearchTable;

    @Contract(pure = true)
    public DrawPane(GuiResearchTable guiResearchTable) {
        this.guiResearchTable = guiResearchTable;
    }

    public void setGuiResearchTable(GuiResearchTable guiResearchTable) {
        this.guiResearchTable = guiResearchTable;
    }

    public abstract void draw(int mouseX, int mouseY);

    public abstract void drawBackground(int mouseX, int mouseY);

    abstract void init();

    public abstract void handleClick(int mouseX, int mouseY, int mouseButton);

    public boolean resize(int startX, int startY, int width, int height) {
        if (this.startX == startX && this.startY == startY && this.width == width && this.height == height)
            return false;

        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
        return true;
    }

    public DrawPane getSubPane(int startX, int startY, int width, int height) {
        final DrawPane drawPane = new DrawPaneBasic(guiResearchTable);
        drawPane.resize(this.startX + startX, this.startY + startY, width, height);
        return drawPane;
    }

    public <T extends DrawPane> T getSubPane(int startX, int startY, int width, int height, Function<GuiResearchTable, T> generator) {
        final T drawPane = generator.apply(guiResearchTable);
        drawPane.resize(this.startX + startX, this.startY + startY, width, height);
        return drawPane;
    }

    public boolean containsPoint(int x, int y) {
        return x > this.startX && x < this.startX + width && y > this.startY && y < this.startY + height;
    }

    public void drawBorder(int color) {
        drawBorder(color, 1);
    }

    public void drawBorder(int color, int size) {
        //Upper and lower border
        getSubPane(0, 0, width + size, size).drawRect(color);
        getSubPane(0, height, width + size, size).drawRect(color);

        //Sides
        getSubPane(0, 0, size, height + size).drawRect(color);
        getSubPane(width, 0, size, height + size).drawRect(color);
    }

    public void drawRect(int color) {
        Gui.drawRect(startX, startY, startX + width, startY + height, color);
    }

    public void drawLocalizedText(String localizationKey, int color) {
        int x;
        final FontRenderer fontRenderer = guiResearchTable.mc.fontRenderer;
        final int fontHeightPre = fontRenderer.FONT_HEIGHT;
        while((x = fontRenderer.getWordWrappedHeight(localizationKey, width)) > height) {
            fontRenderer.FONT_HEIGHT--;
        }
        fontRenderer.drawSplitString(localizationKey, startX, startY, width, color);
        fontRenderer.FONT_HEIGHT = fontHeightPre;
    }
}
