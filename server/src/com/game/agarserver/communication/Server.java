package com.game.agarserver.communication;

import com.game.agarserver.eventsystem.EventProcessor;
import com.game.agarserver.eventsystem.MainEventHandler;
import com.game.agarserver.eventsystem.NetworkEventHandler;
import com.game.agarserver.logic.Broadcaster;
import com.game.agarserver.logic.World;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    Thread gameThread;
    ServerSocket serverSocket;
    EventProcessor eventProcessor;
    Broadcaster broadcaster;
    World world;

    public void run() {
        try {
            serverSocket = new ServerSocket(1234);

            eventProcessor = new EventProcessor();
            world = new World(1000, 1000, eventProcessor);
            broadcaster = new Broadcaster(world.getUsers());

            eventProcessor.addEventHandler(new MainEventHandler(broadcaster));
            eventProcessor.addEventHandler(new NetworkEventHandler(broadcaster));

            gameThread = new Thread(new GameThread(world));
            gameThread.start();



            //world.doStart();

            while (true) {
                Socket socket = serverSocket.accept();
                world.createNewPlayer(socket);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            gameThread.interrupt();
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws Exception {
        new Server().run();
    }

}
