package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.View.ClientLauncher;
import it.polimi.ingsw.Server.Controller.ServerLauncher;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        if (args.length > 0) {
            System.out.println(Arrays.toString(args));
            String param0 = args[0];
            if (param0.equals("--server")) {
                ServerLauncher.main(args);
            } else {
                ClientLauncher.main(args);
            }
        }
    }
}
