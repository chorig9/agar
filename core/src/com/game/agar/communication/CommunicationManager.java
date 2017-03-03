package com.game.agar.communication;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;

public class CommunicationManager {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Consumer<String> listener;

    private volatile boolean running;

    public void setCommunicationListener(Consumer<String> listener){
        this.listener = listener;
    }

    public void start() throws IOException {
        running = true;
        socket = new Socket("localhost", 1234);
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        in = new BufferedReader(new InputStreamReader(inputStream));
        out = new PrintWriter(outputStream);

        new Thread(this::handleInput).start();
    }

    public void send(String msg) throws IOException{
        if(!socket.isConnected())
            throw new IOException("connection closed");

        out.print(msg);
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

    // TODO - where to call this method?
    public void end() throws IOException {
        running = false;
        socket.close();
    }

}
