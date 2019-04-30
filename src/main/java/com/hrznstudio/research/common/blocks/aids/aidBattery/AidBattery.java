package com.hrznstudio.research.common.blocks.aids.aidBattery;

import com.hrznstudio.research.ResearchMod;
import com.hrznstudio.research.api.place.IResearchPlace;
import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.research.IResearchAid;
import com.hrznstudio.research.common.gui.DrawPane;
import net.minecraft.util.ResourceLocation;

public class AidBattery implements IResearchAid {
    private static final ResourceLocation ID = new ResourceLocation(ResearchMod.MODID, "aids/battery");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public DrawPane drawInfo(IResearchPlace place, DrawPane drawPane, PlayerProgress playerProgress) {
        return new DrawPaneAidBattery(drawPane);
    }

    @Override
    public DrawPane drawInfoForStep(IResearchPlace place, DrawPane drawPane, PlayerProgress playerProgress) {
        return drawInfo(place, drawPane, playerProgress);
    }
}
