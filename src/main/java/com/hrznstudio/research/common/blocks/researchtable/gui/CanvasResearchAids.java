package com.hrznstudio.research.common.blocks.researchtable.gui;

import com.hrznstudio.research.api.gui.Canvas;
import com.hrznstudio.research.api.gui.CanvasBorder;
import com.hrznstudio.research.api.gui.CanvasConstructors;
import com.hrznstudio.research.api.research.IResearchAid;

public class CanvasResearchAids extends Canvas {

    private IResearchAid selectedAid;
    private Canvas canvasContent;

    public CanvasResearchAids(Canvas parent, double width, double height) {
        super(parent, width, height);
    }

    @Override
    protected void initContent() {
        final double hUnit = height / 5.0D;

        final CanvasBorder headerBorder = getSubCanvas(0, 0, width, hUnit, CanvasConstructors.getConstructor(CanvasBorder.class, 0xff000000, 1));
        headerBorder.getInnerCanvas(2.5, CanvasConstructors.getText("Research Aid", 0xffff00ff));

        canvasContent = getSubCanvas(0, hUnit, width, 4 * hUnit, CanvasConstructors.getBorder(1, 0xff000000));//.getInnerCanvas(2.5D);
        aidChanged();
    }

    @Override
    protected void drawContent(int mouseX, int mouseY) {

    }

    @Override
    protected void drawBackgroundContent(int mouseX, int mouseY) {

    }

    public void aidChanged() {
        if (this.place.getActiveAid() != this.selectedAid) {
            this.selectedAid = this.place.getActiveAid();
            this.canvasContent.clearChildren();
            this.selectedAid.attachCanvas(this.canvasContent);
        }
    }

}
