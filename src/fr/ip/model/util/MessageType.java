package fr.ip.model.util;

public enum MessageType {
    INFORMATION("Information : "),
    WARNING("Warning : "),
    ERROR("Error : ");

    private String toString;

    MessageType(String toString) {
        this.toString = toString;
    }

    public String toString() {
        return toString;
    }
}