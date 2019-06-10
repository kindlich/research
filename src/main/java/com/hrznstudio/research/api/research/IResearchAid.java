package com.hrznstudio.research.api.research;

import com.hrznstudio.research.api.gui.Canvas;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;

public interface IResearchAid {
    @Contract(pure = true)
    ResourceLocation getId();

    void attachCanvas(Canvas canvasContent);
}
