package com.hrznstudio.research.api.gui;

import com.hrznstudio.research.common.blocks.researchtable.ItemStackHandlerResearchTable;
import org.jetbrains.annotations.Contract;

public class CanvasResearchTools extends Canvas {

    private final ItemStackHandlerResearchTable slots;
    private int slotOffset = 0;
    private int slotCount;


    protected CanvasResearchTools(Canvas parent, double width, double height, ItemStackHandlerResearchTable slots) {
        super(parent, width, height);
        this.slots = slots;
        //init();
    }

    @Override
    public void initContent() {
        this.clearChildren();

        this.slotCount = ((int) (this.width / 20)) - 2;
        if (slotCount < 1) {
            return;
        }

        Canvas arrowLeft = new CanvasArrow(this, 16, 16, CanvasArrow.Direction.LEFT) {
            @Override
            public void handleClick(int mouseX, int mouseY, int mouseButton) {
                slotOffset = (slotOffset <= 0 ? slots.getSizeToolSlots() : slotOffset) - 1;
            }
        };
        Canvas arrowRight = new CanvasArrow(this, 16, 16, CanvasArrow.Direction.RIGHT) {
            @Override
            public void handleClick(int mouseX, int mouseY, int mouseButton) {
                slotOffset = (slotOffset + 1) % slots.getSizeToolSlots();
            }
        };


        this.addChild(arrowLeft, 2, 1);
        this.addChild(arrowRight, (slotCount + 1) * 20, 1);



        final int startXOff;// = 0;

        if (slotCount >= slots.getSizeToolSlots()) {
            arrowLeft.setActive(false);
            arrowRight.setActive(false);
            startXOff = ((slotCount - slots.getSizeToolSlots()) / 2) * 20;
        } else {
            arrowLeft.setActive(true);
            arrowRight.setActive(true);
            startXOff = 0;
        }

        final int min = Math.min(slotCount, slots.getSizeToolSlots());


        for (int i = 0; i < min; i++) {
            final int offsetX = startXOff + 20 * i + 20;
            this.addChild(new CanvasItemHolderOffset(this, offsetX, 0, 18, 18, i), offsetX, 0);
        }
    }

    @Override
    protected void drawContent(int mouseX, int mouseY) {
    }

    @Override
    protected void drawBackgroundContent(int mouseX, int mouseY) {
    }

    @Override
    public boolean moveAndResize(double absoluteX, double absoluteY, double width, double height) {
        if (slots == null)
            return super.moveAndResize(absoluteX, absoluteY, width, height);

        final int nSlotCount = ((int) (width / 20)) - 2;
        if (this.slotCount == nSlotCount) {
            return super.moveAndResize(absoluteX, absoluteY, width, height);
        }
        this.clearChildren();

        this.absoluteX = absoluteX;
        this.absoluteY = absoluteY;

        this.width = width;
        this.height = height;

        init();
        return true;
    }

    private static class CanvasItemHolderOffset extends Canvas {

        private final CanvasResearchTools parent;
        private final int slot;

        private CanvasItemHolderOffset(Canvas parent, double offsetX, double offsetY, double width, double height, int slot) {
            super(parent, width, height);
            this.parent = (CanvasResearchTools) parent;
            this.slot = slot;
        }

        @Override
        protected void drawContent(int mouseX, int mouseY) {
            //Top/Bottom
            renderer.drawRect(getAbsX(), getAbsY(), getWidth(), 1, 0xff000000);
            renderer.drawRect(getAbsX(), getAbsY() + getHeight(), getWidth(), 1, 0xff000000);

            //Sides
            renderer.drawRect(getAbsX(), getAbsY(), 1, getHeight(), 0xff000000);
            renderer.drawRect(getAbsX() + getWidth(), getAbsY(), 1, getHeight() + 1, 0xff000000);

            renderer.drawItemStack(this.getAbsX() + 1, this.getAbsY() + 1, this.getWidth() - 2, this.getHeight() - 2, parent.slots.getToolSlot(getSlotId()));
        }

        @Override
        protected void drawBackgroundContent(int mouseX, int mouseY) {
            if (containsAbsolutePoint(mouseX, mouseY))
                renderer.drawRect(getAbsX(), getAbsY(), getWidth(), getHeight(), 0x89abcdef);
        }

        @Override
        protected void initContent() {

        }

        @Override
        public void handleClick(int mouseX, int mouseY, int mouseButton) {
            if (containsAbsolutePoint(mouseX, mouseY))
                parent.slots.clickSlot(getSlotId(), mouseButton);
        }

        @Contract(pure = true)
        private int getSlotId() {
            return (slot + parent.slotOffset) % parent.slots.getSizeToolSlots();
        }
    }
}
