package com.hrznstudio.research.api.research;

import com.hrznstudio.research.api.gui.DrawTool;
import com.hrznstudio.research.api.place.IResearchPlace;
import com.hrznstudio.research.api.player.PlayerProgress;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;

import java.awt.*;

public interface IResearchAid {
    @Contract(pure = true)
    ResourceLocation getId();

    void drawInfo(IResearchPlace place, DrawTool drawTool, Rectangle dimensions, PlayerProgress playerProgress);

    default void drawInfoForStep(IResearchPlace place, DrawTool drawTool, Rectangle dimensions, PlayerProgress playerProgress) {
        drawInfo(place, drawTool, dimensions, playerProgress);
    }
}
