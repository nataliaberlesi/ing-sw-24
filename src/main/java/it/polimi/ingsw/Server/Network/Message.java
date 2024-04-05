package it.polimi.ingsw.Server.Network;

import java.util.ArrayList;

public class Message {
    /**
     * The type of message [i.e. System;Game;Warning...]
     */
    private String type;
    /**
     * The method that will be called by the message
     */
    private String method;
    /**
     * The parameters that will be passed into the method
     */
    private ArrayList<Object> params;

    public Message(String type, String method, ArrayList<Object> params) {
        this.type = type;
        this.method = method;
        this.params = params;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public ArrayList<Object> getParams() {
        return params;
    }

    public void setParams(ArrayList<Object> params) {
        this.params = params;
    }
}
