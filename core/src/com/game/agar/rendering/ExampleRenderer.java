package com.game.agar.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.TimeUtils;

/*
    Only an example
*/
public class ExampleRenderer implements IRenderer {

    private double red, green, blue;
    @Override
    public void renderFrame() {
        long time = TimeUtils.millis();
        double timeFactor = (double) time/3000;
        red = 0.5f + 0.5f*Math.sin( timeFactor);
        green = 0.5f + 0.5f*Math.sin( (timeFactor + 2*Math.PI/3));
        blue = 0.5f + 0.5f*Math.sin( timeFactor + 4*Math.PI/3);
        Gdx.gl.glClearColor((float)red, (float)green, (float)blue, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}
