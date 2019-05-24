package com.hrznstudio.research.api.gui;

@FunctionalInterface
public interface CanvasConstructor<T extends Canvas> {


    T generate(Canvas parent, double offsetX, double offsetY, double width, double height);
}
