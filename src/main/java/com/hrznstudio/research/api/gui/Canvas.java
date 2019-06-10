package com.hrznstudio.research.api.gui;

import com.hrznstudio.research.api.place.IResearchPlace;
import com.hrznstudio.research.common.gui.Renderer;
import org.jetbrains.annotations.Contract;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * A canvas is the main drawing unit.
 * They are structured in a DOM-like structure, so each canvas can either draw or have children (or both)
 * If you don't need your canvas to draw anything consider implementing {@link AbstractCanvasStructure}
 */
@ParametersAreNonnullByDefault
public abstract class Canvas {
    public static final int buttonLeft = 0, buttonRight = 1;

    protected final Renderer renderer;
    protected final IResearchPlace place;

    /**
     * The children of this canvas
     * Each parent draws first and then asks its children to draw ontop.
     */
    private final Collection<Canvas> children = new HashSet<>();

    /**
     * The absolute X/Y coordinate of the Canvas start.
     * Even though most methods use relative coordinates to create Canvasses these absolute coordinates are saved within the canvas to make calculation easier.
     */
    protected double absoluteX, absoluteY;
    protected double height;
    protected double width;
    /**
     * Is the canvas visible and can it be interacted with?
     */
    private boolean active = true;

    /**
     * Use this constructor for every Canvas that is not the root canvas.
     * Try to avoid calling a constructor directly and instead to use the {@link CanvasConstructors} methods for a {@link CanvasConstructor} object.
     * See {@link Canvas#getSubCanvas} and {@link Canvas#getInnerCanvas} for more info
     *
     * @param parent The canvas' parent, to inherit the {@link Canvas#renderer} and {@link Canvas#place} from.
     */
    public Canvas(Canvas parent, double width, double height) {
        this.renderer = parent.renderer;
        this.width = width;
        this.height = height;
        this.place = parent.place;
        //parent.addChild(this, offsetX, offsetY);
    }

    /**
     * Use this constructor only for a root
     */
    @Contract(pure = true)
    public Canvas(Renderer renderer, IResearchPlace place, double absoluteX, double absoluteY, double width, double height) {
        this.renderer = renderer;
        this.absoluteX = absoluteX;
        this.absoluteY = absoluteY;
        this.width = width;
        this.height = height;
        this.place = place;
    }

    /**
     * Checks if the coordinates are within this Canvas.
     * Careful, the parameters are <strong>absolute</strong> values!
     */
    public boolean containsAbsolutePoint(double x, double y) {
        return x > this.absoluteX && x < this.absoluteX + width && y > this.absoluteY && y < this.absoluteY + height;
    }

    /**
     * Attaches a child at the given offset
     */
    public void addChild(Canvas child, double offsetX, double offsetY) {
        children.add(child);
        final double width = Math.min(child.width, this.width);
        final double height = Math.min(child.height, this.height);

        double absX = this.absoluteX + offsetX;
        double absY = this.absoluteY + offsetY;

        child.moveAndResize(absX, absY, width, height);
    }

    public Collection<Canvas> getChildren() {
        return children;
    }

    /**
     * Draw the content, called after {@link Canvas#drawBackgroundContent}
     * Each parent draws <strong>before</strong> their children
     */
    protected abstract void drawContent(int mouseX, int mouseY);

    /**
     * Draw the content, called before {@link Canvas#drawContent}
     * Each parent draws <strong>before</strong> their children
     */
    protected abstract void drawBackgroundContent(int mouseX, int mouseY);

    /**
     * Draws the background of this Canvas and every child
     * Usually not needed to be overridden, use {@link #drawBackgroundContent} instead
     */
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

    /**
     * Called during the GUI's initialization phase
     * Move the creation of any children in here and try to avoid doing that in the constructor
     * <p>
     * By this point the Canvas will already have a definite size and you'll be ready to add whatever you need.
     */
    protected abstract void initContent();

    /**
     * Called when the GUI closes.
     * Can also be called by a parent before removing a child to clean up.
     */
    public void tearDown() {
        for (Canvas child : children) {
            child.tearDown();
        }
    }

    /**
     * Called by the GUI to draw on the screen
     * Usually not needed to be overridden, use {@link #drawContent} instead
     */
    public void draw(int mouseX, int mouseY) {
        this.drawContent(mouseX, mouseY);
        for (Canvas child : this.children) {
            if (child.isActive())
                child.draw(mouseX, mouseY);
        }
    }

    /**
     * Called when someone clicks on the screen.
     * Usually only called when it's already certain that the canvas' absolute point is included but it probably doesn't hurt having a simple {@link #containsAbsolutePoint} check regardless
     */
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

    /**
     * Moves and resizes the canvas to the specified <strong>absolute</strong> coordinates and size
     * <p>
     * By default scales the children proportionally but can be overridden for additional functionality
     *
     * @return Did the canvas move and or resize at all
     */
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

    /**
     * Creates a {@link CanvasSimple} with the provided size and relative coordinates
     * Also directly attaches it to this Canvas
     */
    public CanvasSimple getSubCanvas(double offsetX, double offsetY, double width, double height) {
        return this.getSubCanvas(offsetX, offsetY, width, height, CanvasConstructors.getBasic());
    }

    /**
     * Creates a {@link T} Canvas with the specified size at the specified relative coordinates.
     * Also directly attaches it to this canvas
     *
     * @param con The constructor to be used. Check {@link CanvasConstructors} for static methods on how to get one.
     *            Try not to use {@link CanvasConstructors} to allow for easy theme switches later.
     * @return the Generated canvas, if you need it.
     */
    public <T extends Canvas> T getSubCanvas(double offsetX, double offsetY, double width, double height, CanvasConstructor<T> con) {
        final T generate = con.generate(this, width, height);
        this.addChild(generate, offsetX, offsetY);
        return generate;
    }

    /**
     * Similar to {@link #getSubCanvas}
     * The proportions of the new Canvas will be an inner Canvas with a border of {@code offsetAll}.
     */
    public CanvasSimple getInnerCanvas(double offsetAll) {
        return getInnerCanvas(offsetAll, CanvasConstructors.getBasic());
    }

    /**
     * Similar to {@link #getSubCanvas}
     * The proportions of the new Canvas will be an inner Canvas with a border of {@code offsetAll}.
     */
    public <T extends Canvas> T getInnerCanvas(double offsetAll, CanvasConstructor<T> con) {
        return getSubCanvas(offsetAll, offsetAll, width - 2 * offsetAll, height - 2 * offsetAll, con);
    }

    /**
     * Returns the absolute X coordinate casted as int, to make rendering easier
     */
    public int getAbsX() {
        return (int) absoluteX;
    }

    /**
     * Returns the absolute Y coordinate casted as int, to make rendering easier
     */
    public int getAbsY() {
        return (int) absoluteY;
    }

    /**
     * Returns the height casted as int, to make rendering easier
     */
    public int getHeight() {
        return (int) height;
    }

    /**
     * Returns the width casted as int, to make rendering easier
     */
    public int getWidth() {
        return (int) width;
    }

    /**
     * Removes all children and calls {@link #tearDown()} upon them.
     */
    public void clearChildren() {
        final Iterator<Canvas> iterator = this.children.iterator();
        while (iterator.hasNext()) {
            final Canvas next = iterator.next();
            next.tearDown();
            iterator.remove();
        }
    }
}
