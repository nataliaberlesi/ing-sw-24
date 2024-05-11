package it.polimi.ingsw.Client.View;

import it.polimi.ingsw.Client.Network.MessageDispatcher;
import it.polimi.ingsw.Client.Network.MessageParser;
import it.polimi.ingsw.Client.Network.NetworkManager;
import it.polimi.ingsw.Client.View.CLI.ViewControllerCLI;
import it.polimi.ingsw.Client.View.GUI.GUIApplication;
import it.polimi.ingsw.Client.View.GUI.InitialScenesController;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class ClientLauncher {
    public static void main(String[] args) {
        try {
            NetworkManager networkManager=new NetworkManager("localhost",60600);
            MessageParser messageParser=new MessageParser(networkManager);
            MessageDispatcher messageDispatcher=new MessageDispatcher(networkManager);
            networkManager.setMessageParser(messageParser);
            ViewControllerCLI viewControllerCLI=new ViewControllerCLI(messageParser, messageDispatcher);
            /*GUIApplication.setParserAndDispatcher(messageParser,messageDispatcher);
            new Thread(()-> GUIApplication.main()).start();
            sleep(5000);*/
            new Thread(networkManager).start();
            while(true) {

            }

        } catch(IOException ioe) {
            System.out.println(ioe);
        }

    }
}
