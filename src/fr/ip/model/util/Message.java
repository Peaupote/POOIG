package fr.ip.model.util;

public class Message {
    public final MessageType t;
    public final String content;

    public Message(MessageType t, String content) {
        this.t = t;
        this.content = content;
    }

    @Override
    public String toString() {
        return t + content;
    }

    public void print() {
        print(false);
    }

    public void print(boolean graphic) {
        if(!graphic)
            System.out.println(toString());
        else {
            // ADD GUI FOR MESSAGE. (JDialog)
        }


    }
}