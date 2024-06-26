package it.polimi.ingsw.Server.Controller;

import it.polimi.ingsw.Server.Network.MessageParser;

import java.io.*;
import java.util.ArrayList;

public class PersistenceHandler {
    private static final String gameInstancePath;
    private static final MessageParser messageParser=MessageParser.getINSTANCE();
    private static File file;
    private static FileWriter fileWriter;
    private static BufferedReader bufferedReader;
    private static ArrayList<String> playersTemp;
    private static boolean persistence=true;

    static {
        gameInstancePath="gameinstance.json";
        file=new File(gameInstancePath);
    }
    public static void saveState(GameInstance gameInstance) throws IOException {
        if(persistence) {
            String state=messageParser.toJson(gameInstance);
            if(file.exists()) {
                if(file.delete()){
                    System.out.println("Deleted file: "+ gameInstancePath);
                }
            }
            if(file.createNewFile()){
                System.out.println("File created: "+gameInstancePath);
            }
            fileWriter=new FileWriter(gameInstancePath);
            fileWriter.write(state);
            fileWriter.flush();
            fileWriter.close();
        }
    }
    public static GameInstance fetchState() throws IOException {
        bufferedReader=new BufferedReader(new FileReader(file));
        StringBuilder state=new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            state.append(line);
        }
        bufferedReader.close();
        return messageParser.parseGameInstance(String.valueOf(state));
    }
    public static boolean gameAlreadyExists() {
        return file.exists();
    }
    public static void deleteGame() {
        file.delete();
    }
    public static void closePersistence() {
        persistence=false;
    }
    public static void startPersistence() {
        persistence=true;
    }
    public static void setPlayersTemp(ArrayList<String> playersTemp) {
        PersistenceHandler.playersTemp=playersTemp;
    }
    public static boolean checkPlayerTemp(String player) {
        boolean flag=true;
        if(playersTemp.contains(player)){
            flag=false;
            playersTemp.remove(player);
        }
        return flag;
    }
    public static boolean playersTempIsEmpty() {
        if(playersTemp!=null) {
            return playersTemp.isEmpty();
        } else {
            return false;
        }
    }
}
