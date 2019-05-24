package com.hrznstudio.research.api.gui;

import com.hrznstudio.research.common.blocks.researchtable.ItemStackHandlerResearchTable;

public class CanvasResearchAids extends Canvas {

    private final ItemStackHandlerResearchTable items;

    protected CanvasResearchAids(Canvas parent, double offsetX, double offsetY, double width, double height, ItemStackHandlerResearchTable items) {
        super(parent, offsetX, offsetY, width, height);
        this.items = items;
        //getInnerCanvas(0.0D, CanvasConstructors.getFilled(0xffff0000))
        //        .getInnerCanvas(height / 10, CanvasConstructors.getFilled(0xff00ffff));

        getSubCanvas(10, 10, 16, 16, CanvasConstructors.getConstructor(CanvasItemHolder.class, items, items.getAidSlotId(0)));
        getSubCanvas(30, 30, 16, 16, CanvasConstructors.getConstructor(CanvasItemHolder.class, items, items.getAidSlotId(1)));
        getSubCanvas(70, 70, 16, 16, CanvasConstructors.getConstructor(CanvasItemHolder.class, items, items.getAidSlotId(2)));
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    void drawContent(int mouseX, int mouseY) {

    }

    @Override
    void drawBackgroundContent(int mouseX, int mouseY) {

    }

}
