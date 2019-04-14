package com.hrznstudio.research.common.blocks.researchtable.gui;

import net.minecraft.client.gui.GuiButton;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class GuiButtonData<T> extends GuiButton {
    
    
    private final Function<@NotNull T, String> stringFunction;
    private T data;
    
    public GuiButtonData(int buttonId, int x, int y, int widthIn, int heightIn, Function<@NotNull T, String> stringFunction) {
        this(buttonId, x, y, widthIn, heightIn, null, stringFunction);
    }
    
    public GuiButtonData(int buttonId, int x, int y, int widthIn, int heightIn, T data, Function<@NotNull T, String> stringFunction) {
        super(buttonId, x, y, widthIn, heightIn, "INVALID");
        this.data = data;
        this.stringFunction = stringFunction;
        
        setDisplayString();
    }
    
    @Contract(pure = true)
    public T getData() {
        return data;
    }
    
    public void setData(T research) {
        this.data = research;
        if(research == null)
            this.visible = false;
        
        setDisplayString();
    }
    
    @Contract(pure = true)
    public boolean hasData() {
        return this.data != null;
    }
    
    private void setDisplayString() {
        this.displayString = getData() == null ? "INVALID" : this.stringFunction.apply(getData());
    }
}
