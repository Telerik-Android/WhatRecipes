package com.whatrecipes.whatrecipes.view;

/**
 * Created by dnt on 12.2.2017 г..
 */

public class Message {
    private String message;
    private String name;

    public Message(){

    }

    public Message(String name, String message){
        setMessage(message);
        setName(name);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
