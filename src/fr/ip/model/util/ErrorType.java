package fr.ip.model.util;

public enum ErrorType {
    INFORMATION("II : "),
    WARNING("WW : "),
    ERROR("EE : ");

    private String toString;

    ErrorType(String toString) {
        this.toString = toString;
    }

    public String toString() {
        return toString;
    }
}