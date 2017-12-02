package fr.ip.model.util;

public enum Level {
    BEGINNER("Débutant"),
    INTERMEDIATE("Intermédiaire"),
    EXPERIENCED("Confirmé"),
    EXPERT("Expert"),
    ANY("Mixte");

    private String name;

    Level(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}