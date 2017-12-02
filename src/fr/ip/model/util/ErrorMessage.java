package fr.ip.model.util;


public class ErrorMessage {
    public final ErrorType t;
    public final String content;

    public ErrorMessage(ErrorType t, String content) {
        this.t = t;
        this.content = content;
    }

    @Override
    public String toString() {
        return t + content;
    }

    public void print() {
        System.out.println(toString());
    }
}