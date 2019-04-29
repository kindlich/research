package com.hrznstudio.research.common.blocks.researchtable;

import com.hrznstudio.research.api.gui.DrawToolRectangle;
import org.jetbrains.annotations.Contract;

import java.awt.*;

public class RTDrawTool implements DrawToolRectangle {
    private final Rectangle dimensions;
    private final GuiResearchTable table;


    @Contract(pure = true)
    public RTDrawTool(Rectangle dimensions, GuiResearchTable table) {
        this.dimensions = dimensions;
        this.table = table;
    }

    @Override
    public DrawToolRectangle createRectangle(Rectangle dimensions) {
        final Rectangle out = new Rectangle(dimensions);
        out.x = this.dimensions.x + dimensions.x;
        out.y = this.dimensions.y + dimensions.y;
        out.width = Math.min(this.dimensions.width, dimensions.width);
        out.height = Math.min(this.dimensions.height, dimensions.height);

        return new RTDrawTool(out, table);
    }

    @Override
    public void drawLocalizedText(String localizationKey) {
        table.drawCenteredString(table.getMC().fontRenderer, localizationKey, dimensions.x, dimensions.y, 0);
    }
}
