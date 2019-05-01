package com.hrznstudio.research.common.gui;

import com.hrznstudio.research.common.blocks.researchtable.GuiResearchTable;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.I18n;
import org.jetbrains.annotations.Contract;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Function;

@ParametersAreNonnullByDefault
public abstract class DrawPane {
    public int startX;
    public int startY;
    public int width;
    public int height;
    protected GuiResearchTable guiResearchTable;

    @Contract(pure = true)
    public DrawPane(GuiResearchTable guiResearchTable) {
        this.guiResearchTable = guiResearchTable;
    }

    @Contract(pure = true)
    public DrawPane(DrawPane other) {
        this.guiResearchTable = other.guiResearchTable;
        resize(other.startX, other.startY, other.width, other.height);
    }

    public abstract void draw(int mouseX, int mouseY);

    public abstract void drawBackground(int mouseX, int mouseY);

    public abstract void init();

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

    public DrawPane drawBorderAndReturnInnerPane(int color, int size, int borderOffset) {
        drawBorder(color, size);
        return innerPane(size + borderOffset);
    }

    public DrawPane innerPane(int offsetAll) {
        final int offset = 2 * offsetAll;
        return getSubPane(offsetAll, offsetAll, width - offset, height - offset);
    }

    public DrawPane innerPane(int offsetTopButton, int offsetSides) {
        return getSubPane(offsetSides, offsetTopButton, width - 2 * offsetSides, height - 2 * offsetTopButton);
    }


    public void drawRect(int color) {
        Gui.drawRect(startX, startY, startX + width, startY + height, color);
    }

    public void drawLocalizedText(int color, String localizationKey, Object... parameters) {
        drawText(color, I18n.format(localizationKey, parameters));
    }

    public void drawProgressBar(double done) {
        drawBorder(0xff000000, 1);

        final int w = (int) (width * done);
        getSubPane(1, 1, w, height - 1).drawRect(0xff008000);
    }

    public void drawText(int color, String text) {
        final FontRenderer fontRenderer = guiResearchTable.mc.fontRenderer;
        final int fontHeightPre = fontRenderer.FONT_HEIGHT;
        while (fontRenderer.getWordWrappedHeight(text, width) > height) {
            fontRenderer.FONT_HEIGHT--;
        }
        fontRenderer.drawSplitString(text, startX, startY, width, color);
        fontRenderer.FONT_HEIGHT = fontHeightPre;
    }

    public void drawSine(double from, double to, int color, int dotSize) {
        double range = to - from;
        for (int i = 0; i < this.width; i++) {
            final double functionValue = Math.sin(from + (i * range / width));
            //final int startY = 0;
            getSubPane(i, (int) ((0.5 * height) * (1.0D - functionValue)), dotSize, dotSize).drawRect(color);
        }
    }

    public void onTableUpdated() {

    }
}
