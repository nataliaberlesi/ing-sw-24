package it.polimi.ingsw.Server.Network;

import org.json.JSONObject;

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
            Receive();
            Send();
        }
    }
    public void Send() {
        JSONObject jobj=new JSONObject(2);
        jobj.put("Day",increaseDay());
        jobj.put("Message","Hello World!");
        outSocket.println(jobj.toString());
        System.out.println("Message sent:"+jobj.toString());
    }
    public void Receive() throws IOException {
        System.out.println("Aspetto un messaggio...");
        String string=inSocket.readLine();
        JSONObject jobj=new JSONObject(string);
        System.out.println("Name:"+jobj.get("Name"));
        System.out.println("Password:"+jobj.get("Psw"));
        System.out.println("Message received: "+string);
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

