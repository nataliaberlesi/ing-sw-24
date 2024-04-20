package it.polimi.ingsw.Client.Network;

import java.util.ArrayList;
import java.util.Arrays;

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

    /**
     * creates a message from an arraylist of parameters
     * @param type
     * @param method
     * @param params
     */
    public Message(String type, String method, ArrayList<Object> params) {
        this.type = type;
        this.method = method;
        this.params = params;
    }

    /**
     * creates a message with empty parameters
     * @param type
     * @param method
     */
    public Message(String type, String method) {
        this.type = type;
        this.method = method;
        this.params=new ArrayList<Object>();
    }

    /**
     * creates a message with a variable amount of parameters
     * @param type
     * @param method
     * @param params
     */
    public Message(String type, String method, Object...params) {
        this.type=type;
        this.method=method;
        this.params=new ArrayList<Object>();
        this.params.addAll(Arrays.asList(params));
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
    public void addParam(Object param) {
        params.add(param);
    }
}
