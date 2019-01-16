package pl.dawidraszka.uszko.beans;

public class Message {
    private String message;
    private boolean isDeviceOwner;

    public Message(String message, boolean isDeviceOwner) {
        this.message = message;
        this.isDeviceOwner = isDeviceOwner;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDeviceOwner() {
        return isDeviceOwner;
    }

    public void setDeviceOwner(boolean deviceOwner) {
        isDeviceOwner = deviceOwner;
    }
}
