package it.polimi.ingsw.Client.View;

import it.polimi.ingsw.Client.Network.MessageDispatcher;
import it.polimi.ingsw.Client.Network.MessageParser;
import it.polimi.ingsw.Client.Network.NetworkManager;
import it.polimi.ingsw.Client.View.CLI.ViewControllerCLI;
import it.polimi.ingsw.Client.View.GUI.GUIApplication;
import it.polimi.ingsw.Client.View.GUI.ViewControllerGUI;

import java.io.IOException;


public class ClientLauncher {
    public static void main(String[] args) {

        if(args.length>1) {
            try {
                NetworkManager networkManager = new NetworkManager(args[1], 60600);
                MessageParser messageParser = MessageParser.getInstance();
                MessageDispatcher messageDispatcher = new MessageDispatcher(networkManager);
                networkManager.setMessageParser(messageParser);
                if (args[0].equals("--gui") ) {
                    ViewControllerGUI viewControllerGUI = new ViewControllerGUI(messageParser, messageDispatcher);
                    GUIApplication.setViewControllerGUI(viewControllerGUI);
                    GUIApplication.setParserAndDispatcher(messageParser, messageDispatcher);
                    new Thread(GUIApplication::main).start();
                    while(viewControllerGUI.getStage() == null){}
                    new Thread(networkManager).start();
                    System.out.println("hello");

                } else if (args[0].equals("--cli")) {
                    new ViewControllerCLI(messageParser, messageDispatcher);
                    new Thread(networkManager).start();
                }
            } catch(IOException ioe) {
                System.out.println(ioe);
            }
        }
        else System.out.println("YOU NEED TO PROVIDE AN IP ADDRESS");
    }
}
