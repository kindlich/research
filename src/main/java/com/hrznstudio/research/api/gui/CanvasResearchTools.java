package com.hrznstudio.research.api.gui;

import com.hrznstudio.research.ResearchMod;
import com.hrznstudio.research.common.blocks.researchtable.ItemStackHandlerResearchTable;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;

public class CanvasResearchTools extends Canvas {

    private final ItemStackHandlerResearchTable slots;
    private Canvas arrowLeft, arrowRight;
    private int slotOffset = 0;
    private int slotCount;


    protected CanvasResearchTools(Canvas parent, double offsetX, double offsetY, double width, double height, ItemStackHandlerResearchTable slots) {
        super(parent, offsetX, offsetY, width, height);
        this.slots = slots;
    }

    @Override
    public void init() {
        this.slotCount = ((int) (this.width / 18)) - 2;
        if (slotCount < 1) {
            throw new IllegalStateException();
        }

        arrowLeft = new CanvasArrow(this, 2, 0, 16, 16, false);
        arrowRight = new CanvasArrow(this, (slotCount + 1) * 18, 0, 16, 16, true);

        if (slotCount >= slots.getSizeToolSlots()) {
            arrowLeft.active = false;
            arrowRight.active = false;
        } else {
            arrowLeft.active = true;
            arrowRight.active = true;
        }

        final int min = Math.min(slotCount, slots.getSizeToolSlots());


        for (int i = 0; i < min; i++) {
            //getSubCanvas(18 * i + 18, 0, 16, 16, CanvasConstructors.getConstructor(CanvasItemHolder.class, slots, slots.getToolSlotId(i)));
            new CanvasItemHolderOffset(this, 18 * i + 18, 0, 16, 16, i);
        }

        super.init();
    }

    @Override
    void drawContent(int mouseX, int mouseY) {
    }

    @Override
    void drawBackgroundContent(int mouseX, int mouseY) {
    }

    @Override
    public boolean moveAndResize(double absoluteX, double absoluteY, double width, double height) {
        if (slots == null)
            return super.moveAndResize(absoluteX, absoluteY, width, height);

        final int nSlotCount = ((int) (width / 18)) - 2;
        if (this.slotCount == nSlotCount) {
            return super.moveAndResize(absoluteX, absoluteY, width, height);
        }
        this.getChildren().clear();

        this.absoluteX = absoluteX;
        this.absoluteY = absoluteY;

        this.width = width;
        this.height = height;

        init();
        return true;
    }

    private static class CanvasArrow extends Canvas {

        private static final ResourceLocation arrowLeftLocation = new ResourceLocation(ResearchMod.MODID, "textures/gui/container/left.png");
        private static final ResourceLocation arrowRightLocation = new ResourceLocation(ResearchMod.MODID, "textures/gui/container/right.png");

        private final CanvasResearchTools parent;
        private final boolean adding;

        private CanvasArrow(Canvas parent, double offsetX, double offsetY, double width, double height, boolean adding) {
            super(parent, offsetX, offsetY, width, height);
            this.parent = (CanvasResearchTools) parent;
            this.adding = adding;
        }

        @Override
        void drawContent(int mouseX, int mouseY) {
        }

        @Override
        void drawBackgroundContent(int mouseX, int mouseY) {
            renderer.drawTexture(this.getAbsX(), this.getAbsY(), this.getWidth(), this.getHeight(), adding ? arrowRightLocation : arrowLeftLocation);
        }

        @Override
        public void handleClick(int mouseX, int mouseY, int mouseButton) {
            if (adding)
                parent.slotOffset = (parent.slotOffset + 1) % parent.slots.getSizeToolSlots();
            else
                parent.slotOffset = (parent.slotOffset <= 0 ? parent.slots.getSizeToolSlots() : parent.slotOffset) - 1;
        }
    }

    private static class CanvasItemHolderOffset extends Canvas {

        private final CanvasResearchTools parent;
        private final int slot;

        private CanvasItemHolderOffset(Canvas parent, double offsetX, double offsetY, double width, double height, int slot) {
            super(parent, offsetX, offsetY, width, height);
            this.parent = (CanvasResearchTools) parent;
            this.slot = slot;
        }

        @Override
        void drawContent(int mouseX, int mouseY) {
            renderer.drawItemStack(this.getAbsX(), this.getAbsY(), this.getWidth(), this.getHeight(), parent.slots.getToolSlot(getSlotId()));
        }

        @Override
        void drawBackgroundContent(int mouseX, int mouseY) {
            if (containsAbsolutePoint(mouseX, mouseY))
                renderer.drawRect(getAbsX(), getAbsY(), getWidth(), getHeight(), 0x89abcdef);
        }

        @Override
        public void handleClick(int mouseX, int mouseY, int mouseButton) {
            if (containsAbsolutePoint(mouseX, mouseY))
                parent.slots.clickSlot(getSlotId());
        }

        @Contract(pure = true)
        private int getSlotId() {
            return (slot + parent.slotOffset) % parent.slots.getSizeToolSlots();
        }
    }
}
