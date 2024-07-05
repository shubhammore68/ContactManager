package com.smart.contactmanager.config;




public class MessageConfig {
    private String message;
    private String type;
    
    public MessageConfig() {
    }
    
    public MessageConfig(String message, String type) {
        super();
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    

    
}
