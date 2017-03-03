package com.game.agar.communication;

import com.game.agar.Game;
import com.game.agar.entities.Ball;
import com.game.agar.entities.Entity;
import com.game.agar.tools.Position;

import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.function.Consumer;

public class Handler {

    private CommunicationManager communicationManager = new CommunicationManager();
    private Consumer<Exception> connectionErrorCallback;

    private List<Entity> entities;
    private Game game;

    public Handler(List<Entity> entities, Game game, Consumer<Exception> connectionErrorCallback) {
        this.entities = entities;
        this.game = game;
        this.connectionErrorCallback = connectionErrorCallback;

        try{
            communicationManager.setCommunicationListener(this::handleRequestWrapper);
            communicationManager.start();
        } catch(IOException e){
            connectionErrorCallback.accept(e);
        }
    }

    private void handleRequestWrapper(String request){
        try{
            handleRequest(request);
        }
        catch(JSONException e){
            connectionErrorCallback.accept(e);
        }
    }

    private void handleRequest(String request){

        JSONObject json = new JSONObject(request);

        String action = json.getString("action");
        switch(action){
            case "add":
                Position position = new Position(json.getInt("x"), json.getInt("y"));
                game.addTask(() -> entities.add(new Ball(position)));
                break;
            // TODO
        }

    }

}
