package com.hrznstudio.research.api.research;

import com.hrznstudio.research.api.gui.Canvas;
import com.hrznstudio.research.api.place.IResearchPlace;
import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.common.gui.DrawPane;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;

public interface IResearchAid {
    @Contract(pure = true)
    ResourceLocation getId();

    DrawPane drawInfo(IResearchPlace place, DrawPane drawPane, PlayerProgress playerProgress);

    default DrawPane drawInfoForStep(IResearchPlace place, DrawPane drawPane, PlayerProgress playerProgress) {
        return drawInfo(place, drawPane, playerProgress);
    }

    void attachCanvas(Canvas canvasContent);
}
