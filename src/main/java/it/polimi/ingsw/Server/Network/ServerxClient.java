package it.polimi.ingsw.Server.Network;


import java.io.*;
import java.net.Socket;

public class ServerxClient implements Runnable{
    Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    int day;
    public ServerxClient(Socket s) throws IOException {
        System.out.println("Connessione stabilita");
        socket=s;
        inSocket=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outSocket=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
        day=0;
    }
    public void play() throws IOException {
        while(true) {
            receive();
            send();
        }
    }
    public void send() {
    }
    public void receive() throws IOException {
    }
    public int increaseDay() {
        return ++this.day;
    }

    @Override
    public void run() {
        try {
            play();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

