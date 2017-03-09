package com.game.agarserver.communication;

import com.game.agarserver.logic.Position;
import com.game.agarserver.logic.World;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(1234);
        World world = new World(500,500);

        world.doStart();

        while(true){
            Socket socket = serverSocket.accept();
            world.createNewPlayer(socket);
        }
    }

}
