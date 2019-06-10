package com.hrznstudio.research.api.gui;

import com.hrznstudio.research.api.place.IResearchPlace;
import com.hrznstudio.research.common.gui.Renderer;
import org.jetbrains.annotations.Contract;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

@ParametersAreNonnullByDefault
public abstract class Canvas {
    public static final int buttonLeft = 0, buttonRight = 1;

    protected final Renderer renderer;
    protected final IResearchPlace place;
    private final Collection<Canvas> children = new HashSet<>();
    protected double absoluteX;
    protected double absoluteY;
    protected double height;
    protected double width;
    private boolean active = true;

    public Canvas(Canvas parent, double width, double height) {
        this.renderer = parent.renderer;
        this.width = width;
        this.height = height;
        this.place = parent.place;
        //parent.addChild(this, offsetX, offsetY);
    }

    @Contract(pure = true)
    public Canvas(Renderer renderer, IResearchPlace place, double absoluteX, double absoluteY, double width, double height) {
        this.renderer = renderer;
        this.absoluteX = absoluteX;
        this.absoluteY = absoluteY;
        this.width = width;
        this.height = height;
        this.place = place;
    }

    public boolean containsAbsolutePoint(double x, double y) {
        return x > this.absoluteX && x < this.absoluteX + width && y > this.absoluteY && y < this.absoluteY + height;
    }

    public void addChild(Canvas child, double offsetX, double offsetY) {
        children.add(child);
        final double width = Math.min(child.width, this.width);
        final double height = Math.min(child.height, this.height);

        double absX = this.absoluteX + offsetX;
        double absY = this.absoluteY + offsetY;

        //if(!this.containsAbsolutePoint(absX + width, absY + height)) {
        //    absX = this.absoluteX;
        //    absY = this.absoluteY;
        //}

        child.moveAndResize(absX, absY, width, height);
    }

    public Collection<Canvas> getChildren() {
        return children;
    }

    protected abstract void drawContent(int mouseX, int mouseY);

    protected abstract void drawBackgroundContent(int mouseX, int mouseY);

    public void drawBackground(int mouseX, int mouseY) {
        this.drawBackgroundContent(mouseX, mouseY);
        for (Canvas child : this.children) {
            if (child.isActive())
                child.drawBackground(mouseX, mouseY);
        }
    }

    /**
     * Called once from the GUI, after the complete Canvas has been created (i.e. the Constructor has completed)
     * Unlike in MC NOT called whenever a resize takes place!
     */
    public final void init() {
        this.initContent();
        for (Canvas child : children) {
            child.init();
        }
    }

    protected abstract void initContent();

    public void tearDown() {
        for (Canvas child : children) {
            child.tearDown();
        }
    }

    public void draw(int mouseX, int mouseY) {
        this.drawContent(mouseX, mouseY);
        for (Canvas child : this.children) {
            if (child.isActive())
                child.draw(mouseX, mouseY);
        }
    }

    public void handleClick(int mouseX, int mouseY, int mouseButton) {
        for (Canvas child : children) {
            if (child.isActive() && child.containsAbsolutePoint(mouseX, mouseY))
                child.handleClick(mouseX, mouseY, mouseButton);
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean moveAndResize(double absoluteX, double absoluteY, double width, double height) {
        if (this.absoluteX == absoluteX && this.absoluteY == absoluteY && this.width == width && this.height == height)
            return false;

        for (Canvas child : children) {
            final double newX = absoluteX + (child.absoluteX - this.absoluteX) * (width / this.width);
            final double newY = absoluteY + (child.absoluteY - this.absoluteY) * (height / this.height);
            final double newWidth = child.width == 0 ? width : (width / this.width) * child.width;
            final double newHeight = child.height == 0 ? height : (height / this.height) * child.height;
            child.moveAndResize(newX, newY, newWidth, newHeight);
        }

        this.height = height;
        this.width = width;
        this.absoluteX = absoluteX;
        this.absoluteY = absoluteY;

        return true;
    }

    public CanvasSimple getSubCanvas(double offsetX, double offsetY, double width, double height) {
        return this.getSubCanvas(offsetX, offsetY, width, height, CanvasConstructors.getBasic());
    }

    public <T extends Canvas> T getSubCanvas(double offsetX, double offsetY, double width, double height, CanvasConstructor<T> con) {
        final T generate = con.generate(this, width, height);
        this.addChild(generate, offsetX, offsetY);
        return generate;
    }

    public CanvasSimple getInnerCanvas(double offsetAll) {
        return getInnerCanvas(offsetAll, CanvasConstructors.getBasic());
    }

    public <T extends Canvas> T getInnerCanvas(double offsetAll, CanvasConstructor<T> con) {
        return getSubCanvas(offsetAll, offsetAll, width - 2 * offsetAll, height - 2 * offsetAll, con);
    }

    public int getAbsX() {
        return (int) absoluteX;
    }

    public int getAbsY() {
        return (int) absoluteY;
    }

    public int getHeight() {
        return (int) height;
    }

    public int getWidth() {
        return (int) width;
    }

    public void clearChildren() {
        final Iterator<Canvas> iterator = this.children.iterator();
        while (iterator.hasNext()) {
            final Canvas next = iterator.next();
            next.tearDown();
            iterator.remove();
        }
    }
}
