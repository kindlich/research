package com.hrznstudio.research.common.blocks.aids.aidBattery;

import com.hrznstudio.research.ResearchMod;
import com.hrznstudio.research.api.gui.Canvas;
import com.hrznstudio.research.api.research.IResearchAid;
import net.minecraft.util.ResourceLocation;

public class AidBattery implements IResearchAid {
    private static final ResourceLocation ID = new ResourceLocation(ResearchMod.MODID, "aids/battery");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void attachCanvas(Canvas canvasContent) {
        canvasContent.addChild(new CanvasAidBattery(canvasContent, canvasContent.getWidth(), canvasContent.getHeight()), 0, 0);
    }
}
