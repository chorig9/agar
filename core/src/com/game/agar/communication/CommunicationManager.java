package com.game.agar.communication;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;

public class CommunicationManager {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Consumer<String> listener;
    private Consumer<Exception> exceptionListener;

    private volatile boolean running;

    public void setCommunicationListener(Consumer<String> listener){
        this.listener = listener;
    }

    public CommunicationManager(Consumer<Exception> exceptionListener){
        this.exceptionListener = exceptionListener;

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

    public void end(){
        running = false;
        try {
            socket.close();
        } catch (IOException e) {
            exceptionListener.accept(e);
        }
    }

}
