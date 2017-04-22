package com.game.agarserver.communication;

import com.game.agar.shared.Connection;
import com.game.agarserver.eventsystem.EventProcessor;
import com.game.agarserver.eventsystem.MainEventHandler;
import com.game.agarserver.eventsystem.NetworkEventHandler;
import com.game.agarserver.logic.Broadcaster;
import com.game.agarserver.logic.User;
import com.game.agarserver.logic.World;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    Thread gameThread, networkThread;
    ServerSocket serverSocket;
    EventProcessor eventProcessor;
    Broadcaster broadcaster;
    World world;
    List<User> users = new ArrayList<>();

    public void run() {
        try {
            serverSocket = new ServerSocket(1234);

            eventProcessor = new EventProcessor();
            world = new World(users, eventProcessor, 1000, 1000);
            broadcaster = new Broadcaster(world.getUsers());

            eventProcessor.addEventHandler(new MainEventHandler(broadcaster));
            eventProcessor.addEventHandler(new NetworkEventHandler(broadcaster));

            //networkThread = new Thread(new NetworkThread(connections));
           // networkThread.start();
            gameThread = new Thread(new GameThread(world));
            gameThread.start();


            while (true) {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket);
                connection.start();
                User user = new User(connection);
                connection.setCommunicationListener(new CommunicationListener(user, world));

                world.addNewPlayer(user);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            gameThread.interrupt();
            try {
                gameThread.join();
                networkThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws Exception {
        new Server().run();
    }

}
