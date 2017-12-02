package fr.ip.model.util;

public enum ErrorType {
    INFORMATION("Information : "),
    WARNING("Warning : "),
    ERROR("Error : ");

    private String toString;

    ErrorType(String toString) {
        this.toString = toString;
    }

    public String toString() {
        return toString;
    }
}