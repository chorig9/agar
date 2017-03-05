package com.game.agarserver.communication;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Consumer<String> listener;

    private volatile boolean running;

    public Connection(Socket socket){
        this.socket = socket;
        try{
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            in = new BufferedReader(new InputStreamReader(inputStream));
            out = new PrintWriter(outputStream);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setCommunicationListener(Consumer<String> listener){
        this.listener = listener;
    }

    public void start(){
        running = true;
        new Thread(this::handleInput).start();
    }

    public void send(String msg) throws IOException{
        if(!socket.isConnected())
            throw new IOException("connection closed");

        out.println(msg);
        out.flush();
    }

    private void handleInput(){
        while(running){
            try {
                String msg = in.readLine();
                listener.accept(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void end(){
        running = false;
        try {
            socket.close();
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.FINE, e.getMessage());
        }
    }
}
