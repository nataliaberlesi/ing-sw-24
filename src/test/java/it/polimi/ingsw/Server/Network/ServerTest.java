package it.polimi.ingsw.Server.Network;

import it.polimi.ingsw.Client.Network.NetworkManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {
    /**
     * Tests if the first player to connect is the master
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    void FirstPlayerIsMaster() throws IOException, InterruptedException {
        ThreadServer ts=new ThreadServer();
        ThreadClient tc=new ThreadClient();
        Thread t1=new Thread(ts);
        t1.start();
        new Thread(tc).start();
        t1.join();
        assertTrue(ts.getServer().getConnections().get(0).isMaster);
    }
    static class ThreadClient implements Runnable {
        NetworkManager networkManager;
        public NetworkManager getClient() {
            return networkManager;
        }
        public void run() {
            try {
                this.networkManager =new NetworkManager("localhost",1000);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    static class ThreadServer implements Runnable {
        Server server;
        public Server getServer() {
            return server;
        }
        public void run() {
            try {
                this.server=new Server(1000);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}