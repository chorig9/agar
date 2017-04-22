package com.game.agarserver.communication;

import com.game.agar.shared.Connection;
import com.game.agarserver.eventsystem.EventProcessor;
import com.game.agarserver.eventsystem.MainEventHandler;
import com.game.agarserver.eventsystem.events.BallMoveEvent;
import com.game.agarserver.eventsystem.events.BallRadiusChangeEvent;
import com.game.agarserver.eventsystem.events.EntityDieEvent;
import com.game.agarserver.eventsystem.events.FrameEvent;
import com.game.agarserver.logic.*;

import javax.swing.tree.ExpandVetoException;
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

            eventProcessor.addEventHandler(FrameEvent.class, new MainEventHandler(broadcaster));
            eventProcessor.addEventHandler(EntityDieEvent.class, (event) -> {
                broadcaster.addPacketToSend(PacketFactory.createRemovePacket(((EntityDieEvent)event).getEntity()));
            });
            eventProcessor.addEventHandler(BallMoveEvent.class, (event) -> {
                Ball ball = ((BallMoveEvent)event).getBall();
                broadcaster.addPacketToSend(PacketFactory.createPositionPacket(ball.getEntityId(), ball.getPosition()));
            });
            eventProcessor.addEventHandler(BallRadiusChangeEvent.class, (event) -> {
                Ball ball = ((BallRadiusChangeEvent)event).getBall();
                broadcaster.addPacketToSend(PacketFactory.createRadiusPacket(ball.getEntityId(), ball.getRadius()));
            });

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
