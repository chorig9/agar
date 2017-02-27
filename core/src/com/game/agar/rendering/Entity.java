package com.game.agar.rendering;

import com.game.agar.tools.Position;

public class Entity {

    private Position position;
    private Model model;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
    //TODO...
}
