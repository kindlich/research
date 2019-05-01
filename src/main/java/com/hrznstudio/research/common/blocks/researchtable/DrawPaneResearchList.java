package com.hrznstudio.research.common.blocks.researchtable;

import com.hrznstudio.research.common.gui.DrawPane;
import com.hrznstudio.research.common.gui.DrawPaneCollectionVertical;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collection;

@ParametersAreNonnullByDefault
public class DrawPaneResearchList extends DrawPane {
    //private final Collection<DrawPane> researchButtons = new ArrayList<>(6);
    private DrawPane body, heading;

    public DrawPaneResearchList(GuiResearchTable guiResearchTable) {
        super(guiResearchTable);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        heading.drawBorderAndReturnInnerPane(0xff000000, 1, 1).drawLocalizedText(0xff000000, "research.table.gui.researches.desc");
        this.body.draw(mouseX, mouseY);
    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {
        drawRect(0xabcdefff);
    }

    @Override
    public void init() {
        final int heightUnit = height / 7;
        this.heading = getSubPane(0, 0, width, heightUnit);

        Collection<DrawPane> researchButtons = new ArrayList<>(6);


        for (int i = 0; i < 6; i++) {
            researchButtons.add(new DrawPaneResearchListSingle(getSubPane(0, (i + 1) * heightUnit, width, heightUnit), i));
        }

        this.body = new DrawPaneCollectionVertical(getSubPane(0, heightUnit, width, 6 * heightUnit), researchButtons);
    }

    @Override
    public void handleClick(int mouseX, int mouseY, int mouseButton) {
        if (body.containsPoint(mouseX, mouseY))
            body.handleClick(mouseX, mouseY, mouseButton);
    }
}
