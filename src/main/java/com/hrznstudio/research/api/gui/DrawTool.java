package com.hrznstudio.research.api.gui;

import java.awt.*;

public interface DrawTool {
    DrawToolRectangle createRectangle(Rectangle dimensions);

    void drawLocalizedText(String localizationKey);
}
