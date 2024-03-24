package it.polimi.ingsw.Client.Network;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;

public class Client {
    private PrintWriter outSocket;
    private BufferedReader inSocket;
    private BufferedReader inKeyboard;
    public Client() throws IOException {
        Socket s=new Socket("localhost",3000);
        outSocket=new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())),true);
        inSocket=new BufferedReader(new InputStreamReader(s.getInputStream()));
        inKeyboard=new BufferedReader(new InputStreamReader(System.in));
        play();
    }

    public void play() throws IOException {
        while(true) {
           send();
           receive();
        }
    }
    public Object putName(Object jobj) throws IOException {

        return jobj;

    }
    public Object putPsw(Object jobj) throws IOException {
      return jobj;
    }
    public void send() {

    }
    public void receive() throws IOException {

    }

    public static void main(String[] args) throws IOException {
        Client client=new Client();
    }
}
