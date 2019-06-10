package com.hrznstudio.research.common.blocks.researchtable.gui;

import com.hrznstudio.research.api.gui.Canvas;
import com.hrznstudio.research.api.gui.CanvasConstructors;
import com.hrznstudio.research.common.blocks.researchtable.TileResearchTable;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.Contract;

public class CanvasResearchList extends Canvas {

    private final TileResearchTable table;
    private final EntityPlayer player;

    public CanvasResearchList(Canvas parent, double width, double height, TileResearchTable table, EntityPlayer player) {
        super(parent, width, height);
        this.table = table;
        this.player = player;
    }

    @SuppressWarnings("unused")
    @Contract(pure = true)
    private static int calculateButtonCount(double height) {
        return 8;
    }

    @Override
    public void initContent() {
        final int buttonCount = calculateButtonCount(getHeight());
        final double heightPerButton = getHeight() / buttonCount;
        for (int i = 0; i < buttonCount; i++) {
            getSubCanvas(0, i * heightPerButton, getWidth(), heightPerButton, CanvasConstructors.getConstructorTypes(CanvasResearchListEntry.class, new Object[]{i, player}, int.class, EntityPlayer.class));
        }
    }

    @Override
    protected void drawContent(int mouseX, int mouseY) {

    }

    @Override
    protected void drawBackgroundContent(int mouseX, int mouseY) {

    }
}
