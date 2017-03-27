package com.game.agar.communication;

import com.badlogic.gdx.Gdx;
import com.game.agar.entities.Ball;
import com.game.agar.entities.Player;
import com.game.agar.shared.Position;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class CommunicationManager {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Consumer<String> listener;
    private Consumer<Exception> exceptionListener;
    private Player player;

    private volatile boolean running;

    public void setCommunicationListener(Consumer<String> listener){
        this.listener = listener;
    }

    public CommunicationManager(Player player,Consumer<Exception> exceptionListener){
        this.exceptionListener = exceptionListener;
        this.player = player;

        try{
            socket = new Socket("localhost", 1234);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            in = new BufferedReader(new InputStreamReader(inputStream));
            out = new PrintWriter(outputStream);
        }
        catch(IOException e){
            exceptionListener.accept(e);
        }
    }

    public void start(){
        running = true;
        startUpdatingingMoveAngles();
        new Thread(this::handleInput).start();
    }

    public void send(String msg){
        if(!socket.isConnected())
            exceptionListener.accept(new IOException("socket closed"));
        else {
            out.println(msg);
            out.flush();
        }
    }

    private void handleInput(){
        while(running){
            try {
                String msg = in.readLine();
                listener.accept(msg);
            } catch (IOException e) {
                exceptionListener.accept(e);
            }
        }
    }

    public void startUpdatingingMoveAngles()
    {
        TimerTask updatingMoveAngles = new TimerTask() {
            @Override
            public void run() {
                int screenX = Gdx.input.getX();
                int screenY = Gdx.input.getY();
                updateMoveAngles(screenX,screenY);
            }
        };
        Timer timer = new Timer();
        timer.schedule(updatingMoveAngles,0,500);
    }

    public void updateMoveAngles(int cursorX, int cursorY)
    {
        Position basePosition = player.getBiggestBall().getPosition();
        for (Ball ball: player.getBalls().values()) {
            Position thisBallPosition = ball.getPosition();
            Position difference = new Position(thisBallPosition.x-basePosition.x,thisBallPosition.y-basePosition.y);
            Position screenRelativePosition =
                    new Position(Gdx.graphics.getWidth()  / 2 + difference.x,Gdx.graphics.getHeight() / 2 - difference.y);
            double angle = Math.atan2(-(cursorY-screenRelativePosition.y),cursorX-screenRelativePosition.x);
            ball.setMoveAngle(angle);

            JSONObject json = new JSONObject();
            json.put("action", "move_angle_update");
            json.put("id",ball.getId());
            json.put("angle", angle);
            send(json.toString());
        }
    }

    public void end(){
        running = false;
        try {
            socket.close();
        } catch (IOException e) {
            exceptionListener.accept(e);
        }
    }

}
