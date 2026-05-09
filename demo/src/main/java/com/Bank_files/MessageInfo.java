package com.Bank_files;

public class MessageInfo<T> {
    public String messageString;public boolean success;public T data;
    public MessageInfo( boolean success, String messageString) {
        this.messageString = messageString;
        this.success = success;
    }
    public MessageInfo( boolean success, String messageString, T data) {
        this.messageString = messageString;
        this.success = success;
        this.data = data;
    }
}
