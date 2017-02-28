package com.game.agar.rendering;


public interface IRenderer {

    default void init(){}
    default void renderFrame(){}
    default void dispose(){}

}
