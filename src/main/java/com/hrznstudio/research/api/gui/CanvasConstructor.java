package com.hrznstudio.research.api.gui;

@FunctionalInterface
public interface CanvasConstructor<T extends Canvas> {
    T generate(Canvas parent, double width, double height);
}
