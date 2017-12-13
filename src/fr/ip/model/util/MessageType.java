package fr.ip.model.util;

public enum MessageType {
    INFORMATION("Information", "II : "),
    WARNING("Warning", "WW : "),
    ERROR("Error", "EE : ");

    private String toString;
    private String title;

    MessageType(String name, String toString) {
        this.toString = toString;
        this.title = name;
    }

    public String toString() {
        return toString;
    }
    public String title() {
        return title;
    }
}