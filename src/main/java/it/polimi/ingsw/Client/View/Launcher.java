package it.polimi.ingsw.Client.View;

import it.polimi.ingsw.Client.Network.MessageDispatcher;
import it.polimi.ingsw.Client.Network.MessageParser;
import it.polimi.ingsw.Client.Network.NetworkManager;
import it.polimi.ingsw.Client.View.GUI.GUIApplication;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) {
        try {
            NetworkManager networkManager=new NetworkManager("localhost",60600);
            MessageParser messageParser=new MessageParser(networkManager);
            MessageDispatcher messageDispatcher=new MessageDispatcher(networkManager);
            GUIApplication.setParserAndDispatcher(messageParser,messageDispatcher);
            GUIApplication.main(null);
            new Thread(networkManager).start();

        } catch(IOException ioe) {

        }

    }
}
